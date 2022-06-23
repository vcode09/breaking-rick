package com.vmoreno.butterfly.rick.data.utils

import com.vmoreno.butterfly.rick.data.network.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.lang.reflect.Type

internal object ResponseHandler {
    fun <S : Any, E : Any> handle(
        response: Response<S>,
        successBodyType: Type,
        errorConverter: Converter<ResponseBody, E>
    ): NetworkResponse<S, E> {
        val body = response.body()
        val headers = response.headers()
        val code = response.code()
        val errorBody = response.errorBody()

        return if (response.isSuccessful) {
            if (body != null) {
                NetworkResponse.Success(body, headers)
            } else {
                if (successBodyType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    NetworkResponse.Success(Unit, headers) as NetworkResponse<S, E>
                } else {
                    NetworkResponse.ServerError(null, code, headers)
                }
            }
        } else {
            val networkResponse: NetworkResponse<S, E> = try {
                val convertedBody = if (errorBody == null) {
                    null
                } else {
                    errorConverter.convert(errorBody)
                }
                NetworkResponse.ServerError(convertedBody, code, headers)
            } catch (ex: Exception) {
                NetworkResponse.UnknownError(ex, code = code, headers = headers)
            }
            networkResponse
        }
    }
}