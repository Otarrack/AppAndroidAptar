package com.florian.projet.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.florian.projet.R;
import com.florian.projet.view.fragment.MachineMCUFragment;
import com.florian.projet.view.fragment.MachineOMEFragment;

public class MachinePagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public MachinePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MachineMCUFragment();
            case 1:
                return new MachineOMEFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.machine_pager_mcu);
            case 1:
                return mContext.getString(R.string.machine_pager_ome);
            default:
                return null;
        }
    }
}
