package restaurantmanager.nfc.csie.packagecom.restaurantmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by katie on 4/21/16.
 */
public class PromotionalActivity extends AppCompatActivity {

    private String promotion = "";
//    private Intent me = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotional_activity);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button b = (Button) findViewById(R.id.submit);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.promotionalText);
                String txt = et.getText().toString();
//                System.out.println(txt);
//                setPromotionString(txt);
                Intent me = getIntent();
                me.putExtra("PROMO", txt);
                setResult(RESULT_OK, me);
                finish();
            }
        });



    }

//    private void setPromotionString(String txt) {
//        System.out.println(this.promotion);
//        this.promotion = txt;
//    }
//
//    public String getPromotionalString() {
//        return this.promotion;
//
//    }



}
