package com.example.conferenceroom.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.conferenceroom.di.annotation.ViewModelKey
import com.example.conferenceroom.ui.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@SuppressWarnings("WeakerAccess")
@Module
abstract class ViewModelModule {
/*
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel
*/

    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory?): ViewModelProvider.Factory?
}