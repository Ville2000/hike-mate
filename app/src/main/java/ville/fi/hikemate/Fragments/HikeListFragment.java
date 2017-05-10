package ville.fi.hikemate.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

import ville.fi.hikemate.Activities.StoredMapActivity;
import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Utils.HikeListAdapter;
import ville.fi.hikemate.Utils.StorageHandler;

/**
 * HikeListFragment is a fragment for the list view of the user's hikes.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class HikeListFragment extends Fragment {

    /**
     * List of user saved hikes.
     */
    LinkedList<Hike> hikes;

    /**
     * Text view for the info message.
     */
    private TextView emptyList;

    /**
     * Default constructor.
     */
    public HikeListFragment() {

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
     * * Starts the stored map activity if a list item is clicked.
     *
     * @param inflater              inflater for the view
     * @param container             oontainer for the view group
     * @param savedInstanceState    bundle for the fragment
     * @return                      view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hike_list, container,
                false);

        emptyList = (TextView) view.findViewById(R.id.list_emptyList);

        StorageHandler sh = new StorageHandler();
        hikes = sh.readStorage(getActivity());

        if (hikes.size() < 1) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
        }

        HikeListAdapter adapter = new HikeListAdapter(getActivity(),
                R.layout.hike_list_item, hikes);

        ListView listView = (ListView) view.findViewById(R.id.ListView_hikes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Starts an activity on item click.
             *
             * @param parent    parent of the adapter
             * @param view      view of the adapter
             * @param position  position of the item
             * @param id        id of the item
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), StoredMapActivity.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });

        return view;
    }
}
