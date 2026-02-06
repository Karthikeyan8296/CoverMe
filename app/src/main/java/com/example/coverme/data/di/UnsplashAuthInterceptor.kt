package com.example.coverme.data.di

import okhttp3.Interceptor
import okhttp3.Response

class UnsplashAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Client-ID 8ZHCmDMoRsSKaGa0EUseIb9BBxElnEVaHKqzWsH66y8"
            ).build()

        return chain.proceed(request)
    }

}