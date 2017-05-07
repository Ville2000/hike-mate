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

public class LocationService extends Service {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private LinkedList<LatLng> locations;
    private Boolean isServiceRunning = false;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

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

    private void sendGPSLocations(double latitude, double longitude) {
        Intent i = new Intent("GPSLocations");
        locations.add(new LatLng(latitude, longitude));
        i.putExtra("Locations", locations);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

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

    @Override
    public boolean stopService(Intent name) {
        System.out.println("Stopping LocationService");
        locationManager = null;
        locationListener = null;
        isServiceRunning = false;
        return super.stopService(name);
    }
}
