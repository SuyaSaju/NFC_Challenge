package com.topcoder.poseidon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import com.topcoder.poseidon.card.LoyaltyCardService;
import com.topcoder.poseidon.databinding.ActivityMainBinding;
import com.topcoder.poseidon.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String TAG = "NFC:Card:Activity";


    /** Messenger for communicating with the service. */
    Messenger mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            setCurrentTab(SectionsPagerAdapter.Companion.getSCANNER_TAB_INDEX());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent;
        intent = new Intent(this, LoyaltyCardService.class);
        bindService(intent, mServerConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * Set the current tab
     * @param tabIndex the tab index, refer to {@link SectionsPagerAdapter}
     */
    public void setCurrentTab(int tabIndex) {
        binding.viewPager.setCurrentItem(tabIndex);
    }

    protected ServiceConnection mServerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "onServiceConnected");
            mService = new Messenger(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };
}