package by.jaaliska.weather.domain

import android.view.View

data class SnackBarInfo(
        val mainTextString: String,
        val actionString: String?,
        val listener: View.OnClickListener?
)
