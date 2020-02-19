package com.topcoder.poseidon.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.topcoder.poseidon.R

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
@SuppressLint("WrongConstant")
class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        if (position == 1) {
            return ScannerFragment.newInstance()
        } else if (position == 2) {
            return NFCPaymentFragment.newInstance()
        }
        return CardDetailsFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    companion object {

        val HOME_TAB_INDEX = 0
        val SCANNER_TAB_INDEX = 2

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_home,
            R.string.tab_text_scanner,
            R.string.tab_tap_and_scanner
        )
    }
}