package com.example.wildfireslive.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val BASE_URL: String = "https://eonet.gsfc.nasa.gov"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit by lazy{
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()
}


interface EonetApiService {

    @GET("api/v3/events")
    @Headers("Accept: application/json")
    suspend fun getWildFires(
        @Query("category")
        eventCategory: String = "wildfires"
    ): Response<WildFiresResponse>
}

object EonetApi {
    val retrofitService: EonetApiService by lazy {
        retrofit.create(EonetApiService::class.java)
    }
}



