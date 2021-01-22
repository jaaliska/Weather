package by.jaaliska.weather.data.openWeather

data class OpenWeatherModel(
    private val main: Main
) {

    fun getTemp() = main.getTemp()
}
