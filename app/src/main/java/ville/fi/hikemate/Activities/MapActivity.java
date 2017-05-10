package ville.fi.hikemate.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
    private boolean locationFound = false;
    private Intent mainActivityIntent;
    private LinkedList<LatLng> hikeLatLng;
    private Intent locationServiceIntent;
    private DateFormat dateFormat;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private Button takeImageButton;
    private Button cancelTrackingButton;
    private Button saveTrackingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mainActivityIntent = new Intent(host, MainActivity.class);
        isTrackingLocation = true;
        sh = new StorageHandler();
        hikes = sh.readStorage(host);
        Date date = new Date();
        dateFormat = new SimpleDateFormat("d MMM yyyy");
        hike = new Hike(dateFormat.format(date));
        dateFormat = new SimpleDateFormat("HH:mm");
        hike.setTime(dateFormat.format(date));
        hikeLatLng = HikeToLatLng.getLatLng(hike);

        takeImageButton = (Button) findViewById(R.id.button_takeImage);
        takeImageButton.setEnabled(false);
        cancelTrackingButton = (Button) findViewById(R.id.button_cancel);
        cancelTrackingButton.setEnabled(false);
        saveTrackingButton = (Button) findViewById(R.id.button_stopAndSave);
        saveTrackingButton.setEnabled(false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocalBroadcastManager.getInstance(host).registerReceiver(mMessageReceiver, new IntentFilter("GPSLocations"));
        locationServiceIntent = new Intent(host, LocationService.class);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(host,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

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
            Debug.toastThisLonger(host, "Fetching location...");
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

        hikes.addFirst(hike);
        sh.writeStorage(host, hikes);
        startActivity(mainActivityIntent);
    }

    public void takeImage(View v) {
        if (locationFound) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_.jpg";
                File photoFile = new File(storageDir, imageFileName);
                mCurrentPhotoPath = photoFile.getAbsolutePath();

                if (photoFile != null) {

                    Uri photoURI = Uri.fromFile(photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("We have results!");

//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(hikeLatLng.getLast().latitude, hikeLatLng.getLast().longitude))
                    );
            hike.addPhotoMapMarker(mCurrentPhotoPath,
                    hikeLatLng.getLast().latitude,
                    hikeLatLng.getLast().longitude);
            galleryAddPic();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService(locationServiceIntent);
                    Debug.toastThisLonger(host, "Fetching location...");
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
            LinkedList<LatLng> locations = (LinkedList) intent.getExtras().get("Locations");

            if (locationFound == false) {
                Debug.toastThis(host, "Location fetched, tracking started!");
                cancelTrackingButton.setEnabled(true);
                saveTrackingButton.setEnabled(true);
                takeImageButton.setEnabled(true);
                locationFound = true;
            }

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
