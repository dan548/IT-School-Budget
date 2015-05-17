package ru.itsschoolsamsung.budget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity implements Runnable{

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView = (TextView)findViewById(R.id.textView);
        new Thread(this).start();

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/video94817050_171360698"));
                startActivity(browseIntent);
                return false;
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            runOnUiThread(new Runnable() {
                public void run() {
                    textView.scrollBy(0, 1);
                    int heightText = textView.getLineCount() * textView.getLineHeight();
                    if (textView.getScrollY() > heightText)
                        textView.scrollTo(0, 0);
                }
            });
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

}
