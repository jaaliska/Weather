package by.jaaliska.weather.services

import by.jaaliska.weather.data.LocationModel
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.repository.server.OpenWeatherProvider
import by.jaaliska.weather.repository.server.WeatherProvider
import by.jaaliska.weather.repository.server.YandexProvider
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.security.auth.Subject

class WeatherAggregateService {

    private val listWeatherProviders = mutableListOf<WeatherProvider>(
        YandexProvider(),
        OpenWeatherProvider()
    )

    val subject: PublishSubject<WeatherModel> = PublishSubject.create()


    fun getWeather(location: LocationModel): Observable<WeatherModel> {
//        val listOvsWeather = listWeatherProviders
//            .map { weatherProvider -> weatherProvider.getWeather(location) }
//        return Observable.merge(listOvsWeather)
//            .toList()
//            .toObservable()


//        Observable.merge(
//            listWeatherProviders
//                    .map { weatherProvider -> weatherProvider.getWeather(location) }
//        ).cast()


               return Observable.fromIterable(listWeatherProviders)
                       .flatMap {
                           provider -> provider.getWeather(location)
                           //.doOnError()
                       }
//
        //       val yp = YandexProvider().getWeather(location)
        //               .mergeWith(OpenWeatherProvider().getWeather(location))
//
//
        //       return Observable
        //               .merge(
        //                       YandexProvider().getWeather(location),
        //                       OpenWeatherProvider().getWeather(location)
        //               ).toList()
        //

    }
}