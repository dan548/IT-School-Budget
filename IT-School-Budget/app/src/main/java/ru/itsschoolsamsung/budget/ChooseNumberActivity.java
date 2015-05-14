package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseNumberActivity extends Activity {

    private String phone1;
    private String phone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_number);

        final Button BTN_NUMBER_1 = (Button) findViewById(R.id.button_number_1);
        final Button BTN_NUMBER_2 = (Button) findViewById(R.id.button_number_2);

        Bundle extras = getIntent().getExtras();

        phone1 = extras.getString("phone1");
        phone2 = extras.getString("phone2");


        BTN_NUMBER_1.setText(phone1);
        BTN_NUMBER_2.setText(phone2);

        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.button_number_1:
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone1)));
                        break;
                    case R.id.button_number_2:
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone2)));
                        break;
                }
            }
        };

        BTN_NUMBER_1.setOnClickListener(btnClick);
        BTN_NUMBER_2.setOnClickListener(btnClick);
    }
}
