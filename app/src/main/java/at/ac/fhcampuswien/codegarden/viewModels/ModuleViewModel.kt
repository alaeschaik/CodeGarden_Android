package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.modules.CreateModuleRequest
import at.ac.fhcampuswien.codegarden.endpoints.modules.Module
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleService
import at.ac.fhcampuswien.codegarden.endpoints.modules.Section
import at.ac.fhcampuswien.codegarden.endpoints.modules.UpdateModuleRequest
import at.ac.fhcampuswien.codegarden.endpoints.users.User
import at.ac.fhcampuswien.codegarden.endpoints.users.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ModuleViewModel(
    private val userService: UserService,
    private val moduleService: ModuleService
) : ViewModel() {

    private val _modules = MutableStateFlow<List<Module>>(emptyList())
    val modules = _modules.asStateFlow()

    private val _sections = MutableStateFlow<List<Section>>(emptyList())
    val sections = _sections.asStateFlow()

    init {
        getAllModules()
    }

    fun createModule(
        title: String,
        description: String,
        introduction: String,
        content: String,
        totalXpPoints: Int,
        onModuleSuccess: (module: Module) -> Unit
    ) {
        viewModelScope.launch {
            val requestBody = CreateModuleRequest(title, description, introduction, content, totalXpPoints)
            moduleService.createModule(requestBody).collect { response ->
                if (response != null) {
                    onModuleSuccess(response.toModule())
                }
            }
        }
    }

    private fun getAllModules() {
        viewModelScope.launch {
            moduleService.getAllModules().collect { modules ->
                modules.let {
                    _modules.value = modules
                }
            }
        }
    }

    fun getModule(id: Int, onModuleFetched: (module: Module) -> Unit) {
        viewModelScope.launch {
            moduleService.getModule(id).collect { module ->
                onModuleFetched(module)
            }
        }
    }

    fun updateModule(module: Module) {
        viewModelScope.launch {
            moduleService.updateModule(
                module.id,
                UpdateModuleRequest(
                    module.title,
                    module.description,
                    module.introduction,
                    module.content,
                    module.totalXpPoints
                )
            ).collect { success ->
                if (success) {
                    _modules.value = _modules.value.map { if (it.id == module.id) module else it }.toList()
                }
            }
        }
    }

    fun deleteModule(id: Int, onDeleteSuccess: () -> Unit) {
        viewModelScope.launch {
            moduleService.deleteModule(id).collect { success ->
                if (success) {
                    onDeleteSuccess()
                }
            }
        }
    }

    fun getModuleSections(id: Int, onSectionsFetched: (sections: List<Section>) -> Unit) {
        viewModelScope.launch {
            moduleService.getModuleSections(id).collect { sections ->
                onSectionsFetched(sections)
            }
        }
    }

    fun userDetails(onDetailsFetched: (User) -> Unit) {
        viewModelScope.launch {
            userService.getUserProfile()?.let {
                onDetailsFetched(it)
            }
        }
    }
}
