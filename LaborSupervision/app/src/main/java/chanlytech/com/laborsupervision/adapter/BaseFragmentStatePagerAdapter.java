package chanlytech.com.laborsupervision.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

import chanlytech.com.laborsupervision.entiy.ColumnEntity;

/**
 * Created by Lyy on 2015/7/22.
 */
public class BaseFragmentStatePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<ColumnEntity>columnEntities;

    public BaseFragmentStatePagerAdapter(FragmentManager fm){
        super(fm);
    }
    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<ColumnEntity> columnEntities, List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
        this.columnEntities=columnEntities;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return columnEntities.get(position).getName();
    }
}
