package com.example.volvoweather

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("compact?")
    fun getWeather(@Header("User-Agent") userAgent : String, @Query(value="lat", encoded=true) lat: Double, @Query(value="lon", encoded=true) lon:  Double) : Call<METJSONForecast>

    companion object {

        var BASE_URL = "https://api.met.no/weatherapi/locationforecast/2.0/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}