package hu.prooktatas.olimpics.model

import com.google.android.gms.maps.model.LatLng

data class MarkerInfo (
    val position: LatLng,
    val markerText: String
)