package cdac.com.yogaapp.View;

import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING_URL_HINDI;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cdac.com.yogaapp.R;
import cdac.com.yogaapp.database.MySqliteOpenHelper;
import cdac.com.yogaapp.database.VideoStatusTable;
import cdac.com.yogaapp.helper.PrefUtils;

public class LanguageSelectActivity extends AppCompatActivity {

    private TextView textSelectLanguage;
    private FloatingActionButton fabNext, fabLightNext;
    private static int itemSelected = 0;
    PrefUtils prefutils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        setTitle("Select Language");
        prefutils = new PrefUtils(LanguageSelectActivity.this);
        textSelectLanguage = (TextView) findViewById(R.id.text_select_language);
        fabNext = (FloatingActionButton) findViewById(R.id.fabNext);
        fabLightNext = (FloatingActionButton) findViewById(R.id.fabLightNext);

        if (prefutils.getLanguage() != null) {
            startActivity(new Intent(LanguageSelectActivity.this, SplashActivity.class));
            finish();
        }
        textSelectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array);
                new AlertDialog.Builder(LanguageSelectActivity.this)
                        .setTitle("Select Language")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, null)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                itemSelected = selectedPosition;
                                switch (selectedPosition) {
                                    case 0:
                                        textSelectLanguage.setText("Hindi");
                                        prefutils.setLanguage("Hindi");
                                        fabLightNext.setVisibility(View.GONE);
                                        fabNext.setVisibility(View.VISIBLE);
                                        String videoUrlHindi[] = {ABOUT_YOGA_DAY_URL_HINDI, ABOUT_YOGA_DAY_URL_HINDI, YOGA_POWER_STANDING_URL_HINDI, YOGA_POWER_SEATING_URL_HINDI, YOGA_POWER_LYING_URL_HINDI, YOGA_POWER_MEDITATION_URL_HINDI};
                                        String videoNameHindi[] = {ABOUT_YOGA_DAY_HINDI, ABOUT_YOGA_HINDI, YOGA_POWER_STANDING_HINDI, YOGA_POWER_SEATING_HINDI, YOGA_POWER_LYING_HINDI, YOGA_POWER_MEDITATION_HINDI};
                                        for (int i = 0; i < videoNameHindi.length; i++) {
                                            MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(LanguageSelectActivity.this);
                                            SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                                            VideoStatusTable.insert(db, videoNameHindi[i], videoUrlHindi[i], "No", "Hindi");
                                            db.close();
                                        }

                                        break;
                                    case 1:
                                        prefutils.setLanguage("English");
                                        textSelectLanguage.setText("English");
                                        fabLightNext.setVisibility(View.GONE);
                                        fabNext.setVisibility(View.VISIBLE);
                                        String videoUrlEnglish[] = {ABOUT_YOGA_DAY_URL, ABOUT_YOGA_DAY_URL, YOGA_POWER_STANDING_URL, YOGA_POWER_SEATING_URL, YOGA_POWER_LYING_URL, YOGA_POWER_MEDITATION_URL};
                                        String videoNameEnglish[] = {ABOUT_YOGA_DAY, ABOUT_YOGA, YOGA_POWER_STANDING, YOGA_POWER_SEATING, YOGA_POWER_LYING, YOGA_POWER_MEDITATION};
                                        for (int i = 0; i < videoNameEnglish.length; i++) {
                                            MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(LanguageSelectActivity.this);
                                            SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                                            VideoStatusTable.insert(db, videoNameEnglish[i], videoUrlEnglish[i], "No", "English");
                                            db.close();
                                        }

                                        break;
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LanguageSelectActivity.this, SplashActivity.class));
                finish();
            }
        });
    }
}
