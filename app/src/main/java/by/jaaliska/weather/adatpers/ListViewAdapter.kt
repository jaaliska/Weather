package by.jaaliska.weather.adatpers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.ObservableArrayList
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.databinding.MainListViewBinding

class ListViewAdapter(private val context: Context): BaseAdapter() {
    var listWeatherModel: ObservableArrayList<WeatherModel> = ObservableArrayList()

    override fun getCount(): Int = listWeatherModel.size

    override fun getItem(position: Int): Any = listWeatherModel[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: MainListViewBinding
        if (convertView == null) {
            binding = MainListViewBinding.inflate(
                LayoutInflater.from(context), parent, false )
            binding.root.tag = binding
            Log.i("viewAdapter", binding.root.toString())
        } else {
            binding = convertView.tag as MainListViewBinding
        }
        binding.model = getItem(position) as WeatherModel
        return binding.root
    }
}