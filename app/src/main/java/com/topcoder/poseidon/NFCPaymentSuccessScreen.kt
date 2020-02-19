package com.topcoder.poseidon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nfcpayment_success_screen.*
import java.lang.StringBuilder

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class NFCPaymentSuccessScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nfcpayment_success_screen)
        intent?.let {
            it.getStringExtra("amount")?.let {
                tvAmount.text = StringBuilder("£ ").append(it).toString()
            }
        }
    }

    companion object {
        fun launch(context: Context, amount: String) {
            val successIntent = Intent(context, NFCPaymentSuccessScreen::class.java)
            successIntent.putExtra("amount", amount)
            successIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(successIntent)
        }
    }
}
