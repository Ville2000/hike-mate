package ville.fi.hikemate.Resources;

/**
 * Created by Ville on 10.4.2017.
 */

public class HikeLocation {

    private double lat;
    private double lng;

    public HikeLocation(double lat, double lng) {
        setLat(lat);
        setLng(lng);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "{ \"lat\":" + String.valueOf(getLat()) + ", \"lng\":" + String.valueOf(getLng()) + " }";
    }
}
