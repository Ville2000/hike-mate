package ville.fi.hikemate.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ville.fi.hikemate.R;

/**
 * HikePlansFragment is a fragment for the apps hike plans view.
 *
 * Hike plans were planned but are not implemented to the final product.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class HikePlansFragment extends Fragment {

    /**
     * Default constructor.
     */
    public HikePlansFragment() {
    }

    /**
     * Sets up the fragment.
     *
     * @param savedInstanceState    bundle for the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up the fragment's view.
     *
     * @param inflater              inflater for the view
     * @param container             container for the view group
     * @param savedInstanceState    bundle for the fragment
     * @return                      view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hike_plans, container, false);
    }
}
