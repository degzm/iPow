package dev.deg.ipow;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    final Context context = this;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    public void mostrarDialogo(Marker marker){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog);
        //Textview 1
        TextView tv1 = dialog.findViewById(R.id.tv1);
        String nombre = marker.getTitle();
        tv1.setText(nombre);
        //Text view 2
        TextView tv2 = dialog.findViewById(R.id.tv2);
        String snippet = marker.getSnippet();
        tv2.setText(snippet);
        dialog.show();
    }

    @Override
    public void onMapReady(GoogleMap mapa) {
        double latitud = 0;
        double longitud = 0;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,}, 1000);
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            mapa.setMyLocationEnabled(true);
        }
        else{
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria, false)));
                latitud = location.getLatitude();
                longitud = location.getLongitude();
                mapa.setMyLocationEnabled(true);
            }
        }

        LatLng ubi = new LatLng(latitud,longitud);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi,18));

        mapa.getUiSettings().setZoomControlsEnabled( true );
        mapa.getUiSettings().setMapToolbarEnabled(true);

        mapa.addMarker(new MarkerOptions().position(new LatLng(25.4439803, -100.8597785 ))
                .title("CU Entrada").snippet("Solar"));

        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                mostrarDialogo(marker);
                return true;
            }
        });
    }
}//Fin mapsActivity
