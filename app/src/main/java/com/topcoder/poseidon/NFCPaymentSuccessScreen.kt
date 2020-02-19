package com.topcoder.poseidon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class NFCPaymentSuccessScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nfcpayment_success_screen)
    }

    companion object {
        fun launch(context: Context) {
            val successIntent = Intent(context, NFCPaymentSuccessScreen::class.java)
            successIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(successIntent)
        }
    }
}
