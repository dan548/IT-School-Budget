package ru.itsschoolsamsung.budget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Intent intent2;
    private Intent intent3;
    private Intent intent4;
    private Intent intent5;
    private Intent intent6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
