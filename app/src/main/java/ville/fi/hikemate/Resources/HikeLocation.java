package ville.fi.hikemate.Resources;

/**
 * HikeLocation stores the user's location data.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class HikeLocation {

    /**
     * Latitude of the location.
     */
    private double lat;

    /**
     * Longitude of the location.
     */
    private double lng;

    /**
     * Default constructor.
     */
    public HikeLocation() {

    }

    /**
     * Constructs a new hike location and sets it's attributes.
     *
     * @param lat   latitude of the location
     * @param lng   longitude of the location
     */
    public HikeLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Sets the latitude of the location.
     *
     * @param lat   latitude of the location
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Sets the longitude of the location.
     *
     * @param lng   longitude of the location
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * Returns the latitude of the location.
     *
     * @return  latitude of the location
     */
    public double getLat() {
        return lat;
    }

    /**
     * Returns the longitude of the location.
     *
     * @return  longitude of the location
     */
    public double getLng() { return lng; }

    /**
     * Returns the location in string format.
     *
     * @return  the location in string format
     */
    @Override
    public String toString() {
        return "{ \"lat\":" + String.valueOf(lat) + ", \"lng\":" + String.valueOf(lng) + " }";
    }
}
