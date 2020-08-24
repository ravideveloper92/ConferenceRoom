package com.example.conferenceroom.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivityt<M : ViewModel, B : ViewDataBinding?> :
    DaggerAppCompatActivity() {
    val TAG1:String= BaseActivityt::class.java.simpleName
    lateinit var activityviewModel : ViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutResId)
        activityviewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModel)!!
        onCreate(savedInstanceState, activityviewModel as M, binding as B)
    }

    protected abstract val viewModel: Class<M>
    protected abstract fun onCreate(instance: Bundle?, viewModel: M, binding: B)

    @get:LayoutRes
    protected abstract val layoutResId: Int
}
