package ville.fi.hikemate.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeList;
import ville.fi.hikemate.Utils.StorageHandler;

public class HikeListFragment extends Fragment {

    public HikeListFragment() {
        System.out.println("HikeListFragment");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hike_list, container, false);

        Hike hike = new Hike("Hike 1");
        hike.addLocation(1.23, 2.34);
        HikeList hikeList = new HikeList();
        hikeList.add(hike);

        ObjectMapper mapper = new ObjectMapper();
        StorageHandler sh = new StorageHandler();
        sh.writeStorage(getActivity(), hikeList);

        HikeList hikes = sh.readStorage(getActivity());
        System.out.println("Name: " + hikes.get(0).getName());

        ArrayAdapter<HikeList> adapter = new ArrayAdapter(getActivity(),
                R.layout.hike_list_item, hikes);
        ListView listView = (ListView) view.findViewById(R.id.ListView_hikes);
        listView.setAdapter(adapter);
        return view;
    }
}
