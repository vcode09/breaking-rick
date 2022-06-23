package com.vmoreno.butterfly.rick.domain.exceptions

import com.vmoreno.butterfly.rick.domain.utils.EMPTY_STRING

class NotificationNotFoundException(message: String = EMPTY_STRING) : RuntimeException(message)
