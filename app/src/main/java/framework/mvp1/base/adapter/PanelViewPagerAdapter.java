package framework.mvp1.base.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class PanelViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> data;
    private FragmentManager fm;

    public PanelViewPagerAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        this.fm = fm;
        this.data = data;
    }

    @Override
    public Fragment getItem(int i) {
        return data.get(i);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment instantiateItem = (Fragment) super.instantiateItem(container, position);
        Fragment item = data.get(position);
        if (instantiateItem == item) {
            return instantiateItem;
        } else {
            //如果集合中对应下标的fragment和fragmentManager中的对应下标的fragment对象不一致，那么就是新添加的，所以自己add进入；
            // 这里为什么不直接调用super方法呢，因为fragment的mIndex搞的鬼，以后有机会再补一补。
            fm.beginTransaction().add(container.getId(), item)
                    .commitNowAllowingStateLoss();
            return item;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof Fragment){
            Fragment fragment= (Fragment) object;
            if (!fragment.isAdded() || !data.contains(object)) {
                return PagerAdapter.POSITION_NONE;
            }
        }
        return data.indexOf(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        //如果getItemPosition中的值为PagerAdapter.POSITION_NONE，就执行该方法。
        if (data.contains(fragment)) {
            super.destroyItem(container, position, object);
            return ;
        }
        //自己执行移除。因为mFragments在删除的时候就把某个fragment对象移除了，所以一般都得自己移除在fragmentManager中的该对象。
        fm.beginTransaction().remove(fragment).commitNowAllowingStateLoss();
    }
}