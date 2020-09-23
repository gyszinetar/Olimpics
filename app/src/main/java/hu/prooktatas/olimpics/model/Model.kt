package hu.prooktatas.olimpics.model
import com.google.android.gms.maps.model.LatLng

data class GameInfo(val country:String, val city:String, val year:Int)

data class MarkerInfo (
    val position: LatLng,
    val markerText: String
)