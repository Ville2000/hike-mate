package ville.fi.hikemate.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPagerAdapter is an adapter for the main activity's tab view.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * List of tab view's fragments.
     */
    private final List<Fragment> mFragmentList = new ArrayList<>();

    /**
     * List of tabs' titles.
     */
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Adapter's constructor.
     *
     * @param manager   manager for the adapter
     */
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    /**
     * Returns a fragment in the specific position.
     *
     * @param position  position of the fragment
     * @return          fragment of the specific position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Returns the amount of fragments.
     *
     * @return  fragment list's size
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * Returns the title of the fragment.
     *
     * @param position  position of the fragment
     * @return          title of the fragment
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
