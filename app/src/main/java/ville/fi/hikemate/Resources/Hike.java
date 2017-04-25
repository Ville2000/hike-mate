package ville.fi.hikemate.Resources;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ville on 10.4.2017.
 */

public class Hike {

    private String name;
    private List<LatLng> locations;

    public Hike() {

    }

    public Hike(String name) {
        setName(name);
        locations = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addLocation(double lat, double lng) {
        locations.add(new LatLng(lat, lng));
    }

    public List<LatLng> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        String hike = "";
        hike += "[";
        for (LatLng l : locations) {
            hike += "{ \"lat\":" + String.valueOf(l.latitude) + ", \"lng\":" + String.valueOf(l.longitude) + " }";
            if (!l.equals(locations.get(locations.size() - 1))) {
                hike += ", ";
            }
        }

        hike += "]";

        return hike;
    }
}
