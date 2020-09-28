package hu.prooktatas.olimpics.model

class AddGameResponse(
    val result:AddGameResult,
    val info:MarkerInfo?
)



enum class AddGameResult {
    ERROR_INVALID_YEAR, SUCCESS_EXISTING_CITY, SUCCESS_NEW_CITY
}