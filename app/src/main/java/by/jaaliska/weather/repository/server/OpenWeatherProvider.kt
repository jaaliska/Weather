package by.jaaliska.weather.repository.server

import by.jaaliska.weather.data.LocationModel
import by.jaaliska.weather.data.WeatherModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OpenWeatherProvider : WeatherProvider {

    private val openWeatherService: OpenWeatherService = OpenWeatherService.getOpenWeatherApi()

    override fun getWeather(location: LocationModel): Observable<WeatherModel> {
        return openWeatherService.getWeatherDataByLocation(
            location.getLatitude(),
            location.getLongitude(),
            "60ee362b89fd4b6368ea3f8dc097a064",
            "metric"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { ow -> WeatherModel("Open Weather", ow.getTemp()) }
    }
}