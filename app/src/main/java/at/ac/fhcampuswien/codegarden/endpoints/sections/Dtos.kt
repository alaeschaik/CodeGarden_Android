package at.ac.fhcampuswien.codegarden.endpoints.sections

data class CreateSectionRequest(
    val moduleId: Int,
    val title: String,
    val content: List<String>,
    val xpPoints: Int
)

data class CreateSectionResponse(
    val id: Int,
    val moduleId: Int,
    val title: String,
    val xpPoints: Int
) {
    fun toSection(): Section {
        return Section(
            this.id,
            this.moduleId,
            this.title,
            this.xpPoints
        )
    }
}

data class UpdateSectionRequest(
    val title: String,
    val moduleId: Int,
    val xpPoints: Int
)

data class Section(
    val id: Int,
    val moduleId: Int,
    val title: String,
    val xpPoints: Int
)
