package ville.fi.hikemate.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

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
import ville.fi.hikemate.Resources.PhotoMapMarker;
import ville.fi.hikemate.Utils.HikeToLatLng;
import ville.fi.hikemate.Utils.MarkerInfoWindowAdapter;
import ville.fi.hikemate.Utils.StorageHandler;

/**
 * StoredMapActivity shows the user tracked route and the photo markers.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class StoredMapActivity extends FragmentActivity
        implements OnMapReadyCallback {

    /**
     * Context of the activity.
     */
    private Context host = this;

    /**
     * This activity.
     */
    private FragmentActivity thisActivity = this;

    /**
     * Map of the view.
     */
    private GoogleMap mMap;

    /**
     * List of hikes.
     */
    private LinkedList<Hike> hikes;

    /**
     * List of hike's location points.
     */
    private LinkedList<LatLng> hike;

    /**
     * List of hike's photo markers.
     */
    private LinkedList<PhotoMapMarker> photoMapMarkers;

    /**
     * Handler for the reading and writing of the storage.
     */
    private StorageHandler sh;

    /**
     * Polyline of the user tracked route.
     */
    private Polyline hikePolyLine;

    /**
     * Integer for the writing storage permission.
     */
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    /**
     * Tracks the last clicked marker.
     */
    private Marker lastClicked;

    /**
     * Initializes this activity's attributes.
     *
     * @param savedInstanceState    bundle for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_map);

        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    thisActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            sh = new StorageHandler();

            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.stored_map);
            mapFragment.getMapAsync(this);

            hikes = sh.readStorage(host);
            hike = HikeToLatLng.getLatLng(hikes.get(
                    (int) getIntent().getExtras().get("position")));
            photoMapMarkers = hikes.get(
                    (int) getIntent().getExtras().get("position"))
                    .getPhotoMapMarkers();
        }
    }

    /**
     * Sets the map attribute to be the map that was got as a parameter.
     *
     * Draws the polyline according to the user tracked location points to
     * the map. Adds photo markers to the path. Moves the camera to the
     * first location point of the hike.
     *
     * @param googleMap     map fragment that was readied
     */
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(hike.get(0).latitude, hike.get(0).longitude), 15));
    }

    /**
     * Initializes the marker click listener.
     *
     * @param map   map fragment of the activity
     */
    private void initMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (lastClicked != null) {
                    lastClicked.hideInfoWindow();

                    if (lastClicked.equals(marker)) {
                        lastClicked = null;

                        return true;
                    }
                }

                marker.showInfoWindow();
                lastClicked = marker;

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(marker.getPosition().latitude + 0.005,
                                marker.getPosition().longitude),
                        15));

                return true;
            }
        });
    }

    /**
     * Reads the external storage and set ups the hike and markers.
     *
     * @param requestCode   code of the request
     * @param permissions   array of permission
     * @param grantResults  array of results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    sh = new StorageHandler();

                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.stored_map);
                    mapFragment.getMapAsync(this);

                    hikes = sh.readStorage(host);
                    hike = HikeToLatLng.getLatLng(hikes.get((int) getIntent()
                            .getExtras().get("position")));
                    photoMapMarkers = hikes.get((int) getIntent().getExtras()
                            .get("position")).getPhotoMapMarkers();
                } else {

                }

                return;
            }
        }
    }
}
