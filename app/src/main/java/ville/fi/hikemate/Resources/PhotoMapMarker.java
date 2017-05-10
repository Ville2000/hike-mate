package ville.fi.hikemate.Resources;

/**
 * PhotoMapMarker is a class that stores data of a map marker.
 *
 * Stores the file path of the photo map marker and it's location.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class PhotoMapMarker {

    /**
     * File path of the photo.
     */
    private String path;

    /**
     * Marker's latitude position.
     */
    private double lat;

    /**
     * Marker's longitude position.
     */
    private double lon;

    /**
     * Default constructor.
     */
    public PhotoMapMarker() {

    }

    /**
     * Constructs a new photo map marker and sets it's attributes.
     *
     * @param path      file path of the photo
     * @param latitude  latitude of the marker
     * @param longitude longitude of the marker
     */
    public PhotoMapMarker(String path, double latitude, double longitude) {
        setPath(path);
        setLat(latitude);
        setLon(longitude);
    }

    /**
     * Returns the photo's file path.
     *
     * @return  photo's file path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the photo's file path.
     *
     * @param path  photo's file path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns the latitude of the marker.
     *
     * @return  latitude of the marker
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets the latitude of the marker.
     *
     * @param lat   latitude of the marker
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Returns the longitude of the marker.
     *
     * @return  longitude of the marker
     */
    public double getLon() {
        return lon;
    }

    /**
     * Sets the longitude of the marker.
     *
     * @param lon   longitude of the marker
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Returns the object in string format.
     *
     * @return  the object in string format
     */
    @Override
    public String toString() {
        String str = "";

        str += "{";

        return "{ \"path\": " + path + ", \"lat\": " + String.valueOf(lat) + ", \"lng\": " + String.valueOf(lon) + "}";
    }
}
