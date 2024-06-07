package com.jay.weatherforecastapp.di

import com.jay.weatherforecastapp.WeatherApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    // Inject into MyApp (Application class)
    fun inject(application: WeatherApplication)

    // Other inject methods for activities, fragments, etc.
    // e.g., fun inject(activity: MyActivity)
}