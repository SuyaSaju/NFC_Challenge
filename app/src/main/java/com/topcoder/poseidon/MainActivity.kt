package com.topcoder.poseidon

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Messenger
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import com.topcoder.poseidon.card.LoyaltyCardService
import com.topcoder.poseidon.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    internal var mService: Messenger? = null

    private var mServerConn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            Log.d(TAG, "onServiceConnected")
            mService = Messenger(binder)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

    public override fun onStart() {
        super.onStart()
        val intent = Intent(this, LoyaltyCardService::class.java)
        bindService(intent, mServerConn, Context.BIND_AUTO_CREATE)
    }

    /**
     * Set the current tab
     * @param tabIndex the tab index, refer to [SectionsPagerAdapter]
     */
    fun setCurrentTab(tabIndex: Int) {
        view_pager.currentItem = tabIndex
    }

    companion object {
        const val TAG = "NFC:Card:Activity"
    }
}