package hu.prooktatas.olimpics

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //displayTestData()
    }

    fun displayTestData() {
        // Add initial markers
        mMap.addMarker(MarkerOptions().position(LatLng(51.4984, -0.136083)).title("London"))
        mMap.addMarker(MarkerOptions().position(LatLng(48.854209, 2.349594)).title("Paris"))
        mMap.addMarker(MarkerOptions().position(LatLng(37.550952, 126.995717)).title("Seoul"))
        mMap.addMarker(MarkerOptions().position(LatLng(55.754712, 37.618264)).title("Moscow"))
        mMap.addMarker(
            MarkerOptions().position(LatLng(34.044429, -118.252161)).title("Los Angeles")
        )

    }

}