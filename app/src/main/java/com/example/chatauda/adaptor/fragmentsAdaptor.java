package com.example.chatauda.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatauda.freagment.callFragment;
import com.example.chatauda.freagment.chatFragment;
import com.example.chatauda.freagment.statusFragment;

public class fragmentsAdaptor extends FragmentPagerAdapter {
    public fragmentsAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {case 0: return new chatFragment();
        case 1: return new statusFragment();
        case 2: return new callFragment() ;
            default: return  new chatFragment();





        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null ;
        if( position==0)
        {
            title="chat" ;
        }
        if( position==1)
        {
            title="status" ;
        }
        if( position==2) {
            title = "call";
        }


        return title ;
    }
}
