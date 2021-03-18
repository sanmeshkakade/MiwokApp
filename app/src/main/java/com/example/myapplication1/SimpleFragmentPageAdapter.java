package com.example.myapplication1;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPageAdapter(FragmentManager fm){super(fm);}


    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new NumbersFragment();
        }else if (position == 1){
            return new ColorsFragment();
        }else if (position == 2){
            return new FamilyMembersFragment();
        }else{
            return new PhrasesFragment();
        }


    }
    @Override
    public int getCount() {
        return 4;
    }
}
