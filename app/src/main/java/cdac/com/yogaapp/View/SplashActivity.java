package cdac.com.yogaapp.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cdac.com.yogaapp.R;
import cdac.com.yogaapp.helper.PrefUtils;

public class SplashActivity extends AppCompatActivity {
    private TextView textViewTitleName, textViewHeaderName, textViewFooterName;
    PrefUtils pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textViewFooterName  = (TextView) findViewById(R.id.textViewFooterName);
        textViewHeaderName  = (TextView) findViewById(R.id.textViewHeaderName);
        textViewTitleName  = (TextView) findViewById(R.id.textViewTitleName);

        pref = new PrefUtils(this);
        if (pref.getLanguage().equals("Hindi")){
            textViewFooterName.setText("प्राणायाम, आसन, उन्नत आसन, व्यायाम, योग, योग मुद्रा");
            textViewHeaderName.setText("समस्त योग विधि और लाभ ");
            textViewTitleName.setText("योग शक्ति");
        }
        else{
            textViewFooterName.setText("Pranayam, Aasan, Yoga, Yoga Mudra");
            textViewHeaderName.setText("All Yoga Position and Benefits");
            textViewTitleName.setText(R.string.app_title);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                finish();

            }
        }, 4000);

    }

}