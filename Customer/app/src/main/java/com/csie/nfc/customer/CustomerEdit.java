package com.csie.nfc.customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.csie.nfc.customer.common.logger.Log;
import com.csie.nfc.customer.common.logger.LogFragment;
import com.csie.nfc.customer.common.logger.LogWrapper;
import com.csie.nfc.customer.common.logger.MessageOnlyLogFilter;


public class CustomerEdit extends AppCompatActivity {

    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);
    }
    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }
    /** Create a chain of targets that will receive log data */
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        //LogFragment logFragment = (LogFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.log_fragment);
        //msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }
    private class AccountUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String account = s.toString();
           // AccountStorage.SetAccount(this, account);
        }
    }
}