package com.example.volvoweather.ui.main

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.volvoweather.ApiInterface
import com.example.volvoweather.ForecastTimeStep
import com.example.volvoweather.METJSONForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.round
import java.time.LocalDate
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherData = MutableLiveData<METJSONForecast>()
    val weatherData: LiveData<METJSONForecast>
        get() = _weatherData

    private val _forecastWeek = MutableLiveData<ArrayList<ForecastDay>>().apply {
        postValue(
            arrayListOf(getForecastDay(), getForecastDay(), getForecastDay(), getForecastDay(), getForecastDay(), getForecastDay(), getForecastDay())
        )
    }
    val forecastWeek: LiveData<ArrayList<ForecastDay>>
        get() = _forecastWeek

    fun fetchWeather(lat: Double, lon: Double) {
        val userAgent =
            "i3tex.com kevin.solovjov@togethertech.com " + System.getProperty("http.agent")
        val apiInterface = ApiInterface.create().getWeather(userAgent, lat, lon)

        apiInterface.enqueue(object : Callback<METJSONForecast> {
            override fun onResponse(
                call: Call<METJSONForecast>?,
                response: Response<METJSONForecast>?
            ) {
                if (response?.body() != null) {
                    _weatherData.value = response.body()!!
                    var weekDayList: ArrayList<ForecastDay> = ArrayList()

                    for (i in 0..6) {
                        weekDayList.add(getForecastDay(i))
                    }

                    _forecastWeek.value = weekDayList

                } else if (response?.errorBody() != null) {
                    Log.v("Error", response?.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<METJSONForecast>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

    fun getStringByIdName(context: Context, idName: String): String {
        val res: Resources = context.resources
        return res.getString(res.getIdentifier(idName, "string", context.packageName))
    }

    fun getDrawableByIdName(context: Context, idName: String): Drawable {
        val res: Resources = context.resources
        return res.getDrawable(res.getIdentifier(idName, "drawable", context.packageName))
    }


    fun getForecastDay(daysOffset: Int = 0): ForecastDay {


        if (weatherData.value != null) {
            val forecastDate = LocalDate.now().plusDays(daysOffset.toLong())
            var dataDay: ForecastTimeStep? = null

            if(daysOffset > 0) {
                val matchingDays = weatherData.value?.properties?.timeseries?.filter{ day ->
                    LocalDate.parse(
                        day.time.substring(
                            0,
                            10
                        )
                    ) == forecastDate
                }
                if(matchingDays != null){
                    dataDay = matchingDays[(matchingDays.size.toDouble() / 2).roundToInt()-1]
                }
            }
            else{
                dataDay = weatherData.value?.properties?.timeseries!![1]
            }


            if (dataDay != null) {
                val temperature =
                    dataDay.data.instant.details?.air_temperature!!.toBigDecimal()
                        .toPlainString() + " °C"
                val wind =
                    dataDay.data.instant.details?.wind_speed!!.toBigDecimal()
                        .toPlainString() + " m/s"

                var symbolCode = ""
                if (dataDay.data.next_1_hours != null) {
                    symbolCode = dataDay.data.next_1_hours?.summary!!.symbol_code
                } else if (dataDay.data.next_6_hours != null) {
                    symbolCode = dataDay.data.next_6_hours?.summary!!.symbol_code
                } else {
                    symbolCode = dataDay.data.next_12_hours?.summary!!.symbol_code
                }

                val context = getApplication<Application>().applicationContext

                val image = getDrawableByIdName(context, symbolCode)

                val description = getStringByIdName(context, symbolCode.substringBefore("_"))

                val dateString =
                    dataDay.time
                var date = LocalDate.parse(dateString.substring(0, 10)).dayOfWeek.name.toLowerCase()
                    .capitalize() + " " + dateString.substring(11, 16)

                if(daysOffset > 0){
                    date = date.substring(0, 3)
                }

                return ForecastDay(wind, temperature, description, date, image)
            } else return ForecastDay("", "", "No Forecast", "", null)


        } else return ForecastDay("", "", "Loading", "", null)

    }

    data class ForecastDay(
        val wind: String,
        val temperature: String,
        val description: String,
        val date: String,
        val image: Drawable?
    )


}
