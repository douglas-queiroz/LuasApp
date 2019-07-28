package com.douglas.luasapp.module.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.douglas.luasapp.R
import javax.inject.Inject

abstract class BaseActivity<T: BaseViewModel>: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel by lazy { getViewMode() }

    private var loadingDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        inject()

        lifecycle.addObserver(viewModel)
        subscribeViewModel()
    }

    private fun getViewMode() : T {

        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(getViewModelClassType())
    }

    private fun subscribeViewModel() {

        viewModel.loadingStatus.observe(this, Observer { showLoading ->
            showLoading(showLoading)
        })

        viewModel.showErrorMessage.observe(this, Observer { msg ->
            showErrorMessage(msg)
        })
    }

    private fun showErrorMessage(msg: Int?) {

        val message = msg ?: return

        MaterialDialog.Builder(this)
            .title(R.string.error_default_title)
            .content(message)
            .positiveText(R.string.button_ok)
            .cancelable(false)
            .show()
    }

    private fun showLoading(showLoading: Boolean?) {

        if (showLoading != null && showLoading) {
            loadingDialog = MaterialDialog.Builder(this)
                .content(R.string.loading_text)
                .progress(true, 0)
                .cancelable(false)
                .show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun getViewModelClassType(): Class<T>

    abstract fun inject()
}