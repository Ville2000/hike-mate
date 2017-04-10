package ville.fi.hikemate.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.HikeList;

/**
 * Created by Ville on 10.4.2017.
 */

public class HikeListAdapter extends ArrayAdapter<HikeList> {

    private Context host;
    private List<HikeList> hikes;

    public HikeListAdapter(Context host, int resource, List<HikeList> hikeList) {
        super(host, resource, hikeList);
        this.host = host;
        this.hikes = hikeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) host).getLayoutInflater();
            view = inflater.inflate(R.layout.hike_list_item, parent, false);
        }
            /*
            employeeHolder.title = (TextView) view.findViewById(R.id.tvtextView);


            view.setTag(employeeHolder);
        } else {
            employeeHolder = (EmployeeItemHolder) view.getTag();

        }

        Employee eItem = (Employee) this.Info.get(position);
        employeeHolder.title.setText(eItem.getEmployeeTitle()+"-"+eItem.getEmplyeeName());

        */

        return view;
    }
}
