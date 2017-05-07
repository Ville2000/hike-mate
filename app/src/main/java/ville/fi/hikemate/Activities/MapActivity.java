package ville.fi.hikemate.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeLocation;
import ville.fi.hikemate.Services.LocationService;
import ville.fi.hikemate.Utils.Debug;
import ville.fi.hikemate.Utils.HikeToLatLng;
import ville.fi.hikemate.Utils.StorageHandler;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    private Context host = this;
    private FragmentActivity thisActivity = this;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    private GoogleMap mMap;
    private LinkedList<Hike> hikes;
    private Hike hike;
    private StorageHandler sh;
    private Polyline hikePolyLine;
    private boolean firstLocationGot = false;
    private boolean isTrackingLocation;
    private Intent mainActivityIntent;
    private LinkedList<LatLng> hikeLatLng;
    private Intent locationServiceIntent;
    private DateFormat dateFormat;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mainActivityIntent = new Intent(host, MainActivity.class);
        isTrackingLocation = true;
        sh = new StorageHandler();
        hikes = sh.readStorage(host);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        hike = new Hike(dateFormat.format(new Date()));
        hikeLatLng = HikeToLatLng.getLatLng(hike);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocalBroadcastManager.getInstance(host).registerReceiver(mMessageReceiver, new IntentFilter("GPSLocations"));
        locationServiceIntent = new Intent(host, LocationService.class);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(host,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Asking permissions, no permissions");
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
            }
        } else {
            startService(locationServiceIntent);
        }
    }

    /**
     * Returns the user to the main activity view.
     *
     * @param v
     */
    public void cancelTracking(View v) {
        isTrackingLocation = false;
        stopService(locationServiceIntent);
        startActivity(mainActivityIntent);
    }

    /**
     * Saves the hike.
     *
     * Saves the hike to the storage and returns the user to the main activity.
     *
     * @param v
     */
    public void saveHike(View v) {
        isTrackingLocation = false;
        stopService(locationServiceIntent);

        for (LatLng latLng : hikeLatLng) {
            hike.addLocation(latLng.latitude, latLng.longitude);
        }

        hikes.add(hike);
        sh.writeStorage(host, hikes);
        startActivity(mainActivityIntent);
    }

    public void takeImage(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            System.out.println("We have a camera activity!");
            // Create the File where the photo should go
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName);

            System.out.println("PhotoFile: " + photoFile);

            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            System.out.println("We have results!");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    /**
     * Checks whether the user has the required permissions or not.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Debug.print(host, "onRequestPermissionResult", "Requesting permission", "Requesting permission", 1);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService(locationServiceIntent);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // TODO: Go back to MainActivity / Exit application.
                }
                return;
            }
        }
    }

    /**
     * Returns the currently active hike.
     *
     * @return
     */
    public Hike getHike() {
        return hike;
    }

    /**
     * Receiver for the LocationService's locations.
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        /**
         * Sets the given locations to a linked list and draws a line according to them.
         *
         * @param context
         * @param intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("Receiving broadcast");
            LinkedList<LatLng> locations = (LinkedList) intent.getExtras().get("Locations");

            if (isTrackingLocation) {
                hikeLatLng.add(locations.getLast());

                hikePolyLine = mMap.addPolyline(new PolylineOptions()
                        .clickable(false));
                hikePolyLine.setPoints(hikeLatLng);

                if (firstLocationGot == false) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locations.getLast().latitude, locations.getLast().longitude), 15));
                    firstLocationGot = true;
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locations.getLast().latitude, locations.getLast().longitude), mMap.getCameraPosition().zoom));
                }
            }
        }
    };
}
