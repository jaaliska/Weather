package by.jaaliska.weather.presentation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import by.jaaliska.weather.R
import by.jaaliska.weather.adatpers.ListViewAdapter
import by.jaaliska.weather.databinding.ActivityMainBinding
import by.jaaliska.weather.domain.MainViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(this, R.layout.activity_main)
    private lateinit var snackBars: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        snackBars = viewModel.snackBars.subscribe(
                Consumer {
                    showSnackbar(it.mainTextString, it.actionString, it.listener)
                }
        )
        viewModel.onCreate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_main_menu) {
            viewModel.onRefreshClick()
        }
        return true
    }

    private fun showSnackbar(
        mainTextString: String,
        actionString: String?,
        listener: View.OnClickListener?
    ) {
        val contextView = findViewById<View>(R.id.main_activity_view)
        val sb = Snackbar.make(contextView, mainTextString, Snackbar.LENGTH_LONG)
        if (actionString != null && listener != null) {
            sb.setAction(actionString) {
                listener.onClick(contextView)
            }
        }
        sb.show()
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        snackBars.dispose()
        super.onDestroy()
    }
}