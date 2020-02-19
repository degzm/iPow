package dev.deg.ipow

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : FragmentActivity(), OnMapReadyCallback {
    val context: Context = this
    var location: Location? = null
    var latitud = 0.0
    var longitud = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        val fineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }
    }

    fun mostrarDialogo(marker: Marker) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.customdialog)
        //Textview 1
        val tv1 = dialog.findViewById<TextView>(R.id.tv1)
        val nombre = marker.title
        tv1.text = nombre
        //Text view 2
        val tv2 = dialog.findViewById<TextView>(R.id.tv2)
        val snippet = marker.snippet
        tv2.text = snippet
        dialog.show()
    }

    override fun onMapReady(mapa: GoogleMap) {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                latitud = location.latitude
                longitud = location.longitude
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        val fineLocation = ActivityCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        val ubi = LatLng(latitud, longitud)
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi, 18f))
        mapa.uiSettings.isZoomControlsEnabled = true
        mapa.uiSettings.isMapToolbarEnabled = true
        mapa.addMarker(MarkerOptions().position(LatLng(25.4439803, -100.8597785))
                .title("CU Entrada").snippet("Solar"))
        mapa.setOnMarkerClickListener { marker ->
            mostrarDialogo(marker)
            true
        }
    }
} //Fin mapsActivity
