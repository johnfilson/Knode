package com.fyp.knode.Adapter;

/**
 * Created by Johnny on 08/02/2016.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fyp.knode.Fragments.ContactsFragment;
import com.fyp.knode.Fragments.MessagerFragment;

/* A {@link FragmentPagerAdapter} that returns a fragment corresponding to
        * one of the sections/tabs/pages.
        */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    protected Context mContext;
    CharSequence mTitles[];
    int mNumberOfTabs;

    public SectionsPagerAdapter(Context context,CharSequence Titles[], int NumberOfTabs ,FragmentManager fm) {
        super(fm);
        mContext = context;
        this.mTitles = Titles;
        this.mNumberOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position)
        {
            case 0:
                return new MessagerFragment();
            case 1:
                return new ContactsFragment();
        }
        return new MessagerFragment();
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}