
package android.turisapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitud = 0.0;
    double longitude = 0.0;
    double latitudDestino;
    double longitudDestino;
    List<String> latitudes = new ArrayList<>();
    List<String> longitudes = new ArrayList<>();
    List<String> nombreDestinos = new ArrayList<>();
    String categoria = "sitios";
    Location location;
    ArrayList<LatLng> markerPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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

   /* public void showDirections(double lat, double lng, double lat1, double lng1) {

        final Intent intent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr=" + lat + "," + lng + "&daddr=" + lat1 + "," +
                lng1));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GoogleMap mMap2 = mMap;
        miUbicacion();

        String query = "SELECT * FROM "+categoria;
        Conexion conexion = new Conexion(this);
        SQLiteDatabase db = conexion.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            latitudDestino = Double.parseDouble(getIntent().getExtras().getString("latitud"));
            longitudDestino = Double.parseDouble(getIntent().getExtras().getString("longitud"));
            String nombreDestino = getIntent().getExtras().getString("nombre");
            destino(latitudDestino,longitudDestino,nombreDestino);


            //MARCAR RUTA





        } else {

            try {
                while (cursor.moveToNext()){
                    latitudes.add(cursor.getString(5));
                    longitudes.add(cursor.getString(6));
                    nombreDestinos.add(cursor.getString(1));
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i<latitudes.size();i++){
                String latitudRecibe = latitudes.get(i);
                String longitudRecibe = longitudes.get(i);
                latitudDestino = Double.parseDouble(latitudRecibe);
                longitudDestino = Double.parseDouble(longitudRecibe);
                String nombreDestino = nombreDestinos.get(i);
                destino(latitudDestino,longitudDestino,nombreDestino);
            }
        }

        /*Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(4.545695136892776,-75.67256734597161);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel=15;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoomLevel));*/


    }

    private void ruta() {


    }

    private void destino(double latitudDestino, double longitudDestino, String nombreDestino) {
        //Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitudDestino,longitudDestino);
        mMap.addMarker(new MarkerOptions().position(sydney).title(nombreDestino));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel=7;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoomLevel));
    }

    private void destinoRuta(double latitudDestino, double longitudDestino, String nombreDestino) {
        try {

            LatLng destino = new LatLng(latitudDestino,longitudDestino);
            LatLng origen = new LatLng(latitud,longitude);


            MarkerOptions marcadorDestino= new MarkerOptions();
            MarkerOptions marcadorOrigen= new MarkerOptions();
            marcadorDestino.position(destino);
            marcadorOrigen.position(origen);
            marcadorDestino.title("Este es tu destino");
            mMap.addMarker(marcadorDestino);


            String url = obtenerDireccionesURL(marcadorOrigen.getPosition(), destino);
         //   DownloadTask downloadTask = new DownloadTask(this);
         //   downloadTask.execute(url);

            Toast.makeText(this,"entró", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


       /* Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(latitud, longitude), new LatLng(latitudDestino, longitudDestino))
                .width(7)
                .color(Color.RED));*/
    }

    public void agreagarMArcador(double latitud, double longitude) {
        LatLng coordenadas = new LatLng(latitud, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        mMap.addMarker(new MarkerOptions().position(coordenadas).title("estoy aquí")
                /*.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))*/);
        mMap.animateCamera(cameraUpdate);

    }

    private void actualizarUbicacipon(Location location) {
        if (location != null) {
            latitud = location.getLatitude();
            longitude = location.getLongitude();
            agreagarMArcador(latitud, longitude);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacipon(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        actualizarUbicacipon(location);
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,15000,0,locationListener);
    }


    // MARCAR RUTA

    private String obtenerDireccionesURL(LatLng origin,LatLng dest){

        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        String sensor = "sensor=false";

        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }





}
