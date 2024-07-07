package at.ac.fhcampuswien.codegarden.di

import android.content.Context
import androidx.compose.runtime.MutableIntState
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeApi
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeService
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentApi
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentService
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.discussions.DiscussionApi
import at.ac.fhcampuswien.codegarden.endpoints.discussions.DiscussionService
import at.ac.fhcampuswien.codegarden.endpoints.discussions.DiscussionServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleApi
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleService
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostApi
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostService
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.sections.SectionApi
import at.ac.fhcampuswien.codegarden.endpoints.sections.SectionService
import at.ac.fhcampuswien.codegarden.endpoints.sections.SectionServiceImpl
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
    val applicationContext: Context
    val sharedPrefManager: SharedPrefManager

    val userApi: UserApi
    val postApi: PostApi
    val discussionApi: DiscussionApi
    val commentApi: CommentApi
    val moduleApi: ModuleApi
    val sectionApi: SectionApi
    val challengeApi: ChallengeApi

    val userService: UserService
    val postService: PostService
    val commentService: CommentService
    val discussionService: DiscussionService
    val moduleService: ModuleService
    val sectionService: SectionService
    val challengeService: ChallengeService
}

class AppModuleImpl(private val appContext: Context) : AppModule {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sheikhs.at/api/")
            .addConverterFactory(GsonConverterFactory.create(gsonWithMutableIntStateAdapter))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build()
            )
            .build()
    }

    override val gsonWithMutableIntStateAdapter: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(MutableIntState::class.java, MutableIntStateAdapter())
            .create()
    }

    override val applicationContext: Context get() = appContext
    override val sharedPrefManager: SharedPrefManager by lazy { SharedPrefManagerImpl(appContext) }

    override val userApi: UserApi get() = retrofit.create(UserApi::class.java)
    override val postApi: PostApi get() = retrofit.create(PostApi::class.java)
    override val discussionApi: DiscussionApi get() = retrofit.create(DiscussionApi::class.java)
    override val commentApi: CommentApi get() = retrofit.create(CommentApi::class.java)
    override val moduleApi: ModuleApi get() = retrofit.create(ModuleApi::class.java)
    override val sectionApi: SectionApi get() = retrofit.create(SectionApi::class.java)
    override val challengeApi: ChallengeApi = retrofit.create(ChallengeApi::class.java)

    override val userService: UserService by lazy { UserServiceImpl(appContext, userApi, sharedPrefManager) }
    override val postService: PostService by lazy { PostServiceImpl(appContext, postApi, sharedPrefManager) }
    override val commentService: CommentService by lazy { CommentServiceImpl(appContext, commentApi, sharedPrefManager) }
    override val discussionService: DiscussionService by lazy { DiscussionServiceImpl(appContext, discussionApi, sharedPrefManager) }
    override val moduleService: ModuleService by lazy { ModuleServiceImpl(appContext, moduleApi, sharedPrefManager) }
    override val sectionService: SectionService by lazy { SectionServiceImpl(appContext, sectionApi, sharedPrefManager) }
    override val challengeService: ChallengeService by lazy { ChallengeServiceImpl(appContext, challengeApi, sharedPrefManager) }
}