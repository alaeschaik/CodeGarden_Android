package at.ac.fhcampuswien.codegarden.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import at.ac.fhcampuswien.codegarden.CodeGardenApplication.Companion.appModule
import at.ac.fhcampuswien.codegarden.viewModels.ModuleViewModel
import at.ac.fhcampuswien.codegarden.viewModels.viewModelFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Scaffold
import at.ac.fhcampuswien.codegarden.widgets.SimpleTopAppBar
import androidx.compose.material3.IconButton
import at.ac.fhcampuswien.codegarden.navigation.Screen
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import at.ac.fhcampuswien.codegarden.widgets.SimpleBottomAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.codegarden.endpoints.modules.Module

@Composable
fun ModuleScreen(navController: NavController) {
    val viewModel = viewModel<ModuleViewModel>(
        factory = viewModelFactory {
            ModuleViewModel(
                appModule.moduleService,
                appModule.sharedPrefManager
            )
        }
    )
    val modules = viewModel.modules.collectAsState().value

    Scaffold (
        topBar = {
            SimpleTopAppBar(
                title = "Home",
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.ModuleScreen.route) }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.CreateModuleScreen.route) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Create")
                    }
                },
            )
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(modules) { module ->
                    ModuleCard(
                        viewModel = viewModel,
                        module = module,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ModuleCard(
    viewModel: ModuleViewModel,
    module: Module,
    navController: NavController
) {
    var isEditing by remember { mutableStateOf(false) }
    var editableText by remember { mutableStateOf(module.description) }

    Card(
        modifier = Modifier
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(8.dp)
        //onClick = { navController.navigate(Screen.ModuleDetailScreen.route + "/${module.id}") }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        isEditing = !isEditing
                        if (!isEditing) {
                            module.description = editableText
                            viewModel.updateModule(module)
                        }
                    },
                ) {
                    Text(if (isEditing) "Save" else "Edit")
                }
//                IconButton(onClick = { viewModel.deleteModule(module.id) }) {
//                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
//                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (isEditing) {
                TextField(
                    value = editableText,
                    onValueChange = { editableText = it },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = editableText)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "0/${module.totalXpPoints}XP",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
