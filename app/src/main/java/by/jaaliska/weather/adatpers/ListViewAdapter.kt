package by.jaaliska.weather.adatpers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import by.jaaliska.weather.data.WeatherModel
import by.jaaliska.weather.databinding.MainListViewBinding

class ListViewAdapter(private val context: Context,
                      private val listWeatherModel: ObservableArrayList<WeatherModel>): BaseAdapter() {
   private val listener = getOnListChangedCallback()


    init{
        listWeatherModel.addOnListChangedCallback(listener)
    }

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

    fun onObservableListChange() {
        notifyDataSetChanged()
    }

    fun onDestroy() {
        listWeatherModel.removeOnListChangedCallback(listener)
    }

    private fun getOnListChangedCallback(): ObservableList.OnListChangedCallback<ObservableList<WeatherModel>> {
        return object : ObservableList.OnListChangedCallback<ObservableList<WeatherModel>>() {
            override fun onChanged(sender: ObservableList<WeatherModel>?) {
                onObservableListChange()
            }

            override fun onItemRangeChanged(
                sender: ObservableList<WeatherModel>?,
                positionStart: Int,
                itemCount: Int
            ) {
                onObservableListChange()
            }

            override fun onItemRangeInserted(
                sender: ObservableList<WeatherModel>?,
                positionStart: Int,
                itemCount: Int
            ) {
                onObservableListChange()
            }

            override fun onItemRangeMoved(
                sender: ObservableList<WeatherModel>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                onObservableListChange()
            }

            override fun onItemRangeRemoved(
                sender: ObservableList<WeatherModel>?,
                positionStart: Int,
                itemCount: Int
            ) {
                onObservableListChange()
            }
        }
    }
}