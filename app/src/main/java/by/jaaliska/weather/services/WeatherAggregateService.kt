package by.jaaliska.weather.services

import by.jaaliska.weather.data.LocationModel
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.repository.server.OpenWeatherProvider
import by.jaaliska.weather.repository.server.WeatherProvider
import by.jaaliska.weather.repository.server.YandexProvider
import io.reactivex.Observable

class WeatherAggregateService {

    private val listWeatherProviders = mutableListOf<WeatherProvider>(
        YandexProvider(),
        OpenWeatherProvider()
    )

    fun getWeather(location: LocationModel): Observable<WeatherModel> {
               return Observable.fromIterable(listWeatherProviders)
                       .flatMap {
                           provider -> provider.getWeather(location)
                           //.doOnError()
                       }
    }
}