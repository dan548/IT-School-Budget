package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class AddSpentActivity extends Activity {
    private boolean flag;
    private EditText spent_date;
    private EditText product;
    private EditText sum;
    private String date;
    private static Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spent);

        Bundle extras = getIntent().getExtras();
        spent_date = (EditText)findViewById(R.id.spent_date);
        product = (EditText)findViewById(R.id.spent_product);
        sum = (EditText)findViewById(R.id.spent_sum);
        date = String.valueOf(c.get(Calendar.DATE)) + "." + String.valueOf(c.get(Calendar.MONTH) + 1) + "." + String.valueOf(c.get(Calendar.YEAR));




        flag = extras.getBoolean("key1");
        if(flag==false){
            spent_date.setText(extras.getString("key5"));
            product.setText(extras.getString("key2"));
            sum.setText(String.valueOf(extras.getInt("key6")));

        }
        else{
            spent_date.setText(date);
            product.setText(null);
            sum.setText(null);

        }





    }
    public void onInsertUpdate(View v){
        if(flag==true)
            SpentListActivity.addSpent(spent_date.getText().toString(), product.getText().toString(), Integer.parseInt(sum.getText().toString()));
        else
            SpentListActivity.editSpent(spent_date.getText().toString(), product.getText().toString(), Integer.parseInt(sum.getText().toString()));
        finish();
    }

    public void btn_back(View v){
        finish();
    }

}