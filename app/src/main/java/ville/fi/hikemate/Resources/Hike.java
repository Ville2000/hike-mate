package ville.fi.hikemate.Resources;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ville on 10.4.2017.
 */

public class Hike {

    private String name;
    private LinkedList<HikeLocation> locations;
    private LinkedList<PhotoMapMarker> photoMapMarkers;

    public Hike() {

    }

    public Hike(String name) {
        setName(name);
        locations = new LinkedList<>();
        photoMapMarkers = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addLocation(double lat, double lng) {
        locations.add(new HikeLocation(lat, lng));
    }

    public void addPhotoMapMarker(String path, double lat, double lon) {
        photoMapMarkers.add(new PhotoMapMarker(path, lat, lon));
    }

    public LinkedList<PhotoMapMarker> getPhotoMapMarkers() { return photoMapMarkers; }

    public LinkedList<HikeLocation> getLocations() {
        return locations;
    }

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
