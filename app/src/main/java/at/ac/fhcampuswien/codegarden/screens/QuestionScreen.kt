package at.ac.fhcampuswien.codegarden.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.endpoints.questions.Question
import at.ac.fhcampuswien.codegarden.viewModels.QuestionViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar

@Composable
fun QuestionScreen(challengeId: Int, navController: NavController) {
    val viewModel = viewModel<QuestionViewModel>(
        factory = viewModelFactory {
            QuestionViewModel(
                appModule.challengeService,
                appModule.sharedPrefManager,
                challengeId
            )
        }
    )
    val questions = viewModel.questions.collectAsState().value

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Questions",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
            })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(questions) { question ->
                    QuestionCard(
                        viewModel = viewModel,
                        question = question,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionCard(
    viewModel: QuestionViewModel,
    question: Question,
    navController: NavController
) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            HtmlContentViewer(htmlContent = question.content, modifier = Modifier.padding(8.dp))
        }
    }
}
