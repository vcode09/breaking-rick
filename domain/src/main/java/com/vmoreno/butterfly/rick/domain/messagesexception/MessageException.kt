package com.vmoreno.butterfly.rick.domain.messagesexception

interface MessageException {
    fun process(error: Throwable, params: HashMap<String, Any>? = null): MessageExceptionInfo
}