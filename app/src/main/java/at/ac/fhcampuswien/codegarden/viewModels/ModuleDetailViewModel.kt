package at.ac.fhcampuswien.codegarden.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.codegarden.endpoints.modules.ModuleService
import at.ac.fhcampuswien.codegarden.endpoints.modules.Section
import at.ac.fhcampuswien.codegarden.endpoints.sections.SectionService
import at.ac.fhcampuswien.codegarden.utils.SharedPrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ModuleDetailViewModel(
    private val moduleService: ModuleService,
    private val sectionService: SectionService,
    private val sharedPrefManager: SharedPrefManager,
    moduleId: Int
) : ViewModel() {

    private val _sections = MutableStateFlow<List<Section>>(emptyList())
    val sections = _sections.asStateFlow()

    init {
        getModuleSections(moduleId) { sections ->
            _sections.value = sections
        }
    }

    private fun getModuleSections(id: Int, onSectionsFetched: (sections: List<Section>) -> Unit) {
        viewModelScope.launch {
            moduleService.getModuleSections(id).collect { sections ->
                onSectionsFetched(sections)
            }
        }
    }
}
