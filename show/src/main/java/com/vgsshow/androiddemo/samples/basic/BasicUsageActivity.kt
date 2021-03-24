package com.vgsshow.androiddemo.samples.basic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.verygoodsecurity.vgsshow.VGSShow
import com.verygoodsecurity.vgsshow.core.listener.VGSOnResponseListener
import com.verygoodsecurity.vgsshow.core.network.client.VGSHttpMethod
import com.verygoodsecurity.vgsshow.core.network.model.VGSRequest
import com.verygoodsecurity.vgsshow.core.network.model.VGSResponse
import com.verygoodsecurity.vgsshow.widget.VGSTextView
import com.vgsshow.androiddemo.BuildConfig
import com.vgsshow.androiddemo.R
import kotlinx.android.synthetic.main.activity_basic_usage.*

class BasicUsageActivity : AppCompatActivity() {

    private val vgsShow = VGSShow.Builder(this, BuildConfig.VAULT_ID).build()
    private val cardAlias = "tok_sandbox_byZu39KQcVt87GKNLXbmAX"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_usage)

        // Setup view
        val tvCardNumber = VGSTextView(this)
        tvCardNumber.setContentPath("json.payment_card_number")
        tvCardNumber.setHint("Fetching card number...")
        tvCardNumber.setHintTextColor(ContextCompat.getColor(this, android.R.color.black))
        rootView.addView(tvCardNumber)

        // Subscribe view
        vgsShow.subscribe(tvCardNumber)

        // Handle response
        vgsShow.addOnResponseListener(object : VGSOnResponseListener {

            override fun onResponse(response: VGSResponse) {
                Log.d(this@BasicUsageActivity::class.java.simpleName, response.toString())
            }
        })

        // Make request
        vgsShow.requestAsync(VGSRequest.Builder("/post", VGSHttpMethod.POST)
            .body(mapOf(
                "payment_card_number" to cardAlias
            ))
            .build())
    }
}