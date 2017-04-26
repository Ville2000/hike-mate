package ville.fi.hikemate.Resources;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ville on 10.4.2017.
 */

public class HikeLocation {

    private double lat;
    private double lng;

    public HikeLocation() {

    }

    public HikeLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }
    public double getLng() { return lng; }


    @Override
    public String toString() {
        return "{ \"lat\":" + String.valueOf(lat) + ", \"lng\":" + String.valueOf(lng) + " }";
    }
}
