package hu.prooktatas.olimpics.model

class AddGameResponse(
    result:AddGameResult,
    info:MarkerInfo?
)



enum class AddGameResult {
    ERROR_INVALID_YEAR, SUCCESS_EXISTING_CITY, SUCCESS_NEW_CITY
}