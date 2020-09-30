package hu.prooktatas.olimpics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import hu.prooktatas.olimpics.model.AddGameRequest
import hu.prooktatas.olimpics.model.AddGameResult
import hu.prooktatas.olimpics.persistence.entity.Country
import hu.prooktatas.olimpics.persistence.repository.OlimpicGamesRepository

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var googleMap: GoogleMap

    private val liveMarkers = mutableListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setOnMapClickListener(this)
        googleMap.uiSettings.isZoomControlsEnabled=true

        buildAllMarkers()
        zoomToSelected()
        //displayTestData()
    }

    private fun zoomToSelected() {
        intent.extras?.get("gps")?.let {
            if (it is LatLng) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 6F))
            }
        }
     }


    fun displayTestData() {
        // Add initial markers
        googleMap.addMarker(MarkerOptions().position(LatLng(51.4984, -0.136083)).title("London"))
        googleMap.addMarker(MarkerOptions().position(LatLng(48.854209, 2.349594)).title("Paris"))
        googleMap.addMarker(MarkerOptions().position(LatLng(37.550952, 126.995717)).title("Seoul"))
        googleMap.addMarker(MarkerOptions().position(LatLng(55.754712, 37.618264)).title("Moscow"))
        googleMap.addMarker(
            MarkerOptions().position(LatLng(34.044429, -118.252161)).title("Los Angeles")
        )

    }

    override fun onMapClick(position: LatLng?) {
        Log.d(TAG, "mapclicked!!")


        checkCity(position!!)

    }

    private fun checkCity(pos:LatLng){
        Thread{
            var repo= OlimpicGamesRepository(this)
            var cc=repo.cityCloseToLocation(pos)
            var city:String?=null
            var country:String?=null
            if(cc!=null){
            city=cc.first
            country=cc.second}
         runOnUiThread{
             displayDialog(pos,city,country)
         }
        }.start()

    }

    private fun buildAllMarkers() {
        val bgThread = Thread {
            val repo = OlimpicGamesRepository(this)
            val allMarkers = repo.buildMarkerInfo()
            runOnUiThread {
                googleMap.clear()

                allMarkers.forEach {
                    val aMarker = googleMap.addMarker(it.mo)
                    aMarker.tag = it.markerTag
                    liveMarkers.add(aMarker)


                }
            }
        }

        bgThread.start()
    }


    private fun displayDialog(pos: LatLng,city:String?,country: String?) {
        val dialog = AlertDialog.Builder(this).create()
        dialog.setTitle("Add New Game")

        val rootView = LayoutInflater.from(this).inflate(R.layout.add_game_request_layout, null)

        val tvLatitude = rootView.findViewById<TextView>(R.id.tvLatitude)
        val tvLongitude = rootView.findViewById<TextView>(R.id.tvLongitude)
        val etCity = rootView.findViewById<EditText>(R.id.etCity)
        val etCountry = rootView.findViewById<EditText>(R.id.etCountry)
        val etYear = rootView.findViewById<EditText>(R.id.etYear)
        val btnOk = rootView.findViewById<Button>(R.id.btnOk)

        if(city!=null){
            etCity.setText(city)
            etCountry.setText(country)
            etCity.isFocusable=false
            etCountry.isFocusable=false
        }

        tvLatitude.text = pos.latitude.toString()
        tvLongitude.text = pos.longitude.toString()

        btnOk.setOnClickListener {
            val dataCity = etCity.text.toString()
            val dataCountry= etCountry.text.toString()
            val dataYear= etYear.text.toString()
            val dataLatitude= tvLatitude.text.toString()
            val dataLongitude= tvLongitude.text.toString()
            if(dataCity.length>2 && dataCountry.length>2 && dataYear.toInt()>1800){

                Log.d(TAG,dataCountry+" "+dataCity+" "+dataYear)
                persistNewGame(AddGameRequest(dataCountry,dataCity,dataYear.toInt(),dataLatitude.toDouble(),dataLongitude.toDouble()), dialog)
//                dialog.dismiss()
            }
        }

        dialog.setView(rootView)
        dialog.show()
    }

    private fun persistNewGame(request: AddGameRequest, d: AlertDialog) {

        val bgThread = Thread {
            val repo = OlimpicGamesRepository(this)
            val addGameResponse = repo.processGameRequest(request)

            runOnUiThread {
                d.dismiss()

                when(addGameResponse.result) {
                    AddGameResult.ERROR_INVALID_YEAR -> {
                        // do nothing, year input was invalid
                        Log.d(TAG, "létező évszám!!!")
                    }

                    AddGameResult.SUCCESS_NEW_CITY -> {
                        Log.d(TAG, "évszám jó, új város")
                        val newMarker = googleMap.addMarker(addGameResponse.info!!.mo)
                        newMarker.tag = addGameResponse.info!!.markerTag
                        liveMarkers.add(newMarker)
                    }

                    AddGameResult.SUCCESS_EXISTING_CITY -> {
                        Log.d(TAG, "évszám jó, létező város")
                        liveMarkers.filter {
                            it.tag == addGameResponse.info!!.markerTag
                        }.first().also {
                            it.title = addGameResponse.info!!.markerText
                        }
                    }
                }
            }
        }

        bgThread.start()

    }


//    private fun sendGameRequest(request: AddGameRequest){
//        Thread{
//            val repo = OlimpicGamesRepository(this)
//            val resposne = repo.processGameRequest(request)
//            runOnUiThread{
//
//            }
//        }.start()
//
//    }


}
