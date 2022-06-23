package com.vmoreno.butterfly.rick.domain.messagesexception

class MessageExceptionHandler(
    private val messageExceptionResources: MessageExceptionResources,
    vararg generalMessagesException: GeneralMessageException
) : MessageException {

    var firstMessageGeneral: GeneralMessageException? = null
    private var chainMessageGeneral: GeneralMessageException? = null

    init {
        for (ex in generalMessagesException) {
            add(ex)
        }
    }

    private fun add(generalMessageException: GeneralMessageException) {
        if (chainMessageGeneral == null) {
            chainMessageGeneral = generalMessageException
            firstMessageGeneral = chainMessageGeneral
        } else {
            chainMessageGeneral?.nextMessageException = generalMessageException
            chainMessageGeneral = chainMessageGeneral?.nextMessageException
        }
    }

    override fun process(error: Throwable, params: HashMap<String, Any>?): MessageExceptionInfo {
        return firstMessageGeneral?.process(error, params)
            ?: MessageExceptionInfo(
                messageExceptionResources.messageTitle(),
                messageExceptionResources.messageBody(),
                error
            )
    }
}