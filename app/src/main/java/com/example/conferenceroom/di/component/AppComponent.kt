package com.example.conferenceroom.di.component

import android.app.Application
import com.example.conferenceroom.ConferenceRoomApp
import com.example.conferenceroom.di.module.ActivityBuilderModule
import com.example.conferenceroom.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<ConferenceRoomApp> {
     @Component.Builder
     interface Builder {
         @BindsInstance
         fun application(application: Application): Builder

         @BindsInstance // you'll call this when setting up Dagger
         fun baseUrl(@Named("baseUrl") baseUrl: String): Builder

         fun build(): AppComponent

     }
}

