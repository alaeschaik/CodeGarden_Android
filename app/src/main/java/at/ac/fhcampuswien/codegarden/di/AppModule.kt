package at.ac.fhcampuswien.codegarden.di

import android.content.Context
import androidx.compose.runtime.MutableIntState
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeApi
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeService
import at.ac.fhcampuswien.codegarden.endpoints.challenges.ChallengeServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.choices.ChoiceApi
import at.ac.fhcampuswien.codegarden.endpoints.choices.ChoiceService
import at.ac.fhcampuswien.codegarden.endpoints.choices.ChoiceServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentApi
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentService
import at.ac.fhcampuswien.codegarden.endpoints.comments.CommentServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleApi
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleService
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostApi
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostService
import at.ac.fhcampuswien.codegarden.endpoints.posts.PostServiceImpl
import at.ac.fhcampuswien.codegarden.endpoints.questions.QuestionApi
import at.ac.fhcampuswien.codegarden.endpoints.questions.QuestionService
import at.ac.fhcampuswien.codegarden.endpoints.questions.QuestionServiceImpl
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
    val gson: Gson
    val sharedPrefManager: SharedPrefManager
    val applicationContext: Context
    val userApi: UserApi
    val postApi: PostApi
    val commentApi: CommentApi
    val moduleApi: ModuleApi
    val sectionApi: SectionApi
    val challengeApi: ChallengeApi
    val questionApi: QuestionApi
    val choiceApi: ChoiceApi
    val userService: UserService
    val postService: PostService
    val commentService: CommentService
    val moduleService: ModuleService
    val sectionService: SectionService
    val challengeService: ChallengeService
    val questionService: QuestionService
    val choiceService: ChoiceService
}

class AppModuleImpl(private val appContext: Context) : AppModule {
    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sheikhs.at/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build()
            )
    }

    override val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(MutableIntState::class.java, MutableIntStateAdapter())
            .create()
    }

    override val sharedPrefManager: SharedPrefManager by lazy { SharedPrefManagerImpl(appContext) }
    override val applicationContext: Context get() = appContext

    private inline fun <reified T> createApi(): T = retrofitBuilder.build().create(T::class.java)

    override val userApi: UserApi get() = createApi()
    override val postApi: PostApi get() = createApi()
    override val commentApi: CommentApi get() = createApi()
    override val moduleApi: ModuleApi get() = createApi()
    override val sectionApi: SectionApi get() = createApi()
    override val challengeApi: ChallengeApi get() = createApi()
    override val questionApi: QuestionApi get() = createApi()
    override val choiceApi: ChoiceApi get() = createApi()

    override val userService: UserService get() = UserServiceImpl(appContext, userApi, sharedPrefManager)
    override val postService: PostService get() = PostServiceImpl(appContext, postApi, sharedPrefManager)
    override val commentService: CommentService get() = CommentServiceImpl(appContext, commentApi, sharedPrefManager)
    override val moduleService: ModuleService get() = ModuleServiceImpl(appContext, moduleApi, sharedPrefManager)
    override val sectionService: SectionService get() = SectionServiceImpl(appContext, sectionApi, sharedPrefManager)
    override val challengeService: ChallengeService get() = ChallengeServiceImpl(appContext, challengeApi, sharedPrefManager)
    override val questionService: QuestionService get() = QuestionServiceImpl(appContext, questionApi, sharedPrefManager)
    override val choiceService: ChoiceService get() = ChoiceServiceImpl(appContext, choiceApi, sharedPrefManager)
}