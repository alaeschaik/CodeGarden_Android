package at.ac.fhcampuswien.codegarden

import android.app.Application
import at.ac.fhcampuswien.codegarden.di.AppModule
import at.ac.fhcampuswien.codegarden.di.AppModuleImpl

class CodeGardenApplication : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}