package com.vmoreno.butterfly.rick.domain.messagesexception

abstract class GeneralMessageException(
    private val messageExceptionResources: MessageExceptionResources
) : MessageException {

    var nextMessageException: GeneralMessageException? = null

    override fun process(error: Throwable, params: HashMap<String, Any>?): MessageExceptionInfo {
        return nextMessageException?.process(error, params)
            ?: MessageExceptionInfo(
                messageExceptionResources.messageTitle(),
                messageExceptionResources.messageBody(),
                error
            )
    }
}