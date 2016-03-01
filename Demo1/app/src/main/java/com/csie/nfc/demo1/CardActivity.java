package com.csie.nfc.demo1;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.telephony.SmsManager;
import android.widget.TextView;
import org.w3c.dom.Text;

public class CardActivity extends AppCompatActivity {

    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // ImageView that we'll use to display cards
        nameTextView = (TextView)findViewById(R.id.nameCard);

        // see if app was started from a tag and show game console
        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) {
            Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            NdefRecord cardRecord = msg.getRecords()[0];
            String consoleName = new String(cardRecord.getPayload());
            displayCard(consoleName);
        }
    }

    private void displayCard(String consoleName) {
        int cardResId = 0;
        if(consoleName.equals("nes")) cardResId = R.drawable.nes;
        else if(consoleName.equals("snes")) cardResId = R.drawable.snes;
        else if(consoleName.equals("megadrive")) cardResId = R.drawable.megadrive;
        else if(consoleName.equals("mastersystem")) cardResId = R.drawable.mastersystem;
    }

    private void sendSMS(String phonenumber, String message, boolean isBinary) {
        SmsManager manager = SmsManager.getDefault();

        manager.sendTextMessage(phonenumber, null, "test Message", null, null);



    }
}
