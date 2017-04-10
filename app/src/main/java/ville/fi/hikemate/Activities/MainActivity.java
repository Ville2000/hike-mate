package ville.fi.hikemate.Activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import ville.fi.hikemate.Fragments.HikePlansFragment;
import ville.fi.hikemate.Fragments.HikeListFragment;
import ville.fi.hikemate.Fragments.NewHikeFragment;
import ville.fi.hikemate.Fragments.ViewPagerAdapter;
import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeList;
import ville.fi.hikemate.Utils.StorageHandler;

public class MainActivity extends AppCompatActivity {

    Context host = this;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();


        ObjectMapper mapper = new ObjectMapper();
        StorageHandler sh = new StorageHandler();

        HikeList hikes = sh.readStorage(host);
        System.out.println("Name: " + hikes.get(0).getName());
    }

    public void startTracking(View v) {
        System.out.println("startTracking");
    }

    public void stopTracking(View v) {
        System.out.println("stopTracking");
    }

    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
