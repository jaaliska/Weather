package by.jaaliska.weather.services

import androidx.appcompat.app.AppCompatActivity
import by.jaaliska.weather.data.LocationModel
import io.reactivex.Observable

interface LocationService {
    fun getLocationModel(activity: AppCompatActivity): Observable<LocationModel>
}