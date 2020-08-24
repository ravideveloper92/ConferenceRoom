package com.example.conferenceroom.di.module

import com.example.conferenceroom.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
