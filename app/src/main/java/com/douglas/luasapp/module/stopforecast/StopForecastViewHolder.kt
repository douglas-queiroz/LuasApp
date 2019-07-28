package com.douglas.luasapp.module.stopforecast

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.douglas.luasapp.R
import com.douglas.luasapp.domain.model.StopForecast

class StopForecastViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    private val destinationText = view.findViewById<TextView>(R.id.destination_text)
    private val dueText = view.findViewById<TextView>(R.id.due_text)
    private val direction = view.findViewById<TextView>(R.id.direction_text)

    fun populizeView(stopForecast: StopForecast) {

        destinationText.text = stopForecast.destination
        dueText.text = formatDue(stopForecast.due)
        direction.text = stopForecast.direction
    }

    private fun formatDue(due: String?): String? {

        return view.context
            .resources
            .getString(R.string.item_stop_forecast_due, due)

    }
}