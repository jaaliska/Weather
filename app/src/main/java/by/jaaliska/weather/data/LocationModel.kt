package by.jaaliska.weather.data

data class LocationModel(private var latitude: Double, private var longitude: Double) {
    fun getLatitude() = latitude
    fun getLongitude() = longitude

}