package by.jaaliska.weather.data.yandexData

data class GeoObject(private val locality: Locality?) {
    fun getLocality() = locality
}