package ville.fi.hikemate.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import ville.fi.hikemate.Fragments.HikePlansFragment;
import ville.fi.hikemate.Fragments.HikeListFragment;
import ville.fi.hikemate.Fragments.NewHikeFragment;
import ville.fi.hikemate.Fragments.ViewPagerAdapter;
import ville.fi.hikemate.R;
import ville.fi.hikemate.Utils.Debug;

/**
 * MainActivity is the main view of the app.
 *
 * MainActivity consists of an action bar and a tab layout. Tab views are
 * fragments and they are added to a view pager adapter. Each fragment has
 * it's own class. User can also start tracking a new hike. This takes the
 * user to a new activity.
 *
 * @author  Ville Haapavaara
 * @version 10.5.2017
 * @since   1.8
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Context of the class.
     */
    Context host = this;

    /**
     * Toolbar of the activity.
     */
    private Toolbar toolbar;

    /**
     * Tab layout of the activity.
     */
    private TabLayout tabLayout;

    /**
     * Pager for the tab layout.
     */
    private ViewPager viewPager;

    /**
     * Sets up the activity.
     *
     * Makes the initial load of the debug class and setups the tab view.
     *
     * @param savedInstanceState    bundle for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debug.loadDebug(host);
        setupView();
    }

    /**
     * Starts the map activity for user location tracking.
     *
     * @param v     view of the activity
     */
    public void startTracking(View v) {
        System.out.println("startTracking");
        Intent i = new Intent(host, MapActivity.class);
        startActivity(i);
    }

    /**
     * Sets up the tab view.
     */
    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewHikeFragment(), "New Hike");
        adapter.addFragment(new HikeListFragment(), "Your Hikes");
        adapter.addFragment(new HikePlansFragment(), "Hike Plans");
        viewPager.setAdapter(adapter);
    }
}
