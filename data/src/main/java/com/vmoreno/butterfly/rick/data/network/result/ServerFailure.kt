package com.vmoreno.butterfly.rick.data.network.result

import com.squareup.moshi.Moshi
import retrofit2.HttpException

class ServerFailure : Failure.FeatureFailure() {

    object InvalidValueForQueryParameter : FeatureFailure()

    companion object {
        fun serverFailure(httpException: HttpException) =
            httpException.response()?.errorBody()?.let {
                Moshi.Builder().build().adapter(ErrorServiceResponse::class.java).fromJson(it.string())
                    ?.run(Companion::mapErrorToServerFailure) ?: UnknownException
            } ?: UnknownException

        private fun mapErrorToServerFailure(errorServiceResponse: ErrorServiceResponse) =
            with(errorServiceResponse) {
                if (status == BAD_REQUEST && error == ErrorType.INVALID_QUERY.name) InvalidValueForQueryParameter
                else UnknownException
            }
    }
}