package by.jaaliska.weather.repository.server

import android.annotation.SuppressLint
import android.util.Log
import by.jaaliska.weather.data.LocationModel
import by.jaaliska.weather.data.WeatherModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class YandexProvider {
    ///общий интерфейс и Adapter привести к общей модели
    private val yandexWeatherService: YandexWeatherService = YandexWeatherService.getApiYandexWeather()

    fun getWeather(location: LocationModel): Observable<WeatherModel> {
        return yandexWeatherService.getWeatherDataByCity(
            location.getLatitude(),
            location.getLongitude(),
                false,
                1
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { yw -> WeatherModel(yw.getTemp().toDouble()) }

    }
}