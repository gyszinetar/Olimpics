package hu.prooktatas.olimpics.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

data class MarkerInfo (
    val position: LatLng,
    val markerText: String
) {

    val mo = MarkerOptions().also {
        it.position(position)
        it.title(markerText)
    }

    val markerTag: String
        get() {
            return markerText.split(":").first()
        }

}