package ville.fi.hikemate.Activities;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import ville.fi.hikemate.Utils.StorageHandler;

public class StoredMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Context host = this;
    private GoogleMap mMap;
    private LinkedList<Hike> hikes;
    private LinkedList<LatLng> hike;
    private LinkedList<PhotoMapMarker> photoMapMarkers;
    private StorageHandler sh;
    private Polyline hikePolyLine;

    /**
     * Initializes this activity's attributes.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_map);

        sh = new StorageHandler();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.stored_map);
        mapFragment.getMapAsync(this);

        hikes = sh.readStorage(host);
        hike = HikeToLatLng.getLatLng(hikes.get((int) getIntent().getExtras().get("position")));
        photoMapMarkers = hikes.get((int) getIntent().getExtras().get("position")).getPhotoMapMarkers();
        Debug.toastThis(this, hike.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        hikePolyLine = mMap.addPolyline(new PolylineOptions()
                .clickable(false));
        hikePolyLine.setPoints(hike);

        if (photoMapMarkers != null) {
            for (PhotoMapMarker p : photoMapMarkers) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(p.getLat(), p.getLon())));
            }
        }

        initMarkerClickListener(googleMap);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(hike.get(0).latitude, hike.get(0).longitude), 15));
    }

    private void initMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Debug.toastThis(host, "Clicking Markers");
                marker.showInfoWindow();
                return false;
            }
        });
    }
}
