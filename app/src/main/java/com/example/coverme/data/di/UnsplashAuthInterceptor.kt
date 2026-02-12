package com.example.coverme.data.di

import okhttp3.Interceptor
import okhttp3.Response

class UnsplashAuthInterceptor: Interceptor {
    //interceptor used to handle the api related attributes, like header, bearer key like that.. to pass on all api
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Client-ID 8ZHCmDMoRsSKaGa0EUseIb9BBxElnEVaHKqzWsH66y8"
            ).build()

        return chain.proceed(request)
    }
}