package com.douglas.luasapp.module.stopforecast

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.douglas.luasapp.R
import com.douglas.luasapp.domain.model.Stop
import com.douglas.luasapp.domain.model.StopForecast
import com.douglas.luasapp.module.base.BaseActivity
import kotlinx.android.synthetic.main.activity_stop_forecast.*

class StopForecastActivity : BaseActivity() {

    private val adapter = StopForecastAdapter()
    private lateinit var viewModel: StopForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRecycleView()
    }

    override fun getLayoutId() = R.layout.activity_stop_forecast

    private fun setupRecycleView() {

        forecast_recycle_view.adapter = adapter
        forecast_recycle_view.layoutManager = LinearLayoutManager(this)
    }

    private fun subscribeViewModel() {

        viewModel.stopLiveData.observe(this, Observer {
            showStopInfo(it)
        })

        viewModel.stopForecastsLiveData.observe(this, Observer {
            showForecasts(it)
        })
    }

    private fun showStopInfo(stop: Stop?) {

        stop_text.text = stop?.name
        message_text.text = stop?.message
    }

    private fun showForecasts(forecasts: List<StopForecast>?) {

        forecasts?.let {
            adapter.list = it
            adapter.notifyDataSetChanged()
        }
    }
}
