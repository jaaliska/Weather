package by.jaaliska.weather.data.yandexData

class YandexWeatherModel(
    private val fact: Fact
) {

    fun getTemp() = fact.getTemp()
}

