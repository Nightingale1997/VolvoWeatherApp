package com.example.volvoweather

import android.widget.ImageView
import androidx.databinding.BindingAdapter

data class METJSONForecast(
    val geometry: PointGeometry,
    val properties: Forecast,
    val type: String = "Feature"
)

data class ForecastSummary(val symbol_code: String)

data class ForecastTimePeriod(
    val air_temperature_max: Double?,
    val air_temperature_min: Double?,
    val precipitation_amount: Double?,
    val precipitation_amount_max: Double?,
    val precipitation_amount_min: Double?,
    val probability_of_precipitation: Double?,
    val probability_of_thunder: Double?,
    val ultraviolet_index_clear_sky_max: Double?
)

data class ForecastUnits(
    val air_pressure_at_sea_level: String?,
    val air_temperature: String?,
    val air_temperature_max: String?,
    val air_temperature_min: String?,
    val cloud_area_fraction: String?,
    val cloud_area_fraction_high: String?,
    val cloud_area_fraction_low: String?,
    val cloud_area_fraction_medium: String?,
    val dew_point_temperature: String?,
    val fog_area_fraction: String?,
    val precipitation_amount: String?,
    val precipitation_amount_max: String?,
    val precipitation_amount_min: String?,
    val probability_of_precipitation: String?,
    val probability_of_thunder: String?,
    val relative_humidity: String?,
    val ultraviolet_index_clear_sky_max: String?,
    val wind_from_direction: String?,
    val wind_speed: String?,
    val wind_speed_of_gust: String?,
)

data class ForecastTimeInstant(
    val air_pressure_at_sea_level: Double?,
    val air_temperature: Double?,
    val cloud_area_fraction: Double?,
    val cloud_area_fraction_high: Double?,
    val cloud_area_fraction_low:Double?,
    val cloud_area_fraction_medium: Double?,
    val dew_point_temperature: Double?,
    val fog_area_fraction: Double?,
    val relative_humidity: Double?,
    val wind_from_direction: Double?,
    val wind_speed: Double?,
    val wind_speed_of_gust: Double?
)

data class ForecastTimeStep(
    val data: Inline_model_0,
    val time: String
)

data class Inline_model_0(
    val instant: Inline_model_1,
    val next_12_hours: Inline_model_2?,
    val next_1_hours: Inline_model_3?,
    val next_6_hours: Inline_model_4?
)


data class Inline_model_1(
    val details: ForecastTimeInstant?
)

data class Inline_model_2(
    val details: ForecastTimePeriod,
    val summary: ForecastSummary
)

data class Inline_model_3(
    val details: ForecastTimePeriod,
    val summary: ForecastSummary
)

data class Inline_model_4(
    val details: ForecastTimePeriod,
    val summary: ForecastSummary
)

data class Inline_model(
    val units: ForecastUnits,
    val updated_at: String
)

data class Forecast(
    val meta: Inline_model,
    val timeseries: Array<ForecastTimeStep>
)

data class PointGeometry(
    val coordinates: Array<Double>,
    val type: String = "Point"
)


@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}