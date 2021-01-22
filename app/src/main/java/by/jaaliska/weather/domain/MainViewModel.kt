package by.jaaliska.weather.domain

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import by.jaaliska.weather.adatpers.ListViewAdapter
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.databinding.ActivityMainBinding
import by.jaaliska.weather.exceptions.LocationAccessDeniedException
import by.jaaliska.weather.services.LocationServiceImpl
import by.jaaliska.weather.services.WeatherAggregateService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class MainViewModel<A : AppCompatActivity>(
        private val activity: A,
        private val activityLayout: Int
): ViewModel() {
    private val locationService = LocationServiceImpl(activity)
    private val weatherAggregateService = WeatherAggregateService()
    var snackBars = PublishSubject.create<SnackBarInfo>()
    val temperature = ObservableField<String>()
    var disposables = CompositeDisposable()
    private lateinit var listViewAdapter: ListViewAdapter
    private var listWeatherModel: ObservableArrayList<WeatherModel> = ObservableArrayList()

    fun onCreate() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                activity,
                activityLayout
        )
        binding.vm = this
        listViewAdapter = ListViewAdapter(activity.applicationContext)
        listViewAdapter.listWeatherModel = listWeatherModel
        binding.listView.adapter = listViewAdapter
    }



    fun onRefreshClick() {
        listWeatherModel.clear()
        disposables.add(locationService
                .getLocationModel()
                .flatMap { location -> weatherAggregateService.getWeather(location) }
                .retryWhen { throwable -> throwable.take(3).delay(1, TimeUnit.SECONDS)}
                .subscribe(
                        {
                            listWeatherModel.add(it)
                            listViewAdapter.notifyDataSetChanged()
                            temperature.set(String.format("Average temperature is %.2f %n", getAverageTemperature()))
                            Log.i("viewModel", listWeatherModel.toString())
                        },
                        {
                            when (it) {
                                is LocationAccessDeniedException -> {
                                    snackBars.onNext(SnackBarInfo(
                                            it.message.toString(),
                                            "Settings",
                                            View.OnClickListener {
                                                onShowAppSettingsClick()
                                            }
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

    private fun onShowAppSettingsClick() {
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

    fun onDestroy() {
        disposables.dispose()
    }
}