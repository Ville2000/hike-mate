package ville.fi.hikemate.Resources;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ville on 10.4.2017.
 */

public class HikeLocation {

    private LatLng location;

    public HikeLocation() {

    }

    public HikeLocation(double lat, double lng) {
        location = new LatLng(lat, lng);
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public LatLng getLocation() {
        return location;
    }


    @Override
    public String toString() {
        return "{ \"lat\":" + String.valueOf(location.latitude) + ", \"lng\":" + String.valueOf(location.longitude) + " }";
    }
}
