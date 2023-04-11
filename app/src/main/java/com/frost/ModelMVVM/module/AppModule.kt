package com.frost.ModelMVVM.module

import androidx.viewbinding.BuildConfig
import com.frost.ModelMVVM.repository.CurrencyRepository
import com.frost.ModelMVVM.service.CurrencyApi
import com.frost.ModelMVVM.uc.CurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): CurrencyApi {

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTI3NzMxNDMsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJqdWFuLmNpc2lsaW5vQGdtYWlsLmNvbSJ9.Wl7_vGAgHGI92jDn-h-TTViLDoIv0ry7m2wVgNkzwwF0_MT9pGG9fuOo84xFjl6sNginST_mWr4vqQRyxs7czQ")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.estadisticasbcra.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(api: CurrencyApi) = CurrencyRepository(api)

    @Provides
    @Singleton
    fun provideUC(repo: CurrencyRepository) = CurrencyUseCase(repo)

}