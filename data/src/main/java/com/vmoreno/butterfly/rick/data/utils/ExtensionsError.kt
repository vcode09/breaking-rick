package com.vmoreno.butterfly.rick.data.utils

import com.vmoreno.butterfly.rick.data.network.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException

internal const val UNKNOWN_ERROR_RESPONSE_CODE = 520

internal fun <E : Any> HttpException.extractFromHttpException(
    errorConverter: Converter<ResponseBody, E>
): NetworkResponse.ServerError<E> {
    val error = response()?.errorBody()
    val responseCode = response()?.code() ?: UNKNOWN_ERROR_RESPONSE_CODE
    val headers = response()?.headers()
    val errorBody = when {
        error == null -> null
        error.contentLength() == 0L -> null
        else -> try {
            errorConverter.convert(error)
        } catch (e: Exception) {
            return NetworkResponse.ServerError(null, responseCode, headers)
        }
    }
    return NetworkResponse.ServerError(errorBody, responseCode, headers)
}

internal fun <S : Any, E : Any> Throwable.extractNetworkResponse(
    errorConverter: Converter<ResponseBody, E>
): NetworkResponse<S, E> {
    return when (this) {
        is EOFException -> NetworkResponse.UnknownError(this)
        is IOException -> NetworkResponse.NetworkError(this)
        is HttpException -> extractFromHttpException(errorConverter)
        else -> NetworkResponse.UnknownError(this)
    }
}