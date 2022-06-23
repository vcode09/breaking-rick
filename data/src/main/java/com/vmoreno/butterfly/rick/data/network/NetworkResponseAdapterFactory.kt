package com.vmoreno.butterfly.rick.data.network

import com.vmoreno.butterfly.rick.data.utils.ResponseHandler
import com.vmoreno.butterfly.rick.data.utils.extractNetworkResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {

        check(returnType is ParameterizedType) { "$returnType must be parameterized. Raw types are not supported" }

        val containerType = getParameterUpperBound(0, returnType)
        if (getRawType(containerType) != NetworkResponse::class.java) {
            return null
        }

        check(containerType is ParameterizedType) { "$containerType must be parameterized. Raw types are not supported" }

        val (successBodyType, errorBodyType) = containerType.getBodyTypes()
        val errorBodyConverter = retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

        return when (getRawType(returnType)) {
            Deferred::class.java -> {
                DeferredNetworkResponseAdapter<Any, Any>(successBodyType, errorBodyConverter)
            }
            else -> null
        }
    }

    private fun ParameterizedType.getBodyTypes(): Pair<Type, Type> {
        val successType = getParameterUpperBound(0, this)
        val errorType = getParameterUpperBound(1, this)
        return successType to errorType
    }

    private class DeferredNetworkResponseAdapter<T : Any, U : Any>(
        private val successBodyType: Type,
        private val errorConverter: Converter<ResponseBody, U>
    ) : CallAdapter<T, Deferred<NetworkResponse<T, U>>> {

        override fun responseType(): Type = successBodyType

        override fun adapt(call: Call<T>): Deferred<NetworkResponse<T, U>> {
            val deferred = CompletableDeferred<NetworkResponse<T, U>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    try {
                        val networkResponse = throwable.extractNetworkResponse<T, U>(errorConverter)
                        deferred.complete(networkResponse)
                    } catch (t: Throwable) {
                        deferred.completeExceptionally(t)
                    }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val networkResponse =
                        ResponseHandler.handle(response, successBodyType, errorConverter)
                    deferred.complete(networkResponse)
                }
            })

            return deferred
        }
    }
}