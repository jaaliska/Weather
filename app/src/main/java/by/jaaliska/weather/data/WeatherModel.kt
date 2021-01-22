package by.jaaliska.weather.data

data class WeatherModel(val whetherProviderName: String, val temperature: Double) {
    fun getTemperature() = temperature.toString()
}