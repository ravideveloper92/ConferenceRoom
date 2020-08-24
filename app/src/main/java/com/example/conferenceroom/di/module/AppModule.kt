package com.example.conferenceroom.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.conferenceroom.data.source.local.AppDatabase
import com.example.conferenceroom.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application) : Context
    {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase
    {
        return Room.databaseBuilder(context,AppDatabase::class.java, Constants.DB_NAME)
            .allowMainThreadQueries()
            .build()

    }

}