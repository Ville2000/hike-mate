package ville.fi.hikemate.Utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeLocation;

/**
 * Created by Ville on 25.4.2017.
 */

public class HikeToLatLng {

    public static LinkedList<LatLng> getLatLng(Hike hike) {
        LinkedList<LatLng> locs = new LinkedList<>();

        for (HikeLocation loc: hike.getLocations()) {
            locs.add(new LatLng(loc.getLat(), loc.getLng()));
        }

        return locs;
    }
}
