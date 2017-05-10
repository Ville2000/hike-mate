package ville.fi.hikemate.Resources;

import java.util.LinkedList;

/**
 * Hike is a user tracked hike.
 *
 * Hike stores a name (which is actually the hike's date) and the time of the
 * hike. It also has a list of locations and a photo marker list.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class Hike {

    /**
     * Name/Date of the hike.
     */
    private String name;

    /**
     * Time of the hike in hours and minutes.
     */
    private String time;

    /**
     * List of hike's locations.
     */
    private LinkedList<HikeLocation> locations;

    /**
     * List of hike's photo markers.
     */
    private LinkedList<PhotoMapMarker> photoMapMarkers;

    /**
     * Default constructor.
     */
    public Hike() {

    }

    /**
     * Constructs a hike and sets it a name and inits the lists.
     *
     * @param name  name of the hike
     */
    public Hike(String name) {
        setName(name);
        locations = new LinkedList<>();
        photoMapMarkers = new LinkedList<>();
    }

    /**
     * Returns the name of the hike.
     *
     * @return  name of the hike
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hike.
     *
     * @param name  name of the hike
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the time of the hike.
     *
     * @return      time of the hike
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time of the hike.
     *
     * @param time  time of the hike
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Adds a new location to the hike's location list.
     *
     * @param lat   latitude of the location
     * @param lng   longitude of the location
     */
    public void addLocation(double lat, double lng) {
        locations.add(new HikeLocation(lat, lng));
    }

    /**
     * Adds a new photo marker to the hike's photo marker list.
     *
     * @param path  file path of the photo
     * @param lat   latitude of the marker's location
     * @param lon   longitude of the marker's location
     */
    public void addPhotoMapMarker(String path, double lat, double lon) {
        photoMapMarkers.add(new PhotoMapMarker(path, lat, lon));
    }

    /**
     * Returns the hike's photo markers list.
     *
     * @return  hike's photo markers list.
     */
    public LinkedList<PhotoMapMarker> getPhotoMapMarkers() {
        return photoMapMarkers;
    }

    /**
     * Returns the hike's location list.
     *
     * @return  hike's locations
     */
    public LinkedList<HikeLocation> getLocations() {
        return locations;
    }

    /**
     * Returns the hike in a string format.
     *
     * @return  hike's string format
     */
    @Override
    public String toString() {
        String hike = "";
        hike += "[";

        for (HikeLocation l : locations) {
            hike += l.toString();

            if (!l.equals(locations.get(locations.size() - 1))) {
                hike += ", ";
            }
        }

        hike += ", ";

        for (PhotoMapMarker p : photoMapMarkers) {
            hike += p.toString();

            if (!p.equals(photoMapMarkers.get(photoMapMarkers.size() -1))) {
                hike += ", ";
            }
        }

        hike += "]";

        return hike;
    }
}
