package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.viewModels.ModuleViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar

@Composable
fun CreateModuleScreen(navController: NavHostController) {
    val viewModel = viewModel<ModuleViewModel>(
        factory = viewModelFactory {
            ModuleViewModel(
                appModule.userService,
                appModule.moduleService
            )
        }
    )
    var title by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var introduction by remember { mutableStateOf(TextFieldValue()) }
    var content by remember { mutableStateOf(TextFieldValue()) }
    var totalXpPoints by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Create new Module",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Cancel"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = introduction,
                onValueChange = { introduction = it },
                label = { Text("Introduction") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = totalXpPoints,
                onValueChange = { totalXpPoints = it },
                label = { Text("Total XP Points") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.createModule(
                    title.text,
                    description.text,
                    introduction.text,
                    content.text,
                    totalXpPoints.text.toInt()
                ) {
                    navController.popBackStack()
                }
            }) {
                Text("Create")
            }
        }
    }
}
