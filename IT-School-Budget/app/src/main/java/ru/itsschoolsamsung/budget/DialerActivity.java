package ru.itsschoolsamsung.budget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DialerActivity extends Activity {

    private EditText phoneEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        phoneEdit = (EditText) findViewById(R.id.phoneEdit);
        phoneEdit.setText("+7");

    }

    public void callClick(View v) {
        if(phoneEdit.getText().toString().length() < 19 && phoneEdit.getText().toString().length() > 4) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneEdit.getText().toString())));
        } else {
            Toast.makeText(this, "Неправильно введён номер!", Toast.LENGTH_LONG).show();
        }
    }

    public void dialerAddClick(View v) {
        Intent addIntent = new Intent(this, AddEditActivity.class);
        addIntent.putExtra("key1", true);
        addIntent.putExtra("phone", phoneEdit.getText().toString());
        startActivity(addIntent);
        finish();
    }

    public void dialerBackClick(View v) {
        finish();
    }

}
