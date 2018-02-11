package ke.co.noel.hao.slider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by root on 10/5/17.
 */

public class SlidePagerAdapter extends FragmentPagerAdapter {

    private List<String> imageURLs;

    public SlidePagerAdapter(FragmentManager fm, List<String> imageURLs) {
        super(fm);
        this.imageURLs = imageURLs;
    }

    @Override
    public Fragment getItem(int position) {
        return SlideFragment.newInstance(imageURLs.get(position));
    }

    @Override
    public int getCount() {
        return imageURLs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}
