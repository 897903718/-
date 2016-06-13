package chanlytech.com.laborsupervision.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import chanlytech.com.laborsupervision.entiy.ColumnEntity;

/**
 * Created by Lyy on 2015/11/9.
 */
public class MyPageAdapter extends PagerAdapter {
    private List<Fragment> fragments;
    private List<ColumnEntity>columnEntities;
    private FragmentManager fragmentManager;
    public MyPageAdapter(List<Fragment> fragments,List<ColumnEntity>columnEntities,FragmentManager fragmentManager){
        this.fragments=fragments;
        this.columnEntities=columnEntities;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return columnEntities.get(position).getName();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);// 取得位置，获取出来Fragment
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            fragmentManager.executePendingTransactions();
        }
        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView()); // 为viewpager增加布局
        }
        return fragment.getView();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(fragments.get(position).getView());
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
