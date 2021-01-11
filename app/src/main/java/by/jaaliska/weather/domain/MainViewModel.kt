package by.jaaliska.weather.domain

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import by.jaaliska.weather.repository.server.YandexProvider
import by.jaaliska.weather.services.LocationServiceImpl


class MainViewModel<A : AppCompatActivity>(
        private val activity: A
) {

    private val locationService = LocationServiceImpl()
    private val yandexProvider = YandexProvider()

    fun onCreate() {
        locationService.setLocation(activity)
        Handler().postDelayed({
            setWeather()
        }, 3000)
    }

    fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        locationService.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setWeather() {
        yandexProvider.setYandexWeather(
                latitude = locationService.getLatitude(),
                longitude = locationService.getLongitude()
        )
    }
}