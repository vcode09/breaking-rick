package com.vmoreno.butterfly.rick.modules.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.vmoreno.butterfly.rick.domain.entities.CharacterBreakingBadEntity
import com.vmoreno.butterfly.rick.domain.exceptions.ErrorResponse
import com.vmoreno.butterfly.rick.domain.exceptions.SuccessResponse
import com.vmoreno.butterfly.rick.domain.messagesexception.MessageException
import com.vmoreno.butterfly.rick.*
import com.vmoreno.butterfly.rick.common.ScopedViewModel
import com.vmoreno.butterfly.rick.modules.character.entities.CharacterBreakingBadUi
import com.vmoreno.butterfly.rick.modules.character.entities.mapToDomain
import com.vmoreno.butterfly.rick.modules.character.viewmodel.CharacterViewModel
import com.vmoreno.butterfly.rick.network.NetworkStatus
import com.vmoreno.butterfly.rick.usecases.breakingbad.UseCase
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.refEq
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val FIELD_NAME_DATA_LIST = "_dataList"

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterViewModelTest {

    @Mock
    lateinit var useCaseMock: UseCase

    @Mock
    lateinit var messageException: MessageException

    private val observer: Observer<ScopedViewModel.UiModel> = mock()

    private lateinit var sut: CharacterViewModel

    private val testContextProvider = TestContextProvider()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTestExecutorRule = InstantTaskExecutorRule()

    companion object {
        const val offset = 50
        const val limit = 20
    }

    @Before
    fun setup() {
        sut = CharacterViewModel(useCaseMock, messageException, testContextProvider)
    }

    @Test
    fun getCharacterList_Empty_returnLoading() =
        runBlockingTest {
            // given
            given(useCaseMock.getCharacters(any(), any())).willReturn(
                SuccessResponse(mockedEmptyListBreakingBad)
            )
            sut.model.observeForever(observer)
            testContextProvider.testCoroutineDispatcher.advanceUntilIdle()
            // when
            sut.getCharacterList()
            // then
            assertEquals(true, sut.notFoundList.value)
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.Loading
                )
            )
        }

    @Test
    fun setCharacterList_list_setDataList() {
        // given

        val characterList = listOf(mockedEntity)
        val uiCharacterList = characterList.map {
            CharacterBreakingBadUi.mapFromDomain(it)
        }
        // when
        sut.callPrivateFun("setCharacterListList", characterList)
        // then
        assertEquals(uiCharacterList, sut.dataList)
    }

    @Test
    fun updateFavouriteList_updateCharacterList_emptyList() {
        // given
        val characterList = emptyList<CharacterBreakingBadEntity>()
        val dataList = listOf(mockedEntity)
        setFieldHelper(FIELD_NAME_DATA_LIST, dataList, sut)
        // when
        sut.callPrivateFun("updateFavouriteList", characterList, true)
        // then
        assertEquals(dataList, sut.characterList.value)
    }

    @Test
    fun updateFavouriteList_updateCharacterList_favouriteList() {
        // given
        val characterList = listOf(mockedEntity)
        val uiCharacterList = characterList.map {
            CharacterBreakingBadUi.mapFromDomain(it)
        }
        setFieldHelper(FIELD_NAME_DATA_LIST, uiCharacterList, sut)
        // when
        sut.callPrivateFun("updateFavouriteList", characterList, true)
        // then
        assertEquals(uiCharacterList, sut.characterList.value)
    }

    @Test
    fun notifyFavourite_setValue_true() {
        // when
        sut.callPrivateFun("notifyFavourite", true)
        // then
        assertEquals(true, sut.notifyFavourite.value)
    }

    @Test
    fun notifyFavourite_setValue_false() {
        // when
        sut.callPrivateFun("notifyFavourite", false)
        // then
        assertEquals(false, sut.notifyFavourite.value)
    }

    @Test
    fun getCharacters_query_returnSuccessEmpty() =
        runBlockingTest {
            // given
            given(useCaseMock.getCharacters(any(), any())).willReturn(
                SuccessResponse(mockedEmptyListBreakingBad)
            )
            sut.model.observeForever(observer)
            testContextProvider.testCoroutineDispatcher.advanceUntilIdle()
            // when
            sut.getCharacterList()
            // then
            assertEquals(true, sut.notFoundList.value)
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.HideLoader
                )
            )
        }

    @Test
    fun getCharacters_query_returnFailure() =
        runBlockingTest {
            // given
            val error = Throwable("error fetching character list")
            given(useCaseMock.getCharacters(any(), any())).willReturn(
                ErrorResponse(error)
            )
            sut.model.observeForever(observer)
            testContextProvider.testCoroutineDispatcher.advanceUntilIdle()
            // when
            sut.getCharacterList()
            // then
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.Loading
                )
            )
            assertEquals(null, sut.notFoundList.value)
        }

    @Test
    fun getCharacters_query_returnSuccessList() =
        runBlockingTest {
            // given
            val characterList = listOf(mockedEntity)
            val uiCharacterList = characterList.map {
                CharacterBreakingBadUi.mapFromDomain(it)
            }
            given(useCaseMock.getCharacters(any(), any())).willReturn(
                SuccessResponse(characterList)
            )
            sut.model.observeForever(observer)
            // when
            sut.getCharacterList()
            // then
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.Loading
                )
            )
            assertEquals(uiCharacterList, sut.dataList)
        }

    @Test
    fun setCharacter_setValue_setCharacterBreakingBadUi() {
        // given
        // when
        sut.callPrivateFun("setCharacter", mockedBreakingBadUi)
        // then
        assertEquals(mockedBreakingBadUi, sut.character.value)
    }

    @Test
    fun saveFavourite_setValue_setCharacterBreakingBadUi() =
        runBlockingTest {
            // given
            whenever(useCaseMock.saveFavouriteItems(any())).thenReturn(null)
            whenever(useCaseMock.getFavouriteItems()).thenReturn(null)
            // when
            sut.saveFavourite(mockedBreakingBadUi)
            // then
            verify(useCaseMock).saveFavouriteItems(mockedBreakingBadUi.mapToDomain())
            verify(useCaseMock).getFavouriteItems()
        }


    @Test
    fun showHideInternetConnection_FirstInternetCall_networkStatusUnavailable() {
        //given
        setFieldHelper("firstDefaultInternetCall", false, sut)
        //when
        sut.showHideInternetConnection(NetworkStatus.Unavailable)
        //then
        assertEquals(ScopedViewModel.UiModel.NoInternetState, sut.model.value)
    }

    @Test
    fun showHideInternetConnection_FirstInternetCall_networkStatusAvailable() {
        //given
        setFieldHelper("firstDefaultInternetCall", false, sut)
        //when
        sut.showHideInternetConnection(NetworkStatus.Available)
        //then
        assertEquals(ScopedViewModel.UiModel.EmptyState, sut.model.value)
    }
}