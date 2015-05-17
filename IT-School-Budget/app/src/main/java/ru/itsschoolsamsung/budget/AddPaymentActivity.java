package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;

public class AddPaymentActivity extends Activity{

    private TextView payment_date;
    private EditText etsum;

    private static Calendar c = Calendar.getInstance();
    private String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        payment_date = (TextView)findViewById(R.id.payment_date_yo);
        etsum = (EditText)findViewById(R.id.et2);
        etsum.setText("100");


        if(c.get(Calendar.MONTH) < 9){
            date = String.valueOf(c.get(Calendar.DATE)) + ".0" + String.valueOf(c.get(Calendar.MONTH) + 1) + "." + String.valueOf(c.get(Calendar.YEAR));
        } else {
            date = String.valueOf(c.get(Calendar.DATE)) + "." + String.valueOf(c.get(Calendar.MONTH) + 1) + "." + String.valueOf(c.get(Calendar.YEAR));
        }

        etsum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                textView.setText(etsum.getText().toString());
                return true;
            }
        });


        payment_date.setText(date);

        payment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new DatePickerPayment();
                dateDialog.show(getFragmentManager(), "datePicker");
            }
        });



    }



    public void onInsertUpdatePayment (View v) {
        PeopleListActivity.addPayment(payment_date.getText().toString(),
                                      Integer.parseInt(etsum.getText().toString()));
        finish();
    }


}
