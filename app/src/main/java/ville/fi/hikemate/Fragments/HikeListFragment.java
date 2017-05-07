package ville.fi.hikemate.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;

import ville.fi.hikemate.Activities.StoredMapActivity;
import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Utils.HikeListAdapter;
import ville.fi.hikemate.Utils.StorageHandler;

public class HikeListFragment extends Fragment {
    LinkedList<Hike> hikes;
    ObjectMapper mapper;
    private TextView emptyList;

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
        View view = inflater.inflate(R.layout.fragment_hike_list, container, false);

        emptyList = (TextView) view.findViewById(R.id.list_emptyList);

        StorageHandler sh = new StorageHandler();
        hikes = sh.readStorage(getActivity());
        if (hikes.size() < 1) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
        }

        System.out.println("Hike 0: " + hikes.size());

        HikeListAdapter adapter = new HikeListAdapter(getActivity(), R.layout.hike_list_item, hikes);

        ListView listView = (ListView) view.findViewById(R.id.ListView_hikes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
