package by.jaaliska.weather.repository.server

import by.jaaliska.weather.data.LocationModel
import by.jaaliska.weather.data.WeatherModel
import io.reactivex.Observable

interface WeatherProvider {
    fun getWeather(location: LocationModel): Observable<WeatherModel>
}