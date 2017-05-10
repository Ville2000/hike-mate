package ville.fi.hikemate.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

/**
 * LocationService tracks the user travelled path.
 *
 * LocationService tracks the user travelled path and passes the tracked
 * locations to the map activity's receiver.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class LocationService extends Service {

    /**
     * Manages the locations.
     */
    private LocationManager locationManager;

    /**
     * Listens to the location changes.
     */
    private LocationListener locationListener;

    /**
     * List of tracked locations.
     */
    private LinkedList<LatLng> locations;

    /**
     * Status of the service (running/not running).
     */
    private Boolean isServiceRunning = false;

    /**
     * Default constructor.
     */
    public LocationService() {
    }

    /**
     * Returns a binder for the service.
     *
     * @param intent    intent for the service
     * @return          binder for the service
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Sets up the service.
     *
     * Initializes the location manager and location listener and sets the
     * services status to true (running).
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isServiceRunning) {
            isServiceRunning = true;

            locations = new LinkedList<>();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            initLocationListener();

            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, locationListener);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }
        return START_STICKY;
    }

    /**
     * Sends a location to the broadcast manager.
     *
     * @param latitude  latitude of the location
     * @param longitude longitude of the location
     */
    private void sendGPSLocations(double latitude, double longitude) {
        Intent i = new Intent("GPSLocations");
        locations.add(new LatLng(latitude, longitude));
        i.putExtra("Locations", locations);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    /**
     * Initializes the service's location listener.
     *
     * On location changed sends the new location to the sendGPSLocations
     * method.
     */
    private void initLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                sendGPSLocations(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
    }

    /**
     * Stops the service.
     *
     * Sets the location manager and location listener to null and sets the
     * service's status to false (not running).
     *
     * @param name
     * @return
     */
    @Override
    public boolean stopService(Intent name) {
        System.out.println("Stopping LocationService");
        locationManager = null;
        locationListener = null;
        isServiceRunning = false;
        return super.stopService(name);
    }
}
