package by.jaaliska.weather.domain

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.jaaliska.weather.data.LocationModel
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.repository.server.YandexProvider
import by.jaaliska.weather.services.LocationServiceImpl
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject


class MainViewModel<A : AppCompatActivity>(
    private val activity: A
) {
    private val locationService = LocationServiceImpl(activity)
    private val yandexProvider = YandexProvider()

    val weather = ReplaySubject.create<WeatherModel>()


    fun onCreate() {

    }

    fun onRefreshClick(

    ) {

        locationService
            .getLocationModel()
            .flatMap{location -> yandexProvider.getWeather(location)}
            .subscribe(
                Consumer { weather.onNext(it) },
                Consumer { Log.i("ViewModel", "${it.message}") }
            )

    }


    fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        // locationService.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}