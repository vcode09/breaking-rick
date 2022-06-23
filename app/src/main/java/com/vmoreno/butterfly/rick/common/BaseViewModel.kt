package com.vmoreno.butterfly.rick.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vmoreno.butterfly.rick.domain.exceptions.ErrorResponse
import com.vmoreno.butterfly.rick.domain.messagesexception.MessageException
import com.vmoreno.butterfly.rick.domain.messagesexception.MessageExceptionInfo
import com.vmoreno.butterfly.rick.utils.asLiveData
import com.vmoreno.butterfly.rick.utils.lifecycle.NavigationEvents

open class BaseViewModel(private val messageException: MessageException) : ViewModel() {

    private val _hasInternetConnection = SingleLiveEvent<Boolean>()
    val hasInternetConnection get() = _hasInternetConnection.asLiveData()

    protected val errorMessageSingle = SingleLiveEvent<MessageExceptionInfo>()
    val errorMessage: LiveData<MessageExceptionInfo> = errorMessageSingle

    protected val _navigationEvent = SingleLiveEvent<NavigationEvents>()
    val navigationEvent get() = _navigationEvent.asLiveData()

    fun showError(errorResponse: ErrorResponse) {
        messageException.process(Throwable()).run {
            errorMessageSingle.postValue(this)
        }
    }
}