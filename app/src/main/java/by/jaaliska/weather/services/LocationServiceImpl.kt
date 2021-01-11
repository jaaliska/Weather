package by.jaaliska.weather.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import by.jaaliska.weather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar

class LocationServiceImpl : LocationService {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var activity: Activity

    override fun getLongitude(): Double {
        if (longitude != null) {
            return longitude as Double
        } else {
            throw ExceptionInInitializerError("Longitude is not initialised")
        }
    }

    override fun getLatitude(): Double {
        if (latitude != null) {
            return latitude as Double
        } else {
            throw ExceptionInInitializerError("Latitude is not initialised")
        }
    }

    fun setLocation(activity: Activity) {
        this.activity = activity
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient!!.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        Log.i(TAG, "------------------>>>>>>>>>>>>>location is null")
                        showSnackbar(
                                "Impossible to determine location. Try again later",
                                "update"
                        ) {
                            getLastLocation()
                        }
                        //return Error
                    } else {
                        setLatAndLon(location.latitude, location.longitude)
                        Log.i(TAG, "-------------------------->>>>>>Local is \"$latitude and $longitude\"")
                    }
                }
    }

    private fun setLatAndLon(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                    View.OnClickListener {
                        startLocationPermissionRequest()
                    })
        } else {
            startLocationPermissionRequest()
        }
    }

    private fun showSnackbar(
            mainTextString: String, actionString: String,
            listener: View.OnClickListener
    ) {
        val contextView = activity.findViewById<View>(R.id.context_view)
        Snackbar.make(contextView, mainTextString, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionString) {
                    listener.onClick(contextView)
                }
                .show()
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    getLastLocation()
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                            View.OnClickListener {
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
                    )
                }
            }
        }
    }

    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}