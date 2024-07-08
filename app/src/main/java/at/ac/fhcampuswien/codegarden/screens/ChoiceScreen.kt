package at.ac.fhcampuswien.codegarden.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.endpoints.choices.AnswerChoiceRequest
import at.ac.fhcampuswien.codegarden.endpoints.choices.Choice
import at.ac.fhcampuswien.codegarden.viewModels.ChoiceViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar

@Composable
fun ChoiceScreen(questionId: Int, navController: NavController) {
    val viewModel = viewModel<ChoiceViewModel>(
        factory = viewModelFactory {
            ChoiceViewModel(
                appModule.userService,
                appModule.choiceService,
                appModule.questionService,
                questionId
            )
        }
    )
    val choices = viewModel.choices.collectAsState().value

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Choices",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(choices) { choice ->
                    ChoiceCard(
                        viewModel = viewModel,
                        choice = choice,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ChoiceCard(
    viewModel: ChoiceViewModel,
    choice: Choice,
    navController: NavController
) {
    var xpPoints by remember { mutableFloatStateOf(0f) }
    viewModel.getQuestion(choice.questionId) { question ->
        xpPoints = question.xpPoints
    }

    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = choice.content,
                style = LocalTextStyle.current.copy(fontSize = 18.sp)
            )
            Button(
                onClick = {
                    viewModel.answerChoice(
                        choice.id,
                        AnswerChoiceRequest(choice.content)
                    ) { success ->
                        if (success) {
                            viewModel.updateUserXpPoints(xpPoints, onUpdated = {})
                            navController.popBackStack()
                        } else {
                            Toast.makeText(navController.context, "Wrong answer", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            ) {
                Text(text = "Answer")
            }
        }
    }
}
