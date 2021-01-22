package by.jaaliska.weather.data.openWeather


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    private val temp: Double
) {
    fun getTemp() = temp
}