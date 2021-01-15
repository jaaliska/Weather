package by.jaaliska.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import by.jaaliska.weather.R
import by.jaaliska.weather.domain.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(this)
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.onCreate()

        text = findViewById(R.id.hello_world)
        text.setOnClickListener {
            viewModel.onRefreshClick()
        }

        viewModel.weather.subscribe(
                { weatherModel -> text.text = weatherModel.temperature.toString() },
                { err -> text.text = "Error" + err.message}
        )
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