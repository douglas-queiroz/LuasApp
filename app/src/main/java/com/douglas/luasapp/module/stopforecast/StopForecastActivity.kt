package com.douglas.luasapp.module.stopforecast

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.douglas.luasapp.LuasApp
import com.douglas.luasapp.R
import com.douglas.luasapp.domain.model.StopInfo
import com.douglas.luasapp.domain.model.StopForecast
import com.douglas.luasapp.module.base.BaseActivity
import kotlinx.android.synthetic.main.activity_stop_forecast.*

class StopForecastActivity : BaseActivity<StopForecastViewModel>() {

    private val adapter = StopForecastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRecycleView()
        setupRefreshButton()
        subscribeViewModel()
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadStopForecasts()
    }

    override fun getLayoutId() = R.layout.activity_stop_forecast

    override fun getViewModelClassType(): Class<StopForecastViewModel> = StopForecastViewModel::class.java

    override fun inject() {
        LuasApp.component.inject(this)
    }

    private fun setupRecycleView() {

        forecast_recycle_view.adapter = adapter
        forecast_recycle_view.layoutManager = LinearLayoutManager(this)
    }

    private fun setupRefreshButton() {

        refresh_button.setOnClickListener(onRefreshButtonClick)
    }

    private val onRefreshButtonClick = View.OnClickListener {

        viewModel.loadStopForecasts()
    }

    private fun subscribeViewModel() {

        viewModel.stopLiveData.observe(this, Observer {
            showStopInfo(it)
        })

        viewModel.stopForecastsLiveData.observe(this, Observer {
            showForecasts(it)
        })
    }

    private fun showStopInfo(stopInfo: StopInfo?) {

        stop_text.text = stopInfo?.name
        message_text.text = stopInfo?.message
    }

    private fun showForecasts(forecasts: List<StopForecast>?) {

        forecasts?.let {
            adapter.list = it
            adapter.notifyDataSetChanged()
        }
    }
}
