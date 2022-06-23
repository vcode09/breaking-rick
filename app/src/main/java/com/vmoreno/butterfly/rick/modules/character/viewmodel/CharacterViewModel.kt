package com.vmoreno.butterfly.rick.modules.character.viewmodel

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vmoreno.butterfly.rick.domain.entities.CharacterBreakingBadEntity
import com.vmoreno.butterfly.rick.domain.exceptions.onError
import com.vmoreno.butterfly.rick.domain.exceptions.onSuccess
import com.vmoreno.butterfly.rick.domain.messagesexception.MessageException
import com.vmoreno.butterfly.rick.common.BaseViewModel
import com.vmoreno.butterfly.rick.common.ScopedViewModel
import com.vmoreno.butterfly.rick.common.SingleLiveEvent
import com.vmoreno.butterfly.rick.modules.character.entities.CharacterBreakingBadUi
import com.vmoreno.butterfly.rick.modules.character.entities.EpisodesBreakingBadUi
import com.vmoreno.butterfly.rick.modules.character.entities.mapToDomain
import com.vmoreno.butterfly.rick.modules.character.view.CharacterFragmentDirections
import com.vmoreno.butterfly.rick.network.InternetAvailability
import com.vmoreno.butterfly.rick.network.NetworkStatus
import com.vmoreno.butterfly.rick.utils.Constant.LIMIT
import com.vmoreno.butterfly.rick.utils.Constant.OFFSET
import com.vmoreno.butterfly.rick.utils.Constant.RANDOM_END
import com.vmoreno.butterfly.rick.utils.Constant.RANDOM_START
import com.vmoreno.butterfly.rick.utils.CoroutineContextProvider
import com.vmoreno.butterfly.rick.utils.asLiveData
import com.vmoreno.butterfly.rick.utils.concatenate
import com.vmoreno.butterfly.rick.utils.lifecycle.NavigationToDirectionEvent
import com.vmoreno.butterfly.rick.usecases.breakingbad.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

class CharacterViewModel(
    private val useCase: UseCase,
    messageException: MessageException,
    contextProvider: CoroutineContextProvider
) : BaseViewModel(messageException) {

    private val _model = SingleLiveEvent<ScopedViewModel.UiModel>()
    val model get() = _model.asLiveData()
    private lateinit var job: Job
    private lateinit var jobService: Job
    var firstDefaultInternetCall: Boolean = true
    private val _character = MutableLiveData<CharacterBreakingBadUi>()
    val character get() = _character.asLiveData()
    val characterList: LiveData<List<CharacterBreakingBadUi>> get() = _characterList
    private var _characterList = MutableLiveData(listOf<CharacterBreakingBadUi>())
        private set
    val episode: LiveData<EpisodesBreakingBadUi> get() = _episode
    private var _episode = MutableLiveData(EpisodesBreakingBadUi())
        private set
    var updateFavorite: Boolean = false
    private val _notifyFavourite = MutableLiveData<Boolean>()
    val notifyFavourite get() = _notifyFavourite.asLiveData()
    private var _dataList: List<CharacterBreakingBadUi> = listOf()
    val dataList get() = _dataList
    private val _notFoundList = MutableLiveData<Boolean>()
    val notFoundList get() = _notFoundList
    private var _internetVisibility = MutableLiveData(GONE)
    val internetVisibility: LiveData<Int> get() = _internetVisibility
    private var _errorVisibility = MutableLiveData(GONE)
    val errorVisibility: LiveData<Int> get() = _errorVisibility
    private val ioContext: CoroutineContext = (contextProvider.IO)
    private var currentLimit = LIMIT
    private var currentOffset = OFFSET

    fun init() {
        job = viewModelScope.launch(ioContext) {
            getCharacterList()
            if (InternetAvailability.check()) showLoaderUiModel()
            else showNoInternetUiModel()
        }
    }
    fun getCharacterList() {
        showLoaderUiModel()
        if (::jobService.isInitialized) jobService.cancel()
        jobService = viewModelScope.launch(ioContext) {
            useCase.getCharacters(
                currentOffset,
                currentLimit
            ).onSuccess {
                if (it.isNullOrEmpty()) {
                    _errorVisibility.postValue(GONE)
                    _notFoundList.postValue(true)
                    hideLoaderUiModel()
                } else {
                    currentOffset += currentLimit
                    setCharacterListList(it)
                }
            }.onError {
                if (this.failure !is CancellationException) {
                    showError(this)
                    if (dataList.isNullOrEmpty())
                        _errorVisibility.postValue(VISIBLE)
                }
            }
        }
    }


    private fun setCharacterListList(
        characterList: List<CharacterBreakingBadEntity>
    ) {

        val newResults = characterList.map {
            CharacterBreakingBadUi.mapFromDomain(it)
        }
        _dataList = concatenate(
            _dataList,
            newResults
        )
        updateView(false)
        hideLoaderUiModel()
    }

    private fun updateView(notifyFavourite: Boolean) {
        if (::job.isInitialized) job.cancel()
        job = viewModelScope.launch(ioContext) {
            updateFavouriteList(useCase.getFavouriteItems(), notifyFavourite)
        }
    }


    private fun updateFavouriteList(
        getFavouriteItems: List<CharacterBreakingBadEntity>,
        notifyFavourite: Boolean
    ) {
        val newFavourite = getFavouriteItems.map {
            CharacterBreakingBadUi.mapFromDomain(it)
        }
        updateFavorite = true

        if (!newFavourite.isNullOrEmpty()) {
            val dataList = _dataList.map { firstListElement ->
                newFavourite
                    .filter { it.charId == firstListElement.charId }
                    .lastOrNull()
                    .let {
                        firstListElement.copy(isFavourite = it?.isFavourite ?: false)
                    }
            }.sortedByDescending { it.isFavourite }
            _characterList.postValue(dataList)
        } else {
            _characterList.postValue(dataList)
        }
        notifyFavourite(notifyFavourite)
    }


    fun setCharacter(character: CharacterBreakingBadUi) {
        _character.value = character
        getEpisodes()
    }

    fun saveFavourite(item: CharacterBreakingBadUi) {
        if (::job.isInitialized) job.cancel()
        job = viewModelScope.launch(ioContext) {
            useCase.saveFavouriteItems(item.mapToDomain())
            updateFavouriteList(useCase.getFavouriteItems(), true)
        }
    }

    fun deleteFavourite(item: CharacterBreakingBadUi) {
        if (::job.isInitialized) job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteFavouriteItems(item.charId.toString())
            updateFavouriteList(useCase.getFavouriteItems(), false)
        }
    }

    private fun getEpisodes() {
        showLoaderUiModel()
        if (::jobService.isInitialized) jobService.cancel()
        jobService = viewModelScope.launch(ioContext) {
            useCase.getEpisodes(
                (RANDOM_START..RANDOM_END).random()
            ).onSuccess {
                if (!it.isNullOrEmpty()) {
                    _episode.postValue(EpisodesBreakingBadUi.mapFromDomain(it.first()))
                    hideLoaderUiModel()
                }
            }.onError {
                showError(this)
            }
        }
    }

    fun showHideInternetConnection(networkStatus: NetworkStatus) {
        if (!firstDefaultInternetCall)
            when (networkStatus) {
                NetworkStatus.Available -> showEmptyStateUiModel()
                NetworkStatus.Unavailable -> showNoInternetUiModel()
            }
        else {
            firstDefaultInternetCall = false
        }
    }

    fun onItemSelected(item: CharacterBreakingBadUi) {
        _navigationEvent.value =
            NavigationToDirectionEvent(
                CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(item)
            )
        notifyFavourite(false)
    }

    private fun notifyFavourite(notify: Boolean) {
        _notifyFavourite.postValue(notify)
    }


    private fun showEmptyStateUiModel() = _model.postValue(ScopedViewModel.UiModel.EmptyState)

    private fun hideLoaderUiModel() = _model.postValue(ScopedViewModel.UiModel.HideLoader)

    private fun showLoaderUiModel() = _model.postValue(ScopedViewModel.UiModel.Loading)

    private fun showNoInternetUiModel() = _model.postValue(ScopedViewModel.UiModel.NoInternetState)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        jobService.cancel()
    }
}