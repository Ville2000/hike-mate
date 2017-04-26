package ville.fi.hikemate.Activities;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeLocation;
import ville.fi.hikemate.Utils.HikeToLatLng;
import ville.fi.hikemate.Utils.StorageHandler;

public class StoredMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Context host = this;
    private GoogleMap mMap;
    private LinkedList<Hike> hikes;
    private LinkedList<LatLng> hike;
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        hikePolyLine = mMap.addPolyline(new PolylineOptions()
                .clickable(false));
        hikePolyLine.setPoints(hike);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(hike.get(0).latitude, hike.get(0).longitude), 15));
    }

    public LinkedList<LatLng> getLatLng(Hike hike) {
        LinkedList<LatLng> locs = new LinkedList<>();

        for (HikeLocation loc: hike.getLocations()) {
            locs.add(new LatLng(loc.getLat(), loc.getLng()));
        }

        return locs;
    }
}
