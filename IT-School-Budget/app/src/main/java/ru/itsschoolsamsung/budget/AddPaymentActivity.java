package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import java.util.Calendar;

public class AddPaymentActivity extends Activity{


    private static SQLiteDatabase sqLiteDatabase;
    private static DatabaseBudget databaseBudget;
    private EditText etday;
    private EditText etmonth;
    private EditText etyear;
    private EditText etsum;

    private static Calendar c = Calendar.getInstance();
    public static int day = c.get(Calendar.DATE);
    public static int month = c.get(Calendar.MONTH);
    public static int year = c.get(Calendar.YEAR);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        etday = (EditText)findViewById(R.id.et_day);
        etmonth = (EditText)findViewById(R.id.et_month);
        etyear = (EditText)findViewById(R.id.et_year);
        etsum = (EditText)findViewById(R.id.et2);

        etday.setText(Integer.toString(day));
        etmonth.setText(Integer.toString(month));
        etyear.setText(Integer.toString(year));
        etsum.setText("100");



        databaseBudget = new DatabaseBudget(this);
        sqLiteDatabase = databaseBudget.getWritableDatabase();



    }



    public void onInsertUpdatePayment (View v) {
        PeopleListActivity.addPayment(Integer.parseInt(String.valueOf(etday.getText())),
                                      Integer.parseInt(String.valueOf(etmonth.getText())),
                                      Integer.parseInt(String.valueOf(etyear.getText())),
                                      Integer.parseInt(etsum.getText().toString()));
        finish();
    }


}
