package restaurantmanager.nfc.csie.packagecom.restaurantmanager.database;

/**
 * Created by atab7 on 4/21/2016.
 */
public class Customer {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    int _no_visits;

    // Empty constructor
    public Customer(){

    }
    // constructor
    public Customer(int id, String name, String _phone_number){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._no_visits = 1;
    }

    // constructor
    public Customer (String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
        this._no_visits = 1;
    }

    public Customer (int id, String name, String _phone_number, int _no_visits){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._no_visits = _no_visits;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public int getNoVisits(){return this._no_visits;}

    public void setNoVisits(int no_visits){this._no_visits = no_visits;}
}
