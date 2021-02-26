package krasnikov.project.pmgithubclient.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import krasnikov.project.pmgithubclient.app.data.pref.SharedPref

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPref {
        return SharedPref(context)
    }
}