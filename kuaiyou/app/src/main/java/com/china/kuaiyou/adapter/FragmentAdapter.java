package com.china.kuaiyou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
	FragmentManager fm;
	List<Fragment> list;

	public FragmentAdapter(FragmentManager fm , List<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fm=fm;
		this.list=list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
}
