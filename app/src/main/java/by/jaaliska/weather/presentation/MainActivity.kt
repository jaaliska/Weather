package by.jaaliska.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.jaaliska.weather.R
import by.jaaliska.weather.domain.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.onCreate()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        Log.i("MainActivity",
                "---------------------->>>> onRequestPermissionsResult")
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}