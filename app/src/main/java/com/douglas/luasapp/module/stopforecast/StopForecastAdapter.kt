package com.douglas.luasapp.module.stopforecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.douglas.luasapp.R
import com.douglas.luasapp.domain.model.StopForecast

class StopForecastAdapter(var list: List<StopForecast> = arrayListOf()): RecyclerView.Adapter<StopForecastViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): StopForecastViewHolder {

        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_stop_forecast, viewGroup, false)

        return StopForecastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(view: StopForecastViewHolder, position: Int) {

        val stopForecast = list[position]

        view.populizeView(stopForecast)
    }
}