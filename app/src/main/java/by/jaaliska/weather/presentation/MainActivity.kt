package by.jaaliska.weather.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import by.jaaliska.weather.R
import by.jaaliska.weather.data.yandexData.YandexWeatherModel
import by.jaaliska.weather.repository.server.YandexWeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val yandexWeatherService: YandexWeatherService = YandexWeatherService.getApiYandexWeather()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        yandexWeatherService.getWeatherDataByCity(
            55.751244F,
            37.618423F,
                false,
                1
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<YandexWeatherModel> {
                override fun accept(t: YandexWeatherModel?) {
                    Toast.makeText(
                        this@MainActivity,
                        t?.getTemp().toString() +" " + t?.getCity(),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.i("ПОЛУЧЕНО ------------->>>>>>>>: ", t?.getTemp().toString() +" " + t?.getCity())
                }
            }, object :Consumer<Throwable> {
                override fun accept(t: Throwable?) {
                    Log.i("ВНИМАНИЕ ОШИБКА: ", t?.message.toString())
                }
            }
            )
    }
}