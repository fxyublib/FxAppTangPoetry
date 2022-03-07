package com.fxyublib.android.FxAppTangPoetry.base;

import android.os.Parcelable;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.List;

public class MyTabFragmentAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = "MyTabFragmentAdapter";

    private String[] titleArray;
    private List<Fragment> listFragments;

    public MyTabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        //Log.d(TAG,"listFragments.size() = " + listFragments.size());
        return listFragments == null ? 0:listFragments.size();
    }

    public void addFragment(Fragment fragment){
        this.listFragments.add(fragment);
    }

    public void setFragments(List<Fragment> fragments){
        this.listFragments = fragments;
    }

    public void setTitlesArr (String[] titlesArr) {
        this.titleArray = titlesArr;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}