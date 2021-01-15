package by.jaaliska.weather.services

import by.jaaliska.weather.data.LocationModel
import io.reactivex.Observable

interface LocationService {
    fun getLocationModel(): Observable<LocationModel>

//    fun onRequestPermissionsResult(
//            requestCode: Int, permissions: Array<String>,
//            grantResults: IntArray)
}