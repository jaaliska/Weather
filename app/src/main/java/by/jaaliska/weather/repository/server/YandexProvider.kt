package by.jaaliska.weather.repository.server

import android.annotation.SuppressLint
import android.util.Log
import by.jaaliska.weather.data.yandexData.YandexWeatherModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class YandexProvider {
    private val yandexWeatherService: YandexWeatherService = YandexWeatherService.getApiYandexWeather()

    @SuppressLint("CheckResult")
    fun setYandexWeather(latitude: Double, longitude: Double) {
        yandexWeatherService.getWeatherDataByCity(
                latitude,
                longitude,
                false,
                1
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<YandexWeatherModel> {
                    override fun accept(t: YandexWeatherModel?) {
                        Log.i(
                                "YANDEX ",
                                "temperature:" + t?.getTemp().toString() + " city: " + t?.getCity()
                        )
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        Log.i("YANDEX EXCEPTION ", t?.message.toString())
                    }
                }
                )
    }
}