package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;

public class AddEditActivity extends Activity{

    private boolean flag;
    private EditText surname;
    private EditText name;
    private EditText lastname;
    private EditText phone1;
    private EditText phone2;
    private TextView payment_date;
    private TextView payment_sum;
    private int day;
    private int month;
    private int year;
    private String payment_date_text;
    private String sum_payments;
    private String rubles;
    private static Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);


        Bundle extras = getIntent().getExtras();
        surname = (EditText)findViewById(R.id.surname);
        name = (EditText)findViewById(R.id.name);
        lastname = (EditText)findViewById(R.id.last_name);
        phone1 = (EditText) findViewById(R.id.phone_edit_1);
        phone2 = (EditText) findViewById(R.id.phone_edit_2);

        phone2.setVisibility(View.GONE);

        payment_date = (TextView)findViewById(R.id.textView2);
        payment_sum = (TextView)findViewById(R.id.textView3);

        payment_date_text = getResources().getString(R.string.date);
        sum_payments = getResources().getString(R.string.sum_payments);
        rubles = getResources().getString(R.string.rubles);
        day = c.get(Calendar.DATE);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);


        flag = extras.getBoolean("key1");
        if(!flag){
            surname.setText(extras.getString("key2"));
            name.setText(extras.getString("key3"));
            lastname.setText(extras.getString("key4"));
            payment_date.setText(payment_date_text+ " " + extras.getInt("key5") + "." + extras.getInt("key7") + "." + extras.getInt("key8"));
            payment_sum.setText(sum_payments + " " + String.valueOf(extras.getInt("key6")) + " " + rubles);
            phone1.setText(extras.getString("key9"));
            phone2.setText(extras.getString("key10"));
        }
        else{
            surname.setText(null);
            name.setText(null);
            lastname.setText(null);
            payment_date.setText(null);
            payment_sum.setText(null);
            phone1.setText(extras.getString("phone"));
            phone2.setText("+7");
        }

    }
    public void onInsertUpdate(View v){
        if (phone1.getText().toString().length() < 19 && phone1.getText().toString().length() > 4 && !surname.getText().toString().equals("") && !name.getText().toString().equals("")) {
            if (flag)
                PeopleListActivity.addArrayContact(surname.getText().toString(),
                        name.getText().toString(),
                        lastname.getText().toString(),
                        day, month, year, 0, phone1.getText().toString(),
                        phone2.getText().toString());
            else
                PeopleListActivity.editContact(surname.getText().toString(),
                        name.getText().toString(),
                        lastname.getText().toString(),
                        phone1.getText().toString(),
                        phone2.getText().toString());
            finish();
        } else {
            Toast.makeText(this, "Вы не заполнили все обязательные поля или неправильно ввели номер телефона!", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_back(View v){
        finish();
    }

    public void addNumber(View v){
        phone2.setVisibility(View.VISIBLE);
    }

}
