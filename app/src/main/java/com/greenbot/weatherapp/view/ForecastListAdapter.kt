package com.greenbot.weatherapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.greenbot.weatherapp.R
import com.greenbot.weatherapp.model.ForecastViewData

class ForecastListAdapter : RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder>() {

    private val data = mutableListOf<ForecastViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(viewHolder: ForecastViewHolder, position: Int) {
        viewHolder.bind(data[position])
    }

    fun setForecastList(forecastList: List<ForecastViewData>) {
        data.clear()
        data.addAll(forecastList)
    }

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val forecastDay: TextView = itemView.findViewById(R.id.forecast_tv_day)
        val forecastTemp: TextView = itemView.findViewById(R.id.forecast_tv_temp)

        fun bind(forecast: ForecastViewData) {
            forecastDay.text = forecast.day
            forecastTemp.text = forecast.avgTemp
        }

    }
}