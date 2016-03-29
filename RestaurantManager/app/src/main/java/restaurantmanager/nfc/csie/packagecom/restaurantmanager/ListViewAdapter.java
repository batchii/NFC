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
        return this.values.get(0)[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listcolumns, null);
        }

        TextView nameTV = (TextView) v.findViewById(R.id.name);
        TextView partySizeTV = (TextView) v.findViewById(R.id.partysize);
        for (int i = 0; i < values.size(); i++) {
            nameTV.setText(values.get(i)[0]);
            partySizeTV.setText(values.get(i)[1]);

            Button buttonID = (Button) v.findViewById(R.id.button);

            buttonID.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Toast.makeText(arg0.getContext(), "Button has been pressed", Toast.LENGTH_LONG).show();
                    //Text user on phone number here

                }
            });
        }

        return v;
    }

}
