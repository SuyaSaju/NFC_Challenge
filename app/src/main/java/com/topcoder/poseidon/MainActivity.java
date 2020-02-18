package com.topcoder.poseidon;

import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import com.topcoder.poseidon.databinding.ActivityMainBinding;
import com.topcoder.poseidon.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            setCurrentTab(SectionsPagerAdapter.SCANNER_TAB_INDEX);
        }
    }

    /**
     * Set the current tab
     * @param tabIndex the tab index, refer to {@link SectionsPagerAdapter}
     */
    public void setCurrentTab(int tabIndex) {
        binding.viewPager.setCurrentItem(tabIndex);
    }

}