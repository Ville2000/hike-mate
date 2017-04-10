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
}
