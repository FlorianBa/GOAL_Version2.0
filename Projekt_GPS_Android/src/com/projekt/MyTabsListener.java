package com.projekt;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import com.projekt.R;

class MyTabsListener implements ActionBar.TabListener {
    public Fragment fragment;

    public MyTabsListener(Fragment fragment) {
    this.fragment = fragment;
    }


	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
				
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		
	}

    }  