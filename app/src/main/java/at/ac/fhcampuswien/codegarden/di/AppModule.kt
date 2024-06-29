package at.ac.fhcampuswien.codegarden.di

import android.content.Context
import androidx.compose.runtime.MutableIntState
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentApi
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentService
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostApi
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostService
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.users.UserApi
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import at.ac.fhcampuswien.codegarden.endpoints.users.UserServiceImpl
import at.ac.fhcampuswien.codegarden.utils.MutableIntStateAdapter
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManagerImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface AppModule {
    val gsonWithMutableIntStateAdapter: Gson
    val userApi: UserApi
    val postApi: PostApi
    val commentApi: CommentApi
    val userService: UserService
    val postService: PostService
    val commentService: CommentService
    val sharedPrefManager: SharedPrefManager
    val applicationContext: Context
}

class AppModuleImpl(private val appContext: Context) : AppModule {
    override val gsonWithMutableIntStateAdapter: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(MutableIntState::class.java, MutableIntStateAdapter())
            .create()
    }


    override val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sheikhs.at/api/")
            .addConverterFactory(GsonConverterFactory.create(gsonWithMutableIntStateAdapter))
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
            .create(UserApi::class.java)
    }


    override val postApi: PostApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sheikhs.at/api/")
            .addConverterFactory(GsonConverterFactory.create(gsonWithMutableIntStateAdapter))
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
            .create(PostApi::class.java)
    }
    override val commentApi: CommentApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sheikhs.at/api/")
            .addConverterFactory(GsonConverterFactory.create(gsonWithMutableIntStateAdapter))
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
            .create(CommentApi::class.java)
    }

    override val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManagerImpl(appContext)
    }
    override val applicationContext: Context by lazy {
        appContext
    }

    override val userService: UserService by lazy {
        UserServiceImpl(appContext, userApi, sharedPrefManager)
    }

    override val postService: PostService by lazy {
        PostServiceImpl(appContext, postApi, sharedPrefManager)
    }
    override val commentService: CommentService by lazy {
        CommentServiceImpl(appContext, commentApi, sharedPrefManager)
    }
}