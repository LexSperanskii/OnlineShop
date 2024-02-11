package com.example.onlineshop

import android.app.Application
import com.example.onlineshop.di.AppContainer
import com.example.onlineshop.di.AppDataContainer

class OnlineShopApplication : Application(){
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}