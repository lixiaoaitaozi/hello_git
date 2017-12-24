package com.china.kuaiyou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 类名: MenuApadter
 * 注释: Viewpager的适配器
 * 时间: 2017/8/25 17:04
 */
public class MenuApadter extends FragmentPagerAdapter {

    /**
     * 数据源
     */
    private List<Fragment> fragmentList;

    public MenuApadter(FragmentManager fragmentManager,
                       List<Fragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    /**
     * 得到每个页面
     */
    @Override
    public Fragment getItem(int position) {
        return (fragmentList == null || fragmentList.size() == 0) ? null
                : fragmentList.get(position);
    }

    /**
     * 每个页面的title
     */
    /*@Override
    public CharSequence getPageTitle(int position) {
		return (titleList.size() > position) ? titleList.get(position) : "";
	}*/


    /**
     * 页面的总个数
     */
    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
}
