package ville.fi.hikemate.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.net.URL;

import ville.fi.hikemate.R;

/**
 * Created by Ville on 9.5.2017.
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context host;
    private View mInfoWindow;
    private View mInfoContents;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration configuration;
    private DisplayImageOptions options;

    public MarkerInfoWindowAdapter(FragmentActivity host) {
        this.host = host;
        mInfoWindow = host.getLayoutInflater().inflate(R.layout.marker_info_window, null);
        mInfoContents = host.getLayoutInflater().inflate(R.layout.marker_info_window_contents, null);
        configuration = new ImageLoaderConfiguration.Builder(host).build();
        ImageLoader.getInstance().init(configuration);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().build();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mInfoWindow);
        return mInfoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        render(marker, mInfoContents);
        return mInfoContents;
    }

    private void render(final Marker marker, View view) {
        Debug.toastThis(host, marker.getSnippet());
        ImageView image = (ImageView) view.findViewById(R.id.marker_photo);
        imageLoader.displayImage("file:///" + marker.getSnippet(), image, options);
    }
}
