package by.jaaliska.weather.presentation

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
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

    private val viewModel: MainViewModel by viewModels()
    private lateinit var snackBars: Disposable
    private lateinit var listViewAdapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.vm = viewModel
        listViewAdapter = ListViewAdapter(applicationContext, viewModel.getListAdapter())
        binding.listView.adapter = listViewAdapter

        snackBars = viewModel.snackBars.subscribe(
                Consumer {
                    showSnackbar(it.mainTextString, it.actionString, it.actionCallback)
                }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_main_menu) {
            viewModel.onRefreshClick(this)
        }
        return true
    }

    private fun showSnackbar(
        mainTextString: String,
        actionString: String?,
        actionCallback: ((Activity) -> Unit)?
    ) {
        val contextView = findViewById<View>(R.id.main_activity_view)
        val sb = Snackbar.make(contextView, mainTextString, Snackbar.LENGTH_LONG)
        if (actionString != null && actionCallback != null) {
            sb.setAction(actionString) {
                actionCallback(this)
            }
        }
        sb.show()
    }

    override fun onDestroy() {
        snackBars.dispose()
        listViewAdapter.onDestroy()
        super.onDestroy()
    }
}