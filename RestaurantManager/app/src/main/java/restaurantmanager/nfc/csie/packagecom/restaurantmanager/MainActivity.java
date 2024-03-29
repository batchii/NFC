/** Let the main activity be the list of customers.
 *
 *
 */

package restaurantmanager.nfc.csie.packagecom.restaurantmanager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import java.util.List;
import java.util.Scanner;

import restaurantmanager.nfc.csie.packagecom.restaurantmanager.database.Customer;
import restaurantmanager.nfc.csie.packagecom.restaurantmanager.database.DatabaseHandler;


public class MainActivity extends AppCompatActivity implements LoyaltyCardReader.AccountCallback{
    public static final String TAG = "MainActivity";

    private NfcAdapter mNfcAdapter;
    private ListView listOfCustomers;
    final ArrayList<String[]> list = new ArrayList<String[]>();
    ListViewAdapter custom;

    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public LoyaltyCardReader mLoyaltyCardReader;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    Toolbar toolbar;
    DatabaseHandler db;

    private String extrapromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();
        // set mDrawerLayout and mActivityTitle in onCreate()
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }
        setupDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        listOfCustomers = (ListView) findViewById(R.id.listView);

        db = new DatabaseHandler(this);

        this.setupList(listOfCustomers);

//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        listOfCustomers = (ListView) findViewById(R.id.listView);


/**
 * CRUD Operations
 * */
        List<Customer> customerList = db.getAllCustomers();
        for(Customer c : customerList){
            db.deleteCustomer(c);
        }
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.deleteCustomer(new Customer("Ravi", "9100000000"));
        db.addCustomer(new Customer("Srinivas", "9199999999"));
        db.addCustomer(new Customer("Tommy", "9522222222"));
        db.addCustomer(new Customer("Karthik", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Customer> contacts = db.getAllCustomers();

        for (Customer cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
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

//        this.setupList(listOfCustomers);
        mLoyaltyCardReader = new LoyaltyCardReader(this);
        enableReaderMode();

    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, "clicking the toolbar!", Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }

    private void addDrawerItems() {
        String[] osArray = { "Customer List", "Promo Message", "Customer Info"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:
                        break;
                    case 1:
                        Intent case1 = new Intent(getBaseContext(), PromotionalActivity.class);
                        startActivityForResult(case1, 1);
                        break;
                    case 2:
                        Intent case2 = new Intent(getBaseContext(), DatabaseViewerActivity.class);
                        startActivity(case2);
                        break;
                    default:
                        Log.d("FUCK", "hi");
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //result code equals 0, should equal -1. data is being passed correctly, could be emulator issue.
        if ( resultCode == RESULT_OK ) {
            extrapromo = data.getStringExtra("PROMO");
//            System.out.println("EXTRA PROMO HERE " + extrapromo);
            custom.setMessage(extrapromo);
//            System.out.println("EXTRA PROMO HERE " + extrapromo);
        } else {
            custom.setMessage("");
        }
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

        //Add stuff from Database here
        List<Customer> contacts = db.getAllCustomers();
        for (Customer cn : contacts) {
            list.add(setData(cn.getName(), "0", cn.getPhoneNumber()));
        }

        custom = new ListViewAdapter(this, R.layout.listcolumns, list);
        listOfCustomers.setAdapter(custom);
//        Toast.makeText(this, "In Main setupList", Toast.LENGTH_LONG).show();
        listOfCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                int pos = position + 1;
                //Toast.makeText(MainActivity.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();

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
                addCustomerToList(toAdd);
            }
        });
    }

    public void addCustomerToList(String[] toAdd){
        list.add(toAdd);
        custom.notifyDataSetChanged();
        if(db.getCustomerByPhoneNumber(toAdd[2]) == null){
            db.addCustomer(new Customer(toAdd[0], toAdd[2]));
        } else {
            db.increaseCustomerVisits(toAdd[2]);
        }
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
