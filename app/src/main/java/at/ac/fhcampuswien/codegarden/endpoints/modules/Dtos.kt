package at.ac.fhcampuswien.codegarden.endpoints.modules

data class CreateModuleRequest(
    val title: String,
    val description: String,
    val introduction: String,
    val content: String,
    val totalXpPoints: Int
)

data class CreateModuleResponse(
    val id: Int,
    val title: String,
    val description: String,
    val introduction: String,
    val totalXpPoints: Int,
    val content: String
) {
    fun toModule(): Module {
        return Module(
            this.id,
            this.title,
            this.description,
            this.introduction,
            this.totalXpPoints,
            this.content
        )
    }
}

data class UpdateModuleRequest(
    val title: String,
    val description: String,
    val introduction: String,
    val content: String,
    val totalXpPoints: Int
)

data class Module(
    val id: Int,
    val title: String,
    val description: String,
    val introduction: String,
    val totalXpPoints: Int,
    val content: String
)

data class Section(
    val id: Int,
    val moduleId: Int,
    val title: String,
    val xpPoints: Int
)