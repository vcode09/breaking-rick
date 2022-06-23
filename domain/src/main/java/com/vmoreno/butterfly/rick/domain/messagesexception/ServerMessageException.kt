package com.vmoreno.butterfly.rick.domain.messagesexception

import com.vmoreno.butterfly.rick.domain.exceptions.ServerException
import com.vmoreno.butterfly.rick.domain.utils.value

open class ServerMessageException(
    private val resources: MessageExceptionResources
) : GeneralMessageException(resources) {
    override fun process(error: Throwable, params: HashMap<String, Any>?): MessageExceptionInfo {
        if (error is ServerException) {
            return MessageExceptionInfo(
                resources.messageTitle(),
                error.message.value(),
                error)
        }
        return super.process(error, params)
    }
}