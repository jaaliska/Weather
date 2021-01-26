package by.jaaliska.weather.domain

import android.app.Activity

data class SnackBarInfo(
        val mainTextString: String,
        val actionString: String?,
        val actionCallback: (Activity) -> Unit
)
