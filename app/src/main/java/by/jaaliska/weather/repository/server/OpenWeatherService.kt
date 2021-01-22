package by.jaaliska.weather.repository.server

import by.jaaliska.weather.data.openWeather.OpenWeatherModel
import by.jaaliska.weather.data.yandexData.YandexWeatherModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("data/2.5/weather?")
    fun getWeatherDataByLocation(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("appid") hours: String,
        @Query("units") limit: String
    ): Observable<OpenWeatherModel>

    companion object Instance {
        private const val DOMAIN = "https://api.openweathermap.org/"

        private fun getRetrofit(): Retrofit {
            val okHttpClient = OkHttpClient()
            val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
            return retrofitBuilder.build()
        }

        fun getOpenWeatherApi(): OpenWeatherService {
            return getRetrofit().create(OpenWeatherService::class.java)
        }
    }


}