package by.jaaliska.weather.adatpers

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.databinding.MainListViewBinding


class WeatherModelAdapter : RecyclerView.Adapter<WeatherModelAdapter.WeatherModelViewHolder>() {

    private var listWeatherModel = mutableListOf<WeatherModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherModelViewHolder {
        val employeeListItemBinding: MainListViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), 50,////////////????????????????????
            parent, false
        )
        return WeatherModelViewHolder(employeeListItemBinding)
    }

    override fun onBindViewHolder(holder: WeatherModelViewHolder, position: Int) {
        val currentWeatherModel = listWeatherModel[position]

    }

    override fun getItemCount(): Int {
        return listWeatherModel.size
    }

    fun setListWeatherModel(list: MutableList<WeatherModel>) {
        listWeatherModel = list
        notifyDataSetChanged()
    }

    class WeatherModelViewHolder(private val mainListViewBinding: MainListViewBinding) :
        RecyclerView.ViewHolder(mainListViewBinding.root) {
        private val listViewBinding: MainListViewBinding = mainListViewBinding

    }
}