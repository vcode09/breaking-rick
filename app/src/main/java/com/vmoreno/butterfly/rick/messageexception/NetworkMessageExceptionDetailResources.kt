package com.vmoreno.butterfly.rick.messageexception

import android.content.res.Resources
import com.vmoreno.butterfly.rick.domain.messagesexception.NetworkMessageExceptionResources
import com.vmoreno.butterfly.rick.R

class NetworkMessageExceptionDetailResources(
    private val resources: Resources
) : MessageExceptionDetailResources(resources), NetworkMessageExceptionResources {
    override fun connectionTitle() = resources.getString(R.string.title_network_exception_error)
    override fun connectionBody() = resources.getString(R.string.body_network_exception_error)
}