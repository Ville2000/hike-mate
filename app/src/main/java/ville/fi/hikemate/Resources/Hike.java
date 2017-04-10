package ville.fi.hikemate.Resources;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ville on 10.4.2017.
 */

public class Hike {

    private String name;
    private List<HikeLocation> locations;

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
        locations.add(new HikeLocation(lat, lng));
    }

    public List<HikeLocation> getLocations() {
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

        hike += "]";

        return hike;
    }
}
