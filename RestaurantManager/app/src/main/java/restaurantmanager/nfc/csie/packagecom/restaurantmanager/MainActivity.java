/** Let the main activity be the list of customers.
 *
 *
 */

package restaurantmanager.nfc.csie.packagecom.restaurantmanager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import android.nfc.NfcAdapter;

import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity implements LoyaltyCardReader.AccountCallback{
    public static final String TAG = "MainActivity";

    private NfcAdapter mNfcAdapter;
    private ListView listOfCustomers;
    final ArrayList<String[]> list = new ArrayList<String[]>();
    ListViewAdapter custom;

    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public LoyaltyCardReader mLoyaltyCardReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        listOfCustomers = (ListView) findViewById(R.id.listView);

        this.setupList(listOfCustomers);
        mLoyaltyCardReader = new LoyaltyCardReader(this);
        enableReaderMode();


        //Checks if NFC capabilities are there.
        //Commented out for testing purposes
        //ie. emulators can't do jack shit with NFC
//        if (mNfcAdapter == null) {
//            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//
//        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void setupList(ListView listOfCustomers) {

        LayoutInflater inflater=this.getLayoutInflater();

        View convertView=inflater.inflate(R.layout.listcolumns, null);

        TextView txtFirst =(TextView) convertView.findViewById(R.id.name);
        TextView txtSecond = (TextView) convertView.findViewById(R.id.partysize);
        //TextView txtThird=(TextView) convertView.findViewById(R.id.phonenumber);
        //Button
        //TextView txtFourth=(TextView) convertView.findViewById(R.id.button);


        String[] headers = new String[]{"Name", "Party Size", "5038041385"};
        String[] test = new String[]{"Katie", "3", "5038041385"};
        list.add(headers);
        list.add(test);
        list.add(setData("Alec", "2", "9086562784"));
        //Get all information in String[]
        //each entry in the list is a String[]

        //CALL setData() here
//        final ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < list.size(); ++i) { //CHANGE THIS FOR LOOP AFTER I GET ALL THE NFC DATA!!!
//            list.add(headers);
//        }


        custom = new ListViewAdapter(this, R.layout.listcolumns, list);
        listOfCustomers.setAdapter(custom);
//        Toast.makeText(this, "In Main setupList", Toast.LENGTH_LONG).show();
        listOfCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                int pos=position+1;
                Toast.makeText(MainActivity.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
            }

        });

    }


    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
    }

    private void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mLoyaltyCardReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        Log.d(TAG, "Disabling reader mode");
        Activity activity = this;
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
        }
    }

    //method to read NFC data

    //method to parse NFC data
    @Override
    public void onAccountReceived(final String account) {
        // This callback is run on a background thread, but updates to UI elements must be performed
        // on the UI thread.
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("account: ", account);
                Scanner sc = new Scanner(account);
                String first = sc.next();
                String second = sc.next();
                String third = sc.next();
                String[] toAdd = setData(first,second, third);
                list.add(toAdd);
                custom.notifyDataSetChanged();
            }
        });
    }

    //insert this string data into List HashMaps whatever,
    //then into a greater bigger list
    //which is sent into the adapter
    //and is dynamically-ish filled

    //everytime onAccountReceived is called

    //phone number connected to button press
        //deletes that item




    //method to insert the data into the listview

    private String[] setData(String name, String partySize, String phoneNumber){
//        return new String[]{"Katie", "3"};
        return new String[]{name, partySize, phoneNumber};
    }

    //

}
