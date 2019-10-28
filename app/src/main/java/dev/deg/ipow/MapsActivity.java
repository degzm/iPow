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
    double latitud;
    double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }else{
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(Objects.requireNonNull(
                           locationManager.getBestProvider(criteria, false)));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap mapa) {
        latitud = location.getLatitude();
        longitud = location.getLongitude();
        LatLng ubi = new LatLng(latitud,longitud);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi,7));

        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled( true );

        mapa.addMarker(new MarkerOptions().position(new LatLng(25.4439803, -100.8597785 ))
                .title("CU Entrada").snippet("Solar"));

        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.customdialog);
                //Text view del titulo, creacion y asignacion
                TextView tv1 = dialog.findViewById(R.id.tv1);
                String nombre = marker.getTitle();
                tv1.setText(nombre);
                //Text view del snippet (detalles), asignacion y detalles
                TextView tv2 = dialog.findViewById(R.id.tv2);
                String snippet = marker.getSnippet();
                tv2.setText(snippet);
                //Muestra el dialogo personalizado
                dialog.show();
                return true;
            }
        });
    }
}
