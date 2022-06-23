package com.vmoreno.butterfly.rick.domain.messagesexception

import com.vmoreno.butterfly.rick.domain.exceptions.NetworkException

open class NetworkMessageException(
    private val resources: NetworkMessageExceptionResources
) : GeneralMessageException(resources) {
    override fun process(error: Throwable, params: HashMap<String, Any>?): MessageExceptionInfo {
        if (error is NetworkException) {
            return MessageExceptionInfo(
                resources.connectionTitle(),
                resources.connectionBody(),
                error)
        }
        return super.process(error, params)
    }
}