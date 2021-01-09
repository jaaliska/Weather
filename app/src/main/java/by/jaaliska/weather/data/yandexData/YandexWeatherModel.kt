package by.jaaliska.weather.data.yandexData

class YandexWeatherModel (
    private val fact: Fact?,
    private val geo_object: GeoObject?
    ) {

    fun getTemp() = fact?.getTemp()
    fun getCity() = geo_object?.getLocality()?.getName()
}

