package com.frost.ModelMVVM.repository

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //rewrite the request to add bearer token
        val token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjQ2MjQxMzUsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJqdWFuLmNpc2lsaW5vQGdtYWlsLmNvbSJ9.Xvbx71WwEAxYAiIOqfyiSKeeogesXyxuvv1ZEI5aZepFSuKKI7BUxgHAVLGYe_HW6nTTkU_9tC8X5jNNSOyJwA"
        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "BEARER $token")
            .build()

        return chain.proceed(newRequest)
    }
}