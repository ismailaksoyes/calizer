package com.avalon.calizer

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.avalon.calizer.utils.MySharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import java.util.logging.Handler

@HiltAndroidApp
class App : Application() {


    override fun onCreate() {
        super.onCreate()


    }

}