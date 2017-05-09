package ville.fi.hikemate.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeLocation;
import ville.fi.hikemate.Resources.PhotoMapMarker;
import ville.fi.hikemate.Utils.Debug;
import ville.fi.hikemate.Utils.HikeToLatLng;
import ville.fi.hikemate.Utils.MarkerInfoWindowAdapter;
import ville.fi.hikemate.Utils.StorageHandler;

public class StoredMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Context host = this;
    private FragmentActivity thisActivity = this;
    private GoogleMap mMap;
    private LinkedList<Hike> hikes;
    private LinkedList<LatLng> hike;
    private LinkedList<PhotoMapMarker> photoMapMarkers;
    private StorageHandler sh;
    private Polyline hikePolyLine;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private Marker lastClicked;

    /**
     * Initializes this activity's attributes.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_map);

        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            sh = new StorageHandler();

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.stored_map);
            mapFragment.getMapAsync(this);

            hikes = sh.readStorage(host);
            hike = HikeToLatLng.getLatLng(hikes.get((int) getIntent().getExtras().get("position")));
            photoMapMarkers = hikes.get((int) getIntent().getExtras().get("position")).getPhotoMapMarkers();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(this));


        hikePolyLine = mMap.addPolyline(new PolylineOptions()
                .clickable(false));
        hikePolyLine.setPoints(hike);

        if (photoMapMarkers != null) {
            for (PhotoMapMarker p : photoMapMarkers) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(p.getLat(), p.getLon()))
                        .snippet(p.getPath()));
            }
        }

        initMarkerClickListener(googleMap);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(hike.get(0).latitude, hike.get(0).longitude), 15));
    }

    private void initMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (lastClicked != null) {
                    // Close the info window
                    lastClicked.hideInfoWindow();

                    // Is the marker the same marker that was already open
                    if (lastClicked.equals(marker)) {
                        // Nullify the lastOpenned object
                        lastClicked = null;
                        // Return so that the info window isn't openned again
                        return true;
                    }
                }

                // Open the info window for the marker
                marker.showInfoWindow();
                // Re-assign the last opened such that we can close it later
                lastClicked = marker;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(marker.getPosition().latitude + 0.003,
                                marker.getPosition().longitude),
                        15));

                // Event was handled by our code do not launch default behaviour.
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    sh = new StorageHandler();

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.stored_map);
                    mapFragment.getMapAsync(this);

                    hikes = sh.readStorage(host);
                    hike = HikeToLatLng.getLatLng(hikes.get((int) getIntent().getExtras().get("position")));
                    photoMapMarkers = hikes.get((int) getIntent().getExtras().get("position")).getPhotoMapMarkers();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
