package com.example.myapplication1;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles = new String[] {"NUMBERS","COLORS","FAMILY", "PHRASES"};

    public SimpleFragmentPageAdapter(FragmentManager fm){super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);}


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

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
