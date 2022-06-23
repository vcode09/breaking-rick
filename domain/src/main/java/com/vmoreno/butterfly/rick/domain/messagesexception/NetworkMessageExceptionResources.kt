package com.vmoreno.butterfly.rick.domain.messagesexception

interface NetworkMessageExceptionResources : MessageExceptionResources {
    fun connectionTitle(): String
    fun connectionBody(): String
}