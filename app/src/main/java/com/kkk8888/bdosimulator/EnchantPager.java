package com.kkk8888.bdosimulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


/**
 * Created by alfo06-18 on 2017-07-24.
 */

public class EnchantPager extends FragmentPagerAdapter {


    Fragment[] fragments = new Fragment[3];
    String[] title = new String[]{"사용 안내", "기어 1", "기어 2"};


    public EnchantPager(FragmentManager fm, String classType) {
        super(fm);

        Bundle bundle = new Bundle();
        bundle.putString("classType", classType); // putString(String key, String value)



        fragments[0] = new EnchantTip();
        fragments[0].setArguments(bundle);
        fragments[1] = new EnchantFirst();
        fragments[1].setArguments(bundle);
        fragments[2] = new EnchantSecond();
        fragments[2].setArguments(bundle);
//        fragments[3] = new EnchantTip();
//        fragments[3].setArguments(bundle);


    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {


        return fragments[position];
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
