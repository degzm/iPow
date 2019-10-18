package dev.deg.ipow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*
     * Metodo para manipular el mapa cuando este listo.
     * Aqui se agregan los marcadores y se comprueban los
     * permisos para usar estos mismos.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Comprueba que se tengan los permisos de ubicacion, de lo contrario los pide
        //Prende la ubicacion en tiempo real
        googleMap.setMyLocationEnabled(true);
        //Activa el boton del zoom del mapa
        googleMap.getUiSettings().setZoomControlsEnabled( true );
        //Marcadores
        googleMap.addMarker(new MarkerOptions().position(new LatLng(25.4439803, -100.8597785 ))
                .title("CU Entrada").snippet("Solar"));

        //Aqui se crea el dialogo al dar click en el marcador
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.customdialog);
                //Text view del titulo, creacion y asignacion
                TextView tv1 = (TextView) dialog.findViewById(R.id.tv1);
                String nombre = marker.getTitle();
                tv1.setText(nombre);
                //Text view del snippet (detalles), asignacion y detalles
                TextView tv2 = (TextView) dialog.findViewById(R.id.tv2);
                String snippet = marker.getSnippet();
                tv2.setText(snippet);
                //Muestra el dialogo personalizado
                dialog.show();
                return true;
            }
        });
    }
}
