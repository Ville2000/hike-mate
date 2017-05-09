package ville.fi.hikemate.Resources;

/**
 * Created by Ville on 8.5.2017.
 */

public class PhotoMapMarker {

    private String path;
    private double lat;
    private double lon;

    public PhotoMapMarker() {

    }

    public PhotoMapMarker(String path, double latitude, double longitude) {
        setPath(path);
        setLat(latitude);
        setLon(longitude);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String toString() {
        String str = "";

        str += "{";

        return "{ \"path\": " + path + ", \"lat\": " + String.valueOf(lat) + ", \"lng\": " + String.valueOf(lon) + "}";
    }
}
