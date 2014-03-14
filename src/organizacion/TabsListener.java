package organizacion;

import com.example.android.infocongreso.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

class TabsListener implements ActionBar.TabListener {

	private Fragment fragment;

	public TabsListener(Fragment fg) {
		this.fragment = fg;
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		arg1.replace(R.id.layout_organizacion, fragment);
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		arg1.remove(fragment);

	}

}