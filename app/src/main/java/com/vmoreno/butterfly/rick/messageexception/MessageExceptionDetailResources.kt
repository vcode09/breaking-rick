package com.vmoreno.butterfly.rick.messageexception

import android.content.res.Resources
import com.vmoreno.butterfly.rick.domain.messagesexception.MessageExceptionResources
import com.vmoreno.butterfly.rick.R


open class MessageExceptionDetailResources(
    private val resources: Resources
) : MessageExceptionResources {
    override fun messageTitle() = resources.getString(R.string.title_generic_exception_error)
    override fun messageBody() = resources.getString(R.string.body_generic_exception_error)
}