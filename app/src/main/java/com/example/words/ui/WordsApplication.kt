package com.example.words.ui

import android.app.Application
import com.example.words.di.homeModule
import com.example.words.di.managementModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WordsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startDependencyInjection()
    }


    private fun startDependencyInjection() {
        startKoin {
            androidContext(this@WordsApplication)
            modules(
                listOf(
                    homeModule,
                    managementModule
                )
            )
        }
    }


    companion object {
        val FILE = "file.ser"
        var fileText = ""
    }
}