package com.vmoreno.butterfly.rick.domain.messagesexception.resources

interface NotFoundMessageExceptionResources : MessageExceptionResources {
    fun notFoundTitle(): String
    fun notFoundBody(): String
}