package by.jaaliska.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import by.jaaliska.weather.R
import by.jaaliska.weather.domain.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.onCreate()
    }


    fun showSnackbar(
            mainTextString: String, actionString: String,
            listener: View.OnClickListener
    ) {
        val contextView = findViewById<View>(R.id.context_view)
        Snackbar.make(contextView, mainTextString, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionString) {
                    listener.onClick(contextView)
                }
                .show()
    }


    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        viewModel.getLocation().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}