package com.vmoreno.butterfly.rick.domain.messagesexception

import com.vmoreno.butterfly.rick.domain.utils.EMPTY_STRING

data class MessageExceptionInfo(
    val title: String = EMPTY_STRING,
    val message: String = EMPTY_STRING,
    val error: Throwable,
    val params: HashMap<String, Any>? = null
)