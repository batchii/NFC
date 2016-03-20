package com.csie.nfc.demo1;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.telephony.SmsManager;
import android.widget.TextView;
import org.w3c.dom.Text;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class CardActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView phoneTextView;
    private String nameString, phoneString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // ImageView that we'll use to display cards
        nameTextView = (TextView)findViewById(R.id.nameCard);
        phoneTextView = (TextView)findViewById(R.id.phoneNumberCard);

        // see if app was started from a tag and show game console
        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) {
            Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            Log.d("Messages" , msg.getRecords().toString());
            NdefRecord nameRecord = msg.getRecords()[0];
            NdefRecord phoneRecord = msg.getRecords()[2];
            nameString = new String(nameRecord.getPayload());
            phoneString = new String(phoneRecord.getPayload());
            displayDetails();
        }

        Button sendBtn;
        sendBtn = (Button) findViewById(R.id.button);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    private void displayDetails() {
        if(phoneString != null && nameString != null){
            nameTextView.setText(nameString);
            phoneTextView.setText(phoneString);
        } else {
            Log.e("Something misread", "Misread");
        }
    }

    private void sendSMS() {

        String phoneNo = phoneString;
        String message = nameString + ": Your table is ready!";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}