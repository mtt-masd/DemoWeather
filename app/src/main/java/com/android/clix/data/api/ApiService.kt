package com.example.android.codelabs.network

import ServiceApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val BASE_URL = "https://api.openweathermap.org/"

    private var retrofit: Retrofit? = null

    private fun getClient(): Retrofit? {
        if (retrofit == null) {
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val request = original.newBuilder()
                    .method(original.method, original.body)
                    .build()
                val response = chain.proceed(request)
                if (response.code in 201..500) {
                    val responseBuilder: Response.Builder = Response.Builder()
                    responseBuilder.code(200)
                    responseBuilder.protocol(response.protocol)
                    responseBuilder.headers(response.headers)
                    responseBuilder.body(response.body)
                    responseBuilder.message(response.message)
                    responseBuilder.networkResponse(response.networkResponse)
                    responseBuilder.handshake(response.handshake)
                    responseBuilder.request(response.request)

                    return@Interceptor responseBuilder.build()
                } else {
                    return@Interceptor response
                }
            })

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.interceptors().add(logging)

            val gson: Gson = GsonBuilder()
                .setLenient()
                .create()
            val client: OkHttpClient = httpClient.build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }

    fun getAPIServiceAuthorization(): ServiceApi? {
        return getClient()?.create(ServiceApi::class.java)
    }
}