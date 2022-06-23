package com.vmoreno.butterfly.rick.domain.utils

fun String?.value(default: String = EMPTY_STRING): String = this ?: default