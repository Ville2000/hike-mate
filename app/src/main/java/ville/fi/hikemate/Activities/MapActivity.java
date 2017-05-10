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
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Services.LocationService;
import ville.fi.hikemate.Utils.Debug;
import ville.fi.hikemate.Utils.HikeToLatLng;
import ville.fi.hikemate.Utils.StorageHandler;

/**
 * MapActivity is the activity that displays the user tracked path.
 *
 * The user taken path is drawn to the map. User can cancel, save or take a
 * photo during the hike.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    /**
     * Context of the activity.
     */
    private Context host = this;

    /**
     * This activity.
     */
    private FragmentActivity thisActivity = this;

    /**
     * Integer used for permission request.
     */
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 0;

    /**
     * Map of the view.
     */
    private GoogleMap mMap;

    /**
     * List of user's hikes.
     */
    private LinkedList<Hike> hikes;

    /**
     * Current hike.
     */
    private Hike hike;

    /**
     * Handler for loading and saving the hike.
     */
    private StorageHandler sh;

    /**
     * Polyline for the user travelled path.
     */
    private Polyline hikePolyLine;

    /**
     * Status of the tracking process.
     */
    private boolean firstLocationGot = false;

    /**
     * Different status of the tracking process.
     */
    private boolean isTrackingLocation;

    /**
     * Yet another status of the tracking process.
     */
    private boolean locationFound = false;

    /**
     * Intent for the main activity.
     */
    private Intent mainActivityIntent;

    /**
     * List of the hike's lat and lng points.
     */
    private LinkedList<LatLng> hikeLatLng;

    /**
     * Intent for the location service.
     */
    private Intent locationServiceIntent;

    /**
     * Formatter for the dates.
     */
    private DateFormat dateFormat;

    /**
     * Integer for the image capture request.
     */
    private final int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * Integer for the photo capture request.
     */
    private final int REQUEST_TAKE_PHOTO = 1;

    /**
     * Previously taken photo's file path.
     */
    private String mCurrentPhotoPath;

    /**
     * Button for taking photos.
     */
    private Button takeImageButton;

    /**
     * Button for cancelling the tracking.
     */
    private Button cancelTrackingButton;

    /**
     * Button for saving the tracked hike.
     */
    private Button saveTrackingButton;

    /**
     * Sets up the activity.
     *
     * Initializes attributes used in this activity and asks user permission
     * for the location tracking.
     *
     * @param savedInstanceState    bundle for the activity
     */
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

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocalBroadcastManager.getInstance(host).registerReceiver(
                mMessageReceiver, new IntentFilter("GPSLocations"));
        locationServiceIntent = new Intent(host, LocationService.class);

        if (ContextCompat.checkSelfPermission(host,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    thisActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
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
     * Cancels tracking and returns the user to the main activity view.
     *
     * @param v     view of the activity
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
     * @param v     view of the activity
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

    /**
     * Takes a photo using device's camera.
     *
     * @param v     view of the activity.
     */
    public void takeImage(View v) {
        if (locationFound) {
            Intent takePictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(
                    getPackageManager()) != null) {

                File storageDir = Environment
                        .getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_.jpg";
                File photoFile = new File(storageDir, imageFileName);
                mCurrentPhotoPath = photoFile.getAbsolutePath();

                if (photoFile != null) {

                    Uri photoURI = Uri.fromFile(photoFile);
                    takePictureIntent.putExtra(
                            MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(
                            takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
    }

    /**
     * Adds a marker to a map.
     *
     * Adds a marker to a map and stores the user taken photo to the
     * device's gallery.
     *
     * @param requestCode   code of the request
     * @param resultCode    code of the result
     * @param data          data of the result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("We have results!");

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(hikeLatLng.getLast().latitude,
                            hikeLatLng.getLast().longitude))
                    );
            hike.addPhotoMapMarker(mCurrentPhotoPath,
                    hikeLatLng.getLast().latitude,
                    hikeLatLng.getLast().longitude);
            galleryAddPic();
        }
    }

    /**
     * Sets the user taken photo to the gallery of the device.
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    /**
     * Sets the map attribute to be the map that was got as a parameter.
     *
     * @param googleMap     map fragment that was readied
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    /**
     * Checks whether the user has the required permissions or not.
     *
     * @param requestCode       code of the request
     * @param permissions       array of permissions
     * @param grantResults      array of permission results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    startService(locationServiceIntent);
                    Debug.toastThisLonger(host, "Fetching location...");
                } else {

                }

                return;
            }
        }
    }

    /**
     * Returns the currently active hike.
     *
     * @return      returns the currenlty active hike
     */
    public Hike getHike() {
        return hike;
    }

    /**
     * Receiver for the LocationService's locations.
     *
     * Receives location services location changes and adds these locations to
     * the locations list. Adds a new polyline to the map as well.
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
            LinkedList<LatLng> locations =
                    (LinkedList) intent.getExtras().get("Locations");

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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(locations.getLast().latitude,
                                    locations.getLast().longitude), 15));
                    firstLocationGot = true;
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(locations.getLast().latitude,
                                    locations.getLast().longitude),
                            mMap.getCameraPosition().zoom));
                }
            }
        }
    };
}
