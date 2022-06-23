package com.vmoreno.butterfly.rick.common

import com.vmoreno.butterfly.rick.domain.messagesexception.MessageExceptionInfo

interface ErrorView {
    fun showError(messageExceptionInfo: MessageExceptionInfo)
}