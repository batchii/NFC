package restaurantmanager.nfc.csie.packagecom.restaurantmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atab7 on 4/21/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "customersManager";

    // Customers table name
    private static final String TABLE_CUSTOMERS = "customers";

    // Customers Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_NO_VISITS = "num_visits";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMERS_TABLE = "CREATE TABLE " + TABLE_CUSTOMERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_NO_VISITS + " INTEGER" + ")";
        db.execSQL(CREATE_CUSTOMERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("ALTER TABLE " + TABLE_CUSTOMERS + " ADD COLUMN " + KEY_NO_VISITS);
                // no break, in case user is upgrading multiple versions

        // Create tables again
        //onCreate(db);
    }
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new customer
    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getName()); // Customer Name
        values.put(KEY_PH_NO, customer.getPhoneNumber()); // Customer Phone
        values.put(KEY_NO_VISITS, 1);
        // Inserting Row
        db.insert(TABLE_CUSTOMERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single customer
    public Customer getCustomer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CUSTOMERS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Customer customer = new Customer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return customer
        return customer;
    }

    public Customer getCustomerByPhoneNumber(String pn){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + " " + KEY_NAME + " " + KEY_PH_NO + " " + KEY_NO_VISITS
                +" FROM " + DATABASE_NAME + " WHERE " + KEY_NAME + "=?", new String[] {pn} );
        Customer customer = null;

        if(cursor != null){
            cursor.moveToFirst();
            customer = new Customer(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));

        }

        return customer;
    }


    // Getting All Customers
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<Customer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setID(Integer.parseInt(cursor.getString(0)));
                customer.setName(cursor.getString(1));
                customer.setPhoneNumber(cursor.getString(2));
                // Adding customer to list
                customerList.add(customer);
            } while (cursor.moveToNext());
        }

        // return customer list
        return customerList;
    }

    // Updating single customer
    public int updateCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getName());
        values.put(KEY_PH_NO, customer.getPhoneNumber());

        // updating row
        return db.update(TABLE_CUSTOMERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(customer.getID()) });
    }

    // Deleting single customer
    public void deleteCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMERS, KEY_ID + " = ?",
                new String[] { String.valueOf(customer.getID()) });
        db.close();
    }


    // Getting customers Count
    public int getCustomersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CUSTOMERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void increaseCustomerVisits(String phone_number) {
        Customer c = getCustomerByPhoneNumber(phone_number);
        c.setNoVisits(c.getNoVisits() + 1);
        updateCustomer(c);
    }
}
