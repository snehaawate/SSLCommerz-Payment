package com.example.sslpayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener

class MainActivity : AppCompatActivity() ,SSLCTransactionResponseListener{

    lateinit var button: Button;
    lateinit var editText: EditText;

    lateinit var sslCommerzInitialization: SSLCommerzInitialization;
    lateinit var additionalInitialization: SSLCAdditionalInitializer;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText =  findViewById(R.id.amountid);
        button = findViewById(R.id.payBtn);

        button.setOnClickListener {
            val amount = editText.text.toString().trim();

            if (amount.isNotEmpty()){
                sslSetUp(amount)
            }else{
                editText.error="Error"
            }

        }


    }

    private fun sslSetUp(amount: String) {
        sslCommerzInitialization = SSLCommerzInitialization(

            "Here add your text id ",
            "here add your text pass",
            amount.toDouble(),
            SSLCCurrencyType.BDT,
            "$amount",
            "eshop",
            SSLCSdkType.TESTBOX
        )

        additionalInitialization = SSLCAdditionalInitializer()
        additionalInitialization!!.valueA ="Value Option 1"
        additionalInitialization!!.valueB ="Value Option 2"
        additionalInitialization!!.valueC ="Value Option 3"
        additionalInitialization!!.valueD ="Value Option 4"

        IntegrateSSLCommerz.getInstance(this)
            .addSSLCommerzInitialization(sslCommerzInitialization)
            .addAdditionalInitializer(additionalInitialization)
            .buildApiCall(this);

    }

    override fun transactionSuccess(p0: SSLCTransactionInfoModel?) {
      Toast.makeText(applicationContext,"Payment success",Toast.LENGTH_SHORT).show();
    }

    override fun transactionFail(p0: String?) {
        Toast.makeText(applicationContext,p0.toString(),Toast.LENGTH_SHORT).show();
    }

    override fun closed(p0: String?) {
        Toast.makeText(applicationContext,p0.toString(),Toast.LENGTH_SHORT).show();
    }
}