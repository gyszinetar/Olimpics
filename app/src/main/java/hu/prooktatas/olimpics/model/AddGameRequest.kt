package hu.prooktatas.olimpics.model

data class AddGameRequest (
    val country: String,
    val city: String,
    val year: Int,
    val latitude: Double,
    val longitude: Double
)