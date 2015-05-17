package ru.itsschoolsamsung.budget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Intent intent2;
    private Intent intent3;
    private Intent intent4;
    private Intent intent5;
    private Intent intent6;
    private boolean isAlertDialog;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(getString(R.string.chb1), false))
        {
            isAlertDialog = true;
        } else {
            isAlertDialog = false;
        }
/*
        // читаем размер шрифта из EditTextPreference
        float fSize = Float.parseFloat(
                prefs.getString(getString(R.string.pref_size), "20"));
        // применяем настройки в текстовом поле
        myEdit.setTextSize(fSize);

        // читаем стили текста из ListPreference
        String regular = prefs.getString(getString(R.string.pref_style), "");
        int typeface = Typeface.NORMAL;

        if (regular.contains("Полужирный"))
            typeface += Typeface.BOLD;

        if (regular.contains("Курсив"))
            typeface += Typeface.ITALIC;

        // меняем настройки в EditText
        //myEdit.setTextSize(fSize);
        myEdit.setTypeface(null, typeface);

        // читаем цвет текста из CheckBoxPreference
        // и суммируем значения для получения дополнительньк цветов текста
        int color = Color.BLACK;
        if (prefs.getBoolean(getString(R.string.pref_color_red), false))
            color += Color.RED;
        if (prefs.getBoolean(getString(R.string.pref_color_green), false))
            color += Color.GREEN;
        if (prefs.getBoolean(getString(R.string.pref_color_blue), false))
            color += Color.BLUE;

        myEdit.setTextColor(color);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);


        intent2 = new Intent(MainActivity.this, PeopleListActivity.class);
        intent3 = new Intent(MainActivity.this, SpentListActivity.class);
        intent4 = new Intent(MainActivity.this, ShowBudgetActivity.class);
        intent5 = new Intent(MainActivity.this, AboutActivity.class);
        intent6 = new Intent(MainActivity.this, DialerActivity.class);


        final Button b2 = (Button)findViewById(R.id.button2);
        final Button b3 = (Button)findViewById(R.id.button3);
        final Button b4 = (Button)findViewById(R.id.button4);
        final Button b5 = (Button)findViewById(R.id.button5);
        final Button b6 = (Button)findViewById(R.id.button6);
        final Button b_call = (Button)findViewById(R.id.button_call_from_main);




        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button2:
                        intent2.putExtra("smart_remove", isAlertDialog);
                        startActivity(intent2);
                        break;
                    case R.id.button3:
                        startActivity(intent3);
                        break;
                    case R.id.button4:
                        startActivity(intent4);
                        break;
                    case R.id.button5:
                        startActivity(intent5);
                        break;
                    case R.id.button6:
                        finish();
                        break;
                    case R.id.button_call_from_main:
                        startActivity(intent6);
                        break;
                }
            }
        };


        b2.setOnClickListener(btnClick);
        b3.setOnClickListener(btnClick);
        b4.setOnClickListener(btnClick);
        b5.setOnClickListener(btnClick);
        b6.setOnClickListener(btnClick);
        b_call.setOnClickListener(btnClick);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Prefs.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
