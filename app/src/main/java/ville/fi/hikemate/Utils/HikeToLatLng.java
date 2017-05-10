package ville.fi.hikemate.Utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeLocation;

/**
 * HikeToLatLng transfers a hike's location list to a LatLng list.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class HikeToLatLng {

    /**
     * Returns a LatLng list of hike's locations.
     *
     * @param hike  hike to transfer
     * @return      latlng list of hike's locations
     */
    public static LinkedList<LatLng> getLatLng(Hike hike) {
        LinkedList<LatLng> locs = new LinkedList<>();

        for (HikeLocation loc: hike.getLocations()) {
            locs.add(new LatLng(loc.getLat(), loc.getLng()));
        }

        return locs;
    }
}
