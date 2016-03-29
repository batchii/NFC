package restaurantmanager.nfc.csie.packagecom.restaurantmanager;

/**
 * Created by katie on 3/29/16.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends ArrayAdapter {

    private String[] values;
    Activity activity;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    Context c; //for debugging

    //need Button

    public ListViewAdapter(Context context, int textViewResourceId, String[] firstrow) {
        super(context, textViewResourceId);
        this.c = context; //for debugging
        this.values = firstrow;
        Toast.makeText(this.c, "In ListViewAdapter constructor", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getCount() {
        return this.values.length;
    }

    @Override
    public Object getItem(int position) {
        return this.values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Toast.makeText(this.c, "In ListViewAdapter getView", Toast.LENGTH_LONG).show();
        LayoutInflater inflater= (LayoutInflater) this.c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        txt1 = (TextView) convertView.findViewById(R.id.name);
//        TextView txt2;
//        TextView txt3;

        Toast.makeText(this.c, values[0], Toast.LENGTH_LONG).show();

        View rowView = inflater.inflate(R.layout.listcolumns, parent, false);
        TextView nameTV = (TextView) rowView.findViewById(R.id.name);
        TextView partySizeTV = (TextView) rowView.findViewById(R.id.partysize);
        nameTV.setText(values[0]);
        partySizeTV.setText(values[1]);
        Button buttonID = (Button) rowView.findViewById(R.id.button);

        buttonID.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {

            }});

        return rowView;
    }

}
