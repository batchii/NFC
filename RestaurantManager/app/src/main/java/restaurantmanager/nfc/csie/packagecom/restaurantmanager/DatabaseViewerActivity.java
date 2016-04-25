package restaurantmanager.nfc.csie.packagecom.restaurantmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import restaurantmanager.nfc.csie.packagecom.restaurantmanager.database.Customer;
import restaurantmanager.nfc.csie.packagecom.restaurantmanager.database.DatabaseHandler;

public class DatabaseViewerActivity extends AppCompatActivity {
    private ListView listOfCustomers;
    final ArrayList<String[]> list = new ArrayList<String[]>();
    ListViewAdapter custom;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);
        List<Customer> customerList = db.getAllCustomers();
        for(Customer c : customerList){
            Log.d("Customer", c.getName());
        }



        listOfCustomers = (ListView) findViewById(R.id.listView);
        setupList(listOfCustomers);
    }

    private void setupList(ListView listOfCustomers) {

        LayoutInflater inflater=this.getLayoutInflater();

        View convertView=inflater.inflate(R.layout.database_view_columns, null);

        TextView txtFirst =(TextView) convertView.findViewById(R.id.name);
        TextView txtSecond = (TextView) convertView.findViewById(R.id.partysize);
        TextView txtThird=(TextView) convertView.findViewById(R.id.numVisits);
        //Button
        //TextView txtFourth=(TextView) convertView.findViewById(R.id.button);

        List<Customer> customerList = db.getAllCustomers();

        String[] headers = new String[]{"Name", "Phone Number", "Number of Visits"};
        String[] test = new String[]{"Katie", "5038041385", "3"};
        list.add(headers);
        list.add(test);
        list.add(setData("Alec", "90865562784", "2"));
        for(Customer c : customerList){
            list.add(setData(c.getName(), c.getPhoneNumber(), c.getNoVisits() + ""));
        }
        //Get all information in String[]
        //each entry in the list is a String[]

        //CALL setData() here
//        final ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < list.size(); ++i) { //CHANGE THIS FOR LOOP AFTER I GET ALL THE NFC DATA!!!
//            list.add(headers);
//        }


        custom = new ListViewAdapter(this, R.layout.database_view_columns, list);
        listOfCustomers.setAdapter(custom);
//        Toast.makeText(this, "In Main setupList", Toast.LENGTH_LONG).show();
        listOfCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                int pos = position + 1;
                Toast.makeText(DatabaseViewerActivity.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();

            }

        });

    }
    private String[] setData(String name, String partySize, String phoneNumber){
//        return new String[]{"Katie", "3"};
        return new String[]{name, partySize, phoneNumber};
    }

}
