package ville.fi.hikemate;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

import ville.fi.hikemate.Fragments.HikePlansFragment;
import ville.fi.hikemate.Fragments.HikeListFragment;
import ville.fi.hikemate.Fragments.NewHikeFragment;
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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



        Hike hike = new Hike("Moi");
        hike.addLocation(1.23, 2.22);
        hike.addLocation(3.02, 1.67);
        hike.addLocation(7.88, 2.44);
        hike.addLocation(9.01, 10.23);
        hike.addLocation(-12.334, 15.2345);

        Hike hikee = new Hike("Hei");
        hikee.addLocation(1.56, 23.1);
        hikee.addLocation(2.02, 31.67);
        hikee.addLocation(87.88, 25.44);
        hikee.addLocation(11.01, 210.23);
        hikee.addLocation(-133.334, 150.2345);

        HikeList hikes = new HikeList();
        hikes.add(hike);
        hikes.add(hikee);

        ObjectMapper mapper = new ObjectMapper();
        StorageHandler sh = new StorageHandler();

        HikeList higes = sh.readStorage(host);
        System.out.println("Name: " + higes.get(0).getName());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewHikeFragment(), "New Hike");
        adapter.addFragment(new HikeListFragment(), "Your Hikes");
        adapter.addFragment(new HikePlansFragment(), "Hike Plans");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
