package com.inkubasi.hirehub

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.inkubasi.hirehub.coreapp.di.*
import com.inkubasi.hirehub.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    newRepositoryModule,
                    newUseCaseModule,
                    preferenceModule,
                    viewModelModule,
                    chatClientModule,
                )
            )
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}