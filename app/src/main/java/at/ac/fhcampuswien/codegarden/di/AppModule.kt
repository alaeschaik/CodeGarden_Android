package at.ac.fhcampuswien.codegarden.di

import android.content.Context
import at.ac.fhcampuswien.codegarden.users.UserApi
import at.ac.fhcampuswien.codegarden.users.UserService
import at.ac.fhcampuswien.codegarden.users.UserServiceImpl
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManagerImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

interface AppModule {
    val userApi: UserApi
    val userService: UserService
    val sharedPrefManager: SharedPrefManager
}

class AppModuleImpl(private val appContext: Context) : AppModule {
    override val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sheikhs.at/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(
                        20,
                        TimeUnit.SECONDS
                    ) // Increase the connection timeout to 20 seconds
                    .readTimeout(20, TimeUnit.SECONDS) // Increase the read timeout to 20 seconds
                    .build()
            )
            .build()
            .create()
    }

    override val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManagerImpl(appContext)
    }

    override val userService: UserService by lazy {
        UserServiceImpl(appContext, userApi, sharedPrefManager)
    }


}