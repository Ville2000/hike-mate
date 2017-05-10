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
 * HikeListAdapter is an adapter for the hike list view.
 *
 * HikeListAdapter sets a custom view for the hike list.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class HikeListAdapter extends ArrayAdapter<Hike> {

    /**
     * Host of the adapter.
     */
    private Context host;

    /**
     * List of the user's hikes.
     */
    private LinkedList<Hike> hikes;

    /**
     * Constructs a new hike list adapter and sets it's attributes.
     *
     * @param host      host of the adapter
     * @param resource  resource of the adapter
     * @param hikeList  list of user saved hikes
     */
    public HikeListAdapter(Context host, int resource, LinkedList<Hike> hikeList) {
        super(host, resource, hikeList);
        this.host = host;
        this.hikes = hikeList;
    }

    /**
     * Returns a view for the hike list.
     *
     * Sets the background color of every odd row to a light purple
     * and sets the title and subtitle of the hike accordingly.
     *
     * @param position      position of an item on the list
     * @param convertView   convert view of the list
     * @param parent        parent of the list
     * @return              returns a view for the list
     */
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

    /**
     * HikeHolder is a helper class to hold hike's list view data.
     *
     * @author      Ville Haapavaara
     * @version     10.5.2017
     * @since       1.8
     */
    class HikeHolder {
        public TextView listTitle;
        public TextView listSubtitle;
    }
}
