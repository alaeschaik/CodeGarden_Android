package at.ac.fhcampuswien.codegarden.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.endpoints.challenges.Challenge
import at.ac.fhcampuswien.codegarden.navigation.Screen
import at.ac.fhcampuswien.codegarden.viewModels.ChallengeViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@Composable
fun ChallengeScreen(sectionId: Int, navController: NavController) {
    val viewModel = viewModel<ChallengeViewModel>(
        factory = viewModelFactory {
            ChallengeViewModel(
                appModule.sectionService,
                appModule.challengeService,
                appModule.sharedPrefManager,
                sectionId
            )
        }
    )
    val challenges = viewModel.challenges.collectAsState().value

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Challenges",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
            })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(challenges) { challenge ->
                    ChallengeCard(
                        viewModel = viewModel,
                        challenge = challenge,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ChallengeCard(
    viewModel: ChallengeViewModel,
    challenge: Challenge,
    navController: NavController
) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column {
            HtmlContentViewer(htmlContent = challenge.content, modifier = Modifier.padding(8.dp), onClicked = {viewModel.getChallengeQuestions(challenge.id) {
                if (it.isNotEmpty())
                navController.navigate(Screen.QuestionScreen.route + "/${challenge.id}")
            }})
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HtmlContentViewer(htmlContent: String, modifier: Modifier = Modifier, onClicked: () -> Unit = {}) {
    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
                    setBackgroundColor(Color.Transparent.toArgb())
                }
            }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(onClick = onClicked)
        )
    }
}
