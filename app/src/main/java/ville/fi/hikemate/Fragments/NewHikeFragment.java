package ville.fi.hikemate.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ville.fi.hikemate.R;

/**
 * NewHikeFragment is the fragment for the new hike view.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class NewHikeFragment extends Fragment {

    /**
     * Default constructor.
     */
    public NewHikeFragment() {
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
        return inflater.inflate(R.layout.fragment_new_hike, container, false);
    }
}
