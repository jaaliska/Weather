package by.jaaliska.weather.domain

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.exceptions.LocationAccessDeniedException
import by.jaaliska.weather.services.LocationServiceImpl
import by.jaaliska.weather.services.WeatherAggregateService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class MainViewModel: ViewModel() {
    private val locationService = LocationServiceImpl()
    private val weatherAggregateService = WeatherAggregateService()
    var snackBars = PublishSubject.create<SnackBarInfo>()
    val temperature = ObservableField<String>()
    var disposables = CompositeDisposable()

        //replace to LiveData
    private var listWeatherModel: ObservableArrayList<WeatherModel> = ObservableArrayList()

    init {
        Log.i("MainViewModel", "ViewModel created")
    }

    fun getListAdapter() = listWeatherModel

    fun onRefreshClick(activity: AppCompatActivity) {
        listWeatherModel.clear()
        disposables.add(locationService
                .getLocationModel(activity)
                .flatMap { location -> weatherAggregateService.getWeather(location) }
                .subscribe(
                        {
                            listWeatherModel.add(it)
                            temperature.set(String.format("Average temperature is %.2f %n", getAverageTemperature()))
                            Log.i("viewModel", listWeatherModel.toString())
                        },
                        {
                            when (it) {
                                is LocationAccessDeniedException -> {
                                    snackBars.onNext(SnackBarInfo(
                                            it.message.toString(),
                                            "Settings",
                                            ::onShowAppSettingsClick
                                    ))
                                }
                            }
                        }
                ))
    }

    private fun getAverageTemperature(): Double {
        var sum = 0.0
        for (wm in listWeatherModel) {
            sum += wm.temperature
        }
        return sum/listWeatherModel.size
    }

    private fun onShowAppSettingsClick(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
                "package", activity.packageName,
                null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}