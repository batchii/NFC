package restaurantmanager.nfc.csie.packagecom.restaurantmanager;

/**
 * Created by katie on 3/29/16.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends ArrayAdapter {

    private ArrayList<String[]> values;
    Activity activity;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    Context c; //for debugging

    //need Button

    public ListViewAdapter(Context context, int textViewResourceId, ArrayList<String[]> firstrow) {
        super(context, textViewResourceId);
        this.c = context; //for debugging
        this.values = firstrow;
//        Toast.makeText(this.c, "In ListViewAdapter constructor", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getCount() {
        return this.values.size();
    }

    @Override
    public Object getItem(int position) {
        return this.values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listcolumns, null);
        }

        TextView nameTV = (TextView) v.findViewById(R.id.name);
        TextView partySizeTV = (TextView) v.findViewById(R.id.partysize);

        nameTV.setText(values.get(position)[0]);
        partySizeTV.setText(values.get(position)[1]);



//        Toast.makeText(this.getContext(), values.get(position)[2], Toast.LENGTH_LONG).show();

        Button buttonID = (Button) v.findViewById(R.id.button);

        buttonID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sendSMS(values.get(position)[2]);

                Toast.makeText(arg0.getContext(), "Texting " + values.get(position)[2], Toast.LENGTH_LONG).show();
                //Text user on phone number here
                //delete upon press

            }
        });

        return v;
    }

    private void sendSMS(String phoneString) {

        String phoneNo = phoneString;
        String message = "Your table is ready!";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(this.getContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(this.getContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}
