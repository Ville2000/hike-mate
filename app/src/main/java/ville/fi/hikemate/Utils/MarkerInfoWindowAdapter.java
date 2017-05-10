package ville.fi.hikemate.Utils;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import ville.fi.hikemate.R;

/**
 * MarkerInfoWindowAdapter is a custom adapter for the photo markers.
 *
 * MarkerInfoWindowAdapter sets the info window to contain only the photo
 * of the specific map marker.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    /**
     * View of the info window.
     */
    private View mInfoWindow;

    /**
     * Info window's contents.
     */
    private View mInfoContents;

    /**
     * Image loader for the photo.
     */
    private ImageLoader imageLoader;

    /**
     * Configuration of the image loader.
     */
    private ImageLoaderConfiguration configuration;

    /**
     * Options for the photo.
     */
    private DisplayImageOptions options;

    /**
     * Constructs a new marker info window adapter and sets it's attributes.
     *
     * @param host  host of the adapter
     */
    public MarkerInfoWindowAdapter(FragmentActivity host) {
        mInfoWindow = host.getLayoutInflater().inflate(
                R.layout.marker_info_window, null);
        mInfoContents = host.getLayoutInflater().inflate(
                R.layout.marker_info_window_contents, null);
        configuration = new ImageLoaderConfiguration.Builder(host).build();
        ImageLoader.getInstance().init(configuration);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().build();
    }

    /**
     * Returns the view of the info window.
     *
     * @param marker    info window's marker
     * @return          the view of the info window
     */
    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mInfoWindow);
        return mInfoWindow;
    }

    /**
     * Returns the view of the contents of the info window.
     *
     * @param marker    info window's marker
     * @return          the view of the info window's contents
     */
    @Override
    public View getInfoContents(Marker marker) {
        render(marker, mInfoContents);
        return mInfoContents;
    }

    /**
     * Renders the image of the photo marker.
     *
     * @param marker    photo's marker
     * @param view      marker's view
     */
    private void render(final Marker marker, View view) {
        ImageView image = (ImageView) view.findViewById(R.id.marker_photo);
        imageLoader.displayImage("file:///" + marker.getSnippet(), image, options);
    }
}
