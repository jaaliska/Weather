package by.jaaliska.weather.services

interface LocationService {
    fun getLongitude(): Double
    fun getLatitude(): Double
    fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray)
}