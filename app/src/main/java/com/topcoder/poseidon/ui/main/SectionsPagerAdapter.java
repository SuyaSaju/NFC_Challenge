package com.topcoder.poseidon.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.topcoder.poseidon.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final int HOME_TAB_INDEX = 0;
    public static final int SCANNER_TAB_INDEX = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.tab_text_home, R.string.tab_text_scanner, R.string.tab_tap_and_scanner};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        if (position == 1) {
            return ScannerFragment.newInstance();
        } else if (position == 2) {
            return NFCPaymentFragment.newInstance();
        }
        return CardDetailsFragment.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}