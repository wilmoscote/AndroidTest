package com.timetonic.androidtest.data.di

import android.content.Context
import com.timetonic.androidtest.data.api.ApiService
import com.timetonic.androidtest.data.api.RetrofitInstance
import com.timetonic.androidtest.data.repository.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = RetrofitInstance.api

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}