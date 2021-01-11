package by.jaaliska.weather.domain

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import by.jaaliska.weather.repository.server.YandexProvider
import by.jaaliska.weather.services.LocationServiceImpl


class MainViewModel<A : AppCompatActivity> (
        private val activity: A
) {

    private val locationService = LocationServiceImpl()
    private val yandexProvider = YandexProvider()
    fun getLocation() = locationService

    fun onCreate() {
        locationService.setLocation(activity)
        Handler().postDelayed({
            yandexProvider.setYandexWeather(
                    latitude = locationService.getLatitude(),
                    longitude = locationService.getLongitude()
            )
        }, 3000)
    }


}