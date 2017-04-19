package ville.fi.hikemate.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Utils.Debug;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    private Context host = this;
    private FragmentActivity thisActivity = this;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location location;
    private Hike hike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        hike = new Hike("MyHike");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) host.getSystemService(Context.LOCATION_SERVICE);
        initLocationListener();
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(host,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Debug.print(host, "Asking permissions", "Requesting permission", "No permissions, dialog", 1);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

            /*
            Debug.print(host, "Asking permissions", "Requesting permission", "We have a permission", 1);

            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Debug.print(host, "Asking permissions", "Requesting permission", "Location Manager Requesting", 1);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
            */
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Debug.print(host, "onRequestPermissionResult", "Requesting permission", "Requesting permission", 1);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        Debug.print(host, "onRequestPermissionResult", "LocationGranted", "LocationManager requesting", 1);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void initLocationListener() {
        Debug.print(host, "initLocationListener", "Initializing location listener", "Location listener init", 1);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Debug.print(host, "onLocationChanged", "Changing location", "Location changed", 1);
                System.out.println("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                location.setLatitude(location.getLatitude());
                location.setLongitude(location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

                hike.addLocation(location.getLatitude(), location.getLongitude());
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
    }

    public Hike getHike() {
        return hike;
    }
}
