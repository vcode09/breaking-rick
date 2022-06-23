package com.vmoreno.butterfly.rick.data.network.result

data class ErrorServiceResponse (
    val error: String? = null,
    val message: String? = null,
    val status: Int? = null
)