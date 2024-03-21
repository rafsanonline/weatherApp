package com.mdrafsanbiswas.weatherapp.di
import com.mdrafsanbiswas.weatherapp.repo.weather.IWeatherRepository
import com.mdrafsanbiswas.weatherapp.repo.weather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideWeatherRepo(repository: WeatherRepository): IWeatherRepository

}