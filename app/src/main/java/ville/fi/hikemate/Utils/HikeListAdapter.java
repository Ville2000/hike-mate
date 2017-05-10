package ville.fi.hikemate.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;

/**
 * Created by Ville on 10.4.2017.
 */

public class HikeListAdapter extends ArrayAdapter<Hike> {

    private Context host;
    private LinkedList<Hike> hikes;

    public HikeListAdapter(Context host, int resource, LinkedList<Hike> hikeList) {
        super(host, resource, hikeList);
        this.host = host;
        this.hikes = hikeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        HikeHolder hh;

        if (view == null) {
            hh = new HikeHolder();
            LayoutInflater inflater = ((Activity) host).getLayoutInflater();
            view = inflater.inflate(R.layout.hike_list_item, parent, false);

            if (position % 2 == 1) {
                view.setBackgroundColor(Color.parseColor("#D1C4E9"));
            }

            hh.listTitle = (TextView) view.findViewById(R.id.list_item_title);
            hh.listSubtitle = (TextView) view.findViewById(R.id.list_item_subtitle);
            view.setTag(hh);
        } else {
            hh = (HikeHolder) view.getTag();
        }

        Hike hike = hikes.get(position);
        hh.listTitle.setText(hike.getName());
        hh.listSubtitle.setText(hike.getTime());

        return view;
    }

    class HikeHolder {
        public TextView listTitle;
        public TextView listSubtitle;
    }
}
