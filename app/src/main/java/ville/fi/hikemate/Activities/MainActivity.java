package ville.fi.hikemate.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ville.fi.hikemate.Fragments.HikePlansFragment;
import ville.fi.hikemate.Fragments.HikeListFragment;
import ville.fi.hikemate.Fragments.NewHikeFragment;
import ville.fi.hikemate.Fragments.ViewPagerAdapter;
import ville.fi.hikemate.R;
import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeList;
import ville.fi.hikemate.Utils.Debug;
import ville.fi.hikemate.Utils.StorageHandler;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    Context host = this;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HikeList hikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debug.loadDebug(host);
        setupView();
    }

    public void startTracking(View v) {
        System.out.println("startTracking");
        Intent i = new Intent(host, MapActivity.class);
        startActivity(i);
    }

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

    public HikeList getHikes() {
        return hikes;
    }
}
