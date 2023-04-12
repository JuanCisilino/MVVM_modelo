package com.frost.model_mvvm.module

import com.frost.model_mvvm.repository.CurrencyRepository
import com.frost.model_mvvm.service.CurrencyApi
import com.frost.model_mvvm.uc.CurrencyUseCase
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
                    .header("Authorization", "Bearer API_KEY")
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