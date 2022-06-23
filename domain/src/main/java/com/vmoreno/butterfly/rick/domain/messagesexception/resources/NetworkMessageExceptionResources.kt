package com.vmoreno.butterfly.rick.domain.messagesexception.resources

interface NetworkMessageExceptionResources : MessageExceptionResources {
    fun connectionTitle(): String
    fun connectionBody(): String
}