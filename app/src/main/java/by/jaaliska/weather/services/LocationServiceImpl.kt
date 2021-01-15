package by.jaaliska.weather.services

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import by.jaaliska.weather.data.LocationModel
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception

class LocationServiceImpl(private val context: Context): LocationService {

    override fun getLocationModel(): Observable<LocationModel> {
        val subject = AsyncSubject.create<LocationModel>()
        getLastLocation(subject)

        return subject
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(subject: AsyncSubject<LocationModel>) {
        LocationServices.getFusedLocationProviderClient(context).lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        subject.onError(Exception("location not found"))
                    } else {
                        subject.onNext(LocationModel(location.latitude, location.longitude))
                        subject.onComplete()
                    }
                }
    }
}