package cdac.com.yogaapp.View;

import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY_DOWNLOAD_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DAY_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DOWNLOAD_DAY_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DOWNLOAD_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_DOWNLOAD_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.TEXT;
import static cdac.com.yogaapp.helper.StringValueHelper.TEXT_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_DOWNLOAD_LYING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_DOWNLOAD_MEDITATION_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_DOWNLOAD_SEATING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING_DOWNLOAD_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_LYING_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION_DOWNLOAD_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_MEDITATION_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING_DOWNLOAD_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_SEATING_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING_DOWNLOAD_URL_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING_HINDI;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING_URL;
import static cdac.com.yogaapp.helper.StringValueHelper.YOGA_POWER_STANDING_URL_HINDI;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import cdac.com.yogaapp.Presenter.DashboardPresenter;
import cdac.com.yogaapp.R;
import cdac.com.yogaapp.database.MySqliteOpenHelper;
import cdac.com.yogaapp.database.VideoStatusTable;
import cdac.com.yogaapp.helper.EncryptDecryptUtils;
import cdac.com.yogaapp.helper.FileUtils;
import cdac.com.yogaapp.helper.PrefUtils;
import cdac.com.yogaapp.helper.StringValueHelper;

public class DashboardActivity extends AppCompatActivity implements Handler.Callback{

    private TextView txt_marqueLine;
    private DashboardPresenter dashboardPresenter;
    private TextView text_language;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private static String video_name;
    private TextView yogaPower_title, textAboutYogaDay, textAboutYoga, textYogaStanding, textYogaSeating, textYogaLying, textYogaMeditation;
    private PrefUtils prefUtils;
    private FloatingActionButton fabAllVideoDownload, fabAllDownloaded;
    ArrayList<String> arrayListURL = new ArrayList<>();
    ArrayList<String> arrayListName = new ArrayList<>();
    ArrayList<String> arrayListStatus = new ArrayList<>();
    ArrayList<String> arrayListLanguage = new ArrayList<>();
    private LinearLayout linearDownload, linearOffline, linearDownloadLight, linear3;
    private boolean flag = false;
    DownloadManager downloadManager;
    long refId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txt_marqueLine = (TextView) findViewById(R.id.txt_marqueLine);
        text_language = (TextView) findViewById(R.id.text_language);
        dashboardPresenter = new DashboardPresenter(this);
        dashboardPresenter.onTextMarquee(txt_marqueLine);
        yogaPower_title = (TextView) findViewById(R.id.yogaPower_title);
        textAboutYoga = (TextView) findViewById(R.id.textaboutYoga);
        textAboutYogaDay = (TextView) findViewById(R.id.textaboutYogaday);
        textYogaLying = (TextView) findViewById(R.id.textYogaLying);
        textYogaMeditation = (TextView) findViewById(R.id.textYogaMeditation);
        textYogaSeating = (TextView) findViewById(R.id.textYogaSeating);
        textYogaStanding = (TextView) findViewById(R.id.textYogaStanding);
        fabAllVideoDownload = (FloatingActionButton) findViewById(R.id.fabAllVideoDownload);
        fabAllDownloaded = (FloatingActionButton) findViewById(R.id.fabAllDownloaded);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        prefUtils = new PrefUtils(this);
        if (prefUtils.getLanguage().equals("Hindi")) {
            txt_marqueLine.setText(TEXT_HINDI);
        } else {
            txt_marqueLine.setText(TEXT);
        }
        fabAllVideoDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAllVideoDownload();
            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefUtils.getLanguage().equals("Hindi")) {
                    addImagesToView(StringValueHelper.TEXT_HINDI);
                } else {
                    addImagesToView(StringValueHelper.TEXT);
                }

            }
        });

        fabAllDownloaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AllVideoDownloadedActivity.class);
                startActivity(intent);
            }
        });

        if (prefUtils.getLanguage().equals("Hindi")) {
            text_language.setText("HI");
            yogaPower_title.setText(R.string.app_title_hindi);
            textAboutYogaDay.setText(R.string.part_a_hindi);
            textAboutYoga.setText(R.string.part_b_hindi);
            textYogaStanding.setText(R.string.part_c_hindi);
            textYogaSeating.setText(R.string.part_d_hindi);
            textYogaLying.setText(R.string.part_e_hindi);
            textYogaMeditation.setText(R.string.part_f_hindi);
        } else {
            text_language.setText("EN");
            yogaPower_title.setText(R.string.title);
            textAboutYogaDay.setText(R.string.part_a);
            textAboutYoga.setText(R.string.part_b);
            textYogaStanding.setText(R.string.part_c);
            textYogaSeating.setText(R.string.part_d);
            textYogaLying.setText(R.string.part_e);
            textYogaMeditation.setText(R.string.part_f);
        }

    }

    public void addImagesToView(final String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_quote, null, false);
        builder.setView(v);
        TextView tv = (TextView) v.findViewById(R.id.text_name);
        tv.setText(text);
        builder.show();
    }


    public void changeLanguage(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Select Language");
        String[] language = {"Hindi", "English"};
        builder.setItems(language, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(DashboardActivity.this, "Hindi", Toast.LENGTH_SHORT).show();
                        txt_marqueLine.setText(TEXT_HINDI);
                        prefUtils.setLanguage("Hindi");
                        text_language.setText("HI");
                        yogaPower_title.setText(R.string.app_title_hindi);
                        textAboutYogaDay.setText(R.string.part_a_hindi);
                        textAboutYoga.setText(R.string.part_b_hindi);
                        textYogaStanding.setText(R.string.part_c_hindi);
                        textYogaSeating.setText(R.string.part_d_hindi);
                        textYogaLying.setText(R.string.part_e_hindi);
                        textYogaMeditation.setText(R.string.part_f_hindi);
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
                        String whereClause = "lang='" + prefUtils.getLanguage() + "'";
                        Cursor cursor = VideoStatusTable.select(db, null, whereClause, null, null, null, null, null);
                        if (cursor.getCount() == 0) {
                            String videoUrlHindi[] = {ABOUT_YOGA_DAY_URL_HINDI, ABOUT_YOGA_DAY_URL_HINDI, YOGA_POWER_STANDING_URL_HINDI, YOGA_POWER_SEATING_URL_HINDI, YOGA_POWER_LYING_URL_HINDI, YOGA_POWER_MEDITATION_URL_HINDI};
                            String videoNameHindi[] = {ABOUT_YOGA_DAY_HINDI, ABOUT_YOGA_HINDI, YOGA_POWER_STANDING_HINDI, YOGA_POWER_SEATING_HINDI, YOGA_POWER_LYING_HINDI, YOGA_POWER_MEDITATION_HINDI};
                            for (int i = 0; i < videoNameHindi.length; i++) {
                                MySqliteOpenHelper mySqliteOpenHelper1 = new MySqliteOpenHelper(DashboardActivity.this);
                                SQLiteDatabase db1 = mySqliteOpenHelper1.getWritableDatabase();
                                VideoStatusTable.insert(db1, videoNameHindi[i], videoUrlHindi[i], "No", "Hindi");
                                db1.close();
                            }
                        }
                        db.close();
                        dialog.dismiss();
                        break;
                    case 1:
                        Toast.makeText(DashboardActivity.this, "English", Toast.LENGTH_SHORT).show();
                        text_language.setText("EN");
                        txt_marqueLine.setText(TEXT);
                        prefUtils.setLanguage("English");
                        yogaPower_title.setText(R.string.title);
                        textAboutYogaDay.setText(R.string.part_a);
                        textAboutYoga.setText(R.string.part_b);
                        textYogaStanding.setText(R.string.part_c);
                        textYogaSeating.setText(R.string.part_d);
                        textYogaLying.setText(R.string.part_e);
                        textYogaMeditation.setText(R.string.part_f);
                        MySqliteOpenHelper mySqliteOpenHelper2 = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db2 = mySqliteOpenHelper2.getReadableDatabase();
                        String whereClause1 = "lang='" + prefUtils.getLanguage() + "'";
                        Cursor cursor1 = VideoStatusTable.select(db2, null, whereClause1, null, null, null, null, null);
                        if (cursor1.getCount() == 0) {
                            String videoUrlEnglish[] = {ABOUT_YOGA_DAY_URL, ABOUT_YOGA_DAY_URL, YOGA_POWER_STANDING_URL, YOGA_POWER_SEATING_URL, YOGA_POWER_LYING_URL, YOGA_POWER_MEDITATION_URL};
                            String videoNameEnglish[] = {ABOUT_YOGA_DAY, ABOUT_YOGA, YOGA_POWER_STANDING, YOGA_POWER_SEATING, YOGA_POWER_LYING, YOGA_POWER_MEDITATION};
                            for (int i = 0; i < videoNameEnglish.length; i++) {
                                MySqliteOpenHelper mySqliteOpenHelper1 = new MySqliteOpenHelper(DashboardActivity.this);
                                SQLiteDatabase db1 = mySqliteOpenHelper1.getWritableDatabase();
                                VideoStatusTable.insert(db1, videoNameEnglish[i], videoUrlEnglish[i], "No", "English");
                                db1.close();
                            }
                        }
                        db2.close();
                        dialog.dismiss();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void onAllVideoDownload() {
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = VideoStatusTable.select(db, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(3).equals("Completed") && cursor.getString(4).equals("English")) {
                if (cursor.getCount() == 6) {
                    flag = true;
                    break;
                }
            } else if (cursor.getString(3).equals("Completed") && cursor.getString(4).equals("Hindi")) {
                if (cursor.getCount() == 6) {
                    flag = true;
                    break;
                }
            }
        }

        if (flag == true) {
            Intent intent = new Intent(DashboardActivity.this, AllVideoDownloadedActivity.class);
            intent.putExtra("language", prefUtils.getLanguage());
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm");
            builder.setMessage("You are sure to download all video. Do you really want to proceed ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
                    Cursor cursor = VideoStatusTable.select(db, null, null, null, null, null, null, null);
                    arrayListURL.clear();
                    arrayListName.clear();
                    arrayListStatus.clear();
                    arrayListLanguage.clear();
                    while (cursor.moveToNext()) {
                        if (cursor.getString(3).equals(prefUtils.getLanguage())) {
                            arrayListName.add(cursor.getString(1));
                            arrayListURL.add(cursor.getString(2));
                            arrayListLanguage.add(cursor.getString(3));
                            arrayListStatus.add(cursor.getString(4));
                        }
                    }
                    if (prefUtils.getLanguage().equals("English")) {
                        allVideoDownload();
                    } else {
                        allVideoDownloadInHindi();
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });


            builder.show();
        }
    }


    public void share(View view) {
        if (prefUtils.getLanguage().equals("Hindi")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String ss = StringValueHelper.SHARE_TEXT_HINDI;
            intent.putExtra(Intent.EXTRA_TEXT, ss);
            startActivity(Intent.createChooser(intent, "शेयर करें"));
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String ss = StringValueHelper.SHARE_TEXT;
            intent.putExtra(Intent.EXTRA_TEXT, ss);
            startActivity(Intent.createChooser(intent, "Share with"));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void yoga(View view) {
        onYogaListener(view);
    }
    //the method below is used to display info abt what is to be displayed in a dialog box

    public void onYogaListener(View view) {
int viewId=view.getId();
if(viewId==R.id.linearlayoutYoga1){
    if (prefUtils.getLanguage().equals("English"))
        onShowYogaDailog(R.drawable.yoga_day, textAboutYogaDay.getText().toString(), StringValueHelper.ABOUT_YOGA_DAY_URL, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA_DAY, ABOUT_YOGA_DOWNLOAD_DAY_URL);
    else
        onShowYogaDailog(R.drawable.yoga_day, textAboutYogaDay.getText().toString(), StringValueHelper.ABOUT_YOGA_DAY_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA_DAY_HINDI, ABOUT_YOGA_DAY_DOWNLOAD_URL_HINDI);

}
else if (viewId == R.id.linearlayoutYoga2) {
    if (prefUtils.getLanguage().equals("English"))
        onShowYogaDailog(R.drawable.logo, textAboutYoga.getText().toString(), StringValueHelper.ABOUT_YOGA_URL, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA, ABOUT_YOGA_DOWNLOAD_URL);
    else
        onShowYogaDailog(R.drawable.logo, textAboutYoga.getText().toString(), StringValueHelper.ABOUT_YOGA_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA_HINDI, ABOUT_YOGA_DOWNLOAD_URL_HINDI);
} else if (viewId == R.id.linearlayoutYoga3) {
    if (prefUtils.getLanguage().equals("English"))
        onShowYogaDailog(R.drawable.standing, textYogaStanding.getText().toString(), StringValueHelper.YOGA_POWER_STANDING_URL, StringValueHelper.YOGA_POWER_PDF_URL_STANDING, YOGA_POWER_DOWNLOAD_SEATING_URL);
    else
        onShowYogaDailog(R.drawable.standing, textYogaStanding.getText().toString(), StringValueHelper.YOGA_POWER_STANDING_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_STANDING_HINDI, YOGA_POWER_STANDING_DOWNLOAD_URL_HINDI);
} else if (viewId == R.id.linearlayoutYoga4) {
    if (prefUtils.getLanguage().equals("English"))
        onShowYogaDailog(R.drawable.sitting, textYogaSeating.getText().toString(), StringValueHelper.YOGA_POWER_SEATING_URL, StringValueHelper.YOGA_POWER_PDF_URL_SEATING, YOGA_POWER_DOWNLOAD_SEATING_URL);
    else
        onShowYogaDailog(R.drawable.sitting, textYogaSeating.getText().toString(), StringValueHelper.YOGA_POWER_SEATING_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_SEATING_HINDI, YOGA_POWER_SEATING_DOWNLOAD_URL_HINDI);
} else if (viewId == R.id.linearlayoutYoga5) {
    if (prefUtils.getLanguage().equals("English"))
        onShowYogaDailog(R.drawable.lying, textYogaLying.getText().toString(), StringValueHelper.YOGA_POWER_LYING_URL, StringValueHelper.YOGA_POWER_PDF_URL_LYING, YOGA_POWER_DOWNLOAD_LYING_URL);
    else
        onShowYogaDailog(R.drawable.lying, textYogaLying.getText().toString(), StringValueHelper.YOGA_POWER_LYING_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_LYING_HINDI, YOGA_POWER_LYING_DOWNLOAD_URL_HINDI);
} else if (viewId == R.id.linearlayoutYoga6) {
    if (prefUtils.getLanguage().equals("English"))
        onShowYogaDailog(R.drawable.meditation, textYogaMeditation.getText().toString(), StringValueHelper.YOGA_POWER_MEDITATION_URL, StringValueHelper.YOGA_POWER_PDF_URL_MEDITATION, YOGA_POWER_DOWNLOAD_MEDITATION_URL);
    else
        onShowYogaDailog(R.drawable.meditation, textYogaMeditation.getText().toString(), StringValueHelper.YOGA_POWER_MEDITATION_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_MEDITATION_HINDI, YOGA_POWER_MEDITATION_DOWNLOAD_URL_HINDI);
}
/*
        switch (view.getId()) {
            case R.id.linearlayoutYoga1:
                if (prefUtils.getLanguage().equals("English"))
                    onShowYogaDailog(R.drawable.yoga_day, textAboutYogaDay.getText().toString(), StringValueHelper.ABOUT_YOGA_DAY_URL, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA_DAY, ABOUT_YOGA_DOWNLOAD_DAY_URL);
                else
                    onShowYogaDailog(R.drawable.yoga_day, textAboutYogaDay.getText().toString(), StringValueHelper.ABOUT_YOGA_DAY_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA_DAY_HINDI, ABOUT_YOGA_DAY_DOWNLOAD_URL_HINDI);
                break;
            case R.id.linearlayoutYoga2:
                if (prefUtils.getLanguage().equals("English"))
                    onShowYogaDailog(R.drawable.logo, textAboutYoga.getText().toString(), StringValueHelper.ABOUT_YOGA_URL, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA, ABOUT_YOGA_DOWNLOAD_URL);
                else
                    onShowYogaDailog(R.drawable.logo, textAboutYoga.getText().toString(), StringValueHelper.ABOUT_YOGA_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_ABOUT_YOGA_HINDI, ABOUT_YOGA_DOWNLOAD_URL_HINDI);
                break;
            case R.id.linearlayoutYoga3:
                if (prefUtils.getLanguage().equals("English"))
                    onShowYogaDailog(R.drawable.standing, textYogaStanding.getText().toString(), StringValueHelper.YOGA_POWER_STANDING_URL, StringValueHelper.YOGA_POWER_PDF_URL_STANDING, YOGA_POWER_DOWNLOAD_SEATING_URL);
                else
                    onShowYogaDailog(R.drawable.standing, textYogaStanding.getText().toString(), StringValueHelper.YOGA_POWER_STANDING_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_STANDING_HINDI, YOGA_POWER_STANDING_DOWNLOAD_URL_HINDI);
                break;
            case R.id.linearlayoutYoga4:
                if (prefUtils.getLanguage().equals("English"))
                    onShowYogaDailog(R.drawable.sitting, textYogaSeating.getText().toString(), StringValueHelper.YOGA_POWER_SEATING_URL, StringValueHelper.YOGA_POWER_PDF_URL_SEATING, YOGA_POWER_DOWNLOAD_SEATING_URL);
                else
                    onShowYogaDailog(R.drawable.sitting, textYogaSeating.getText().toString(), StringValueHelper.YOGA_POWER_SEATING_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_SEATING_HINDI, YOGA_POWER_SEATING_DOWNLOAD_URL_HINDI);
                break;
            case R.id.linearlayoutYoga5:
                if (prefUtils.getLanguage().equals("English"))
                    onShowYogaDailog(R.drawable.lying, textYogaLying.getText().toString(), StringValueHelper.YOGA_POWER_LYING_URL, StringValueHelper.YOGA_POWER_PDF_URL_LYING, YOGA_POWER_DOWNLOAD_LYING_URL);
                else
                    onShowYogaDailog(R.drawable.lying, textYogaLying.getText().toString(), StringValueHelper.YOGA_POWER_LYING_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_LYING_HINDI, YOGA_POWER_LYING_DOWNLOAD_URL_HINDI);
                break;
            case R.id.linearlayoutYoga6:
                if (prefUtils.getLanguage().equals("English"))
                    onShowYogaDailog(R.drawable.meditation, textYogaMeditation.getText().toString(), StringValueHelper.YOGA_POWER_MEDITATION_URL, StringValueHelper.YOGA_POWER_PDF_URL_MEDITATION, YOGA_POWER_DOWNLOAD_MEDITATION_URL);
                else
                    onShowYogaDailog(R.drawable.meditation, textYogaMeditation.getText().toString(), StringValueHelper.YOGA_POWER_MEDITATION_URL_HINDI, StringValueHelper.YOGA_POWER_PDF_URL_MEDITATION_HINDI, YOGA_POWER_MEDITATION_DOWNLOAD_URL_HINDI);
                break;
        }

 */

    }

    private void onShowYogaDailog(int yoga1, final String yogaPowerName, final String videoURL, final String pdfUrl, final String videoDownloadURL) {
        video_name = yogaPowerName;
        builder = new AlertDialog.Builder(DashboardActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_dailog, null, false);
        builder.setView(view);
       dialog = builder.create();
       //new code

        //

        TextView textYoga = (TextView) view.findViewById(R.id.txt_yoga);
        textYoga.setText(yogaPowerName);
        ImageView imageYoga = (ImageView) view.findViewById(R.id.img_logo);
        imageYoga.setImageResource(yoga1);
        TextView textDialogTitle = (TextView) view.findViewById(R.id.dialogTitle);
        textDialogTitle.setText(R.string.app_title);
        TextView txt_download = (TextView) view.findViewById(R.id.txt_download);
        txt_download.setText(R.string.download);
        TextView txt_download_light = (TextView) view.findViewById(R.id.txt_download_light);
        txt_download_light.setText(R.string.download);
        TextView txt_offLine = (TextView) view.findViewById(R.id.txt_offLine);
        txt_offLine.setText(R.string.offline);
        TextView txt_playOnline = (TextView) view.findViewById(R.id.txt_playOnline);
        txt_playOnline.setText(R.string.online);
        TextView txt_readPdf = (TextView) view.findViewById(R.id.txt_readPdf);
        txt_readPdf.setText(R.string.readPDF);
        TextView close_icon = (TextView) view.findViewById(R.id.txt_close);
        linearDownload = (LinearLayout) view.findViewById(R.id.linearDownload);
        LinearLayout linearPlayOnline = (LinearLayout) view.findViewById(R.id.linearPlayOnline);
        RelativeLayout linearDialogPlayOnline = (RelativeLayout) view.findViewById(R.id.linearDialogPlayOnline);
        linearDialogPlayOnline.setBackgroundResource(yoga1);
        LinearLayout linearReadPDF = (LinearLayout) view.findViewById(R.id.linearReadPDF);
        linearOffline = (LinearLayout) view.findViewById(R.id.linearOffline);
        linearDownloadLight = (LinearLayout) view.findViewById(R.id.linearDownloadLight);
        if (prefUtils.getLanguage().equals("Hindi")) {
            textDialogTitle.setText(R.string.app_title_hindi);
            txt_download.setText(R.string.download_hindi);
            txt_offLine.setText(R.string.offline_hindi);
            txt_playOnline.setText(R.string.online_hindi);
            txt_readPdf.setText(R.string.readPDF_hindi);
            txt_download_light.setText(R.string.download_hindi);

        } else {
            textDialogTitle.setText(R.string.app_title);
            txt_download.setText(R.string.download);
            txt_offLine.setText(R.string.offline);
            txt_playOnline.setText(R.string.online);
            txt_readPdf.setText(R.string.readPDF);
            txt_download_light.setText(R.string.download);
        }

        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = VideoStatusTable.select(db, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(video_name) && cursor.getString(4).equals("Completed")) {
                linearOffline.setVisibility(View.VISIBLE);
                linearDownload.setVisibility(View.GONE);
                linearDownloadLight.setVisibility(View.GONE);
            } else if (cursor.getString(1).equals(video_name) && cursor.getString(4).equals("Progress")) {
                linearOffline.setVisibility(View.GONE);
                linearDownload.setVisibility(View.GONE);
                linearDownloadLight.setVisibility(View.VISIBLE);
            }
        }
        db.close();
        linearDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownload(video_name, videoDownloadURL);
            }
        });
        linearOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(DashboardActivity.this);
                progressDialog.setTitle("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onPlayOffline();
                    }
                }, 100);
            }
        });
        linearPlayOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onPlayOnline(videoURL);
            }
        });


// new code
        linearReadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReadPDF(video_name, pdfUrl);
            }
        });
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        linearDialogPlayOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onPlayOnline(videoURL);
            }
        });

        dialog.show();
    }
    /*
    ImageView playButton = findViewById(R.id.Play_Button_1);
        //playButton.setTag(videoURL);
        playButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String videoURL=getCurrentVideoURL();
            Intent intent = new Intent(DashboardActivity.this, Video_Intent.class);
            intent.putExtra("url", (String) v.getTag());
            startActivity(intent);
        }
    }

    private String getCurrentVideoURL() {

    });

     */

    public void onDownload(final String fileName, String videoURL) {
        Log.d("1234", "onDownload: "+videoURL);
        FileUtils.deleteDownloadedFile(DashboardActivity.this, fileName);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(videoURL);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(fileName);
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //PRDownloader.download(videoURL, FileUtils.getDirPath(DashboardActivity.this), fileName).build().setOnStartOrResumeListener((OnStartOrResumeListener) DashboardActivity.this).start((OnDownloadListener) DashboardActivity.this);
       // request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", fileName);
        refId = downloadManager.enqueue(request);
        


        if (prefUtils.getLanguage().equals("Hindi")) {
            updateUI("फाइल डौन्लोडिंग.. " + fileName);
        } else {
            updateUI("Downloading file.. " + fileName);
        }
        //dialog.dismiss();
    }

    private void onPlayOffline() {
        try {
            playVideo(FileUtils.getTempFileDescriptor(DashboardActivity.this, decrypt(video_name)));
        } catch (IOException e) {
            updateUI("Error Playing Video.\n Exception: " + e.getMessage());
            return;
        }
    }


    @OptIn(markerClass = UnstableApi.class) private void playVideo(String path) {
        //dialog.dismiss();
        progressDialog.dismiss();

        if (path != null) {
            Intent intent = new Intent(DashboardActivity.this, PlayVideoActivity.class);
            intent.putExtra("url", path);
            intent.putExtra("name", video_name);
            intent.putExtra("type","offline");
            startActivity(intent);
        }
    }

    private void onReadPDF(String fileName, String url) {
        //dialog.dismiss();
        Intent intent = new Intent(DashboardActivity.this, ReadPDFActivity.class);
        intent.putExtra("fileName", fileName);
        intent.putExtra("pdfUrl", url);
        startActivity(intent);
    }

    private void onPlayOnline(final String videoURL) {
        //dialog.dismiss();
        new Handler().postDelayed(new Runnable() {
            @OptIn(markerClass = UnstableApi.class) @Override
            public void run() {
                if (prefUtils.getLanguage().equals("English")) {
                    updateUI("Video Playing Online");
                } else {
                    updateUI("प्ले वीडियो ऑनलाइन");
                }
                //Intent intent = new Intent(DashboardActivity.this, PlayVideoActivity.class);
                Intent intent = new Intent(DashboardActivity.this, PlayVideoActivity.class);
                intent.putExtra("url", videoURL);
                intent.putExtra("name", video_name);
                intent.putExtra("type", "Online");
                startActivity(intent);
            }
        }, 100);


    }


    @Override
    public boolean handleMessage(Message message) {
        if (null != message) {
            updateUI(message.obj.toString());
        }
        return false;
    }

    private void updateUI(String s) {
        Toast.makeText(DashboardActivity.this, "" + s, Toast.LENGTH_SHORT).show();
    }


    // Encrypt and save to disk

    private boolean encrypt(String name) {
        video_name = name;
        try {
            byte[] fileData = FileUtils.readFile(FileUtils.getFilePath(DashboardActivity.this, video_name));
            byte[] encodedBytes = EncryptDecryptUtils.encode(EncryptDecryptUtils.getInstance(DashboardActivity.this).getSecretKey(), fileData);
            FileUtils.saveFile(encodedBytes, FileUtils.getFilePath(DashboardActivity.this, video_name));
            return true;
        } catch (Exception e) {
            updateUI("File Encryption failed.\nException: " + e.getMessage());
        }
        return false;
    }


    // Decrypt and return the decoded bytes

    private byte[] decrypt(String name) {
        video_name = name;
        try {
            byte[] fileData = FileUtils.readFile(FileUtils.getFilePath(DashboardActivity.this, video_name));
            byte[] decryptedBytes = EncryptDecryptUtils.decode(EncryptDecryptUtils.getInstance(DashboardActivity.this).getSecretKey(), fileData);
            return decryptedBytes;
        } catch (Exception e) {
            updateUI("File Decryption failed.\nException: " + e.getMessage());
        }
        return null;
    }
/*
    @Override
    public void onStartOrResume() {
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        String whereClause = "name='" + video_name + "'";
        value.put("status", "Progress");
        VideoStatusTable.update(db, value, whereClause);
        db.close();
    }

    @Override
    public void onError(Error error) {
        if (prefUtils.getLanguage().equals("Hindi")) {
            updateUI("फाइल डाउनलोड एरर");
        } else {
            updateUI("File Download Error");
        }
        downloadManager.remove(refId);
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        String whereClause = "name='" + video_name + "'";
        value.put("status", "No");
        VideoStatusTable.update(db, value, whereClause);
        db.close();
    }


    @Override
    public void onDownloadComplete() {
//        if (encrypt(video_name)) {
//            if (prefUtils.getLanguage().equals("Hindi")) {
//                updateUI(video_name + " डाउनलोड कम्प्लेटेड.");
//            } else {
//                updateUI(video_name + " Video Download Complete.");
//            }
//            MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
//            SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
//            ContentValues value = new ContentValues();
//            String whereClause = "name='" + video_name + "'";
//            value.put("status", "Completed");
//            VideoStatusTable.update(db, value, whereClause);
//            db.close();
//        }
    }


 */
    private void allVideoDownloadInHindi() {
        // download video A Hindi
        if (arrayListName.get(0).equals(ABOUT_YOGA_DAY_HINDI) && arrayListStatus.get(0).equals("No")) {
            video_name = ABOUT_YOGA_DAY_HINDI;
            final DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(ABOUT_YOGA_DAY_URL_HINDI);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(video_name);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", video_name);
            refId = downloadManager.enqueue(request);
            updateUI("फाइल डौन्लोडिंग... " + video_name);
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (downloadId == refId) {
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(refId);
                        Cursor cursor = downloadManager.query(query);
                        if (cursor.moveToFirst()) {
                            int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            int status = cursor.getInt(statusIndex);
                            switch (status) {
                                case DownloadManager.STATUS_SUCCESSFUL:
                                    if (encrypt(ABOUT_YOGA_HINDI)) {
                                        updateUI("फाइल डाउनलोड कम्प्लीटेड");
                                        // Update status to "Completed" in your SQLite database
                                    }
                                    break;
                                case DownloadManager.STATUS_FAILED:
                                    updateUI("फाइल डाउनलोड एरर");
                                    // Update status to "No" in your SQLite database
                                    break;
                                case DownloadManager.STATUS_PAUSED:
                                case DownloadManager.STATUS_PENDING:
                                case DownloadManager.STATUS_RUNNING:
                                    // Handle download in progress if needed
                                    break;
                            }
                        }
                        cursor.close();
                        unregisterReceiver(this);
                    }
                }
            };
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
/*
            downloadManager=new DownloadManager(ABOUT_YOGA_DAY_URL, FileUtils.getDirPath(DashboardActivity.this), video_name).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + video_name + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {


                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(video_name)) {
                        updateUI("फाइल डाउनलोड कम्प्लेटेड");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + video_name + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("फाइल डाउनलोड एरर");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + video_name + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();

                }
            });

             */


        // download video B
        if (arrayListName.get(1).equals(ABOUT_YOGA_HINDI) && arrayListStatus.get(1).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(ABOUT_YOGA_URL_HINDI);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(ABOUT_YOGA_HINDI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", ABOUT_YOGA_HINDI);
            refId = downloadManager.enqueue(request);
            updateUI("फाइल डौन्लोडिंग... " + ABOUT_YOGA_HINDI);
            /*
            PRDownloader.download(ABOUT_YOGA_URL_HINDI, FileUtils.getDirPath(DashboardActivity.this), ABOUT_YOGA_HINDI).build().setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {


                }
            }).setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {

                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + ABOUT_YOGA_HINDI + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(ABOUT_YOGA_HINDI)) {
                        updateUI("फाइल डाउनलोड कम्प्लेटेड");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + ABOUT_YOGA_HINDI + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                        }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("फाइल डाउनलोड एरर");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + ABOUT_YOGA_HINDI + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });
        }

        // download video C
        if (arrayListName.get(2).equals(YOGA_POWER_STANDING_HINDI) && arrayListStatus.get(2).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_STANDING_URL_HINDI);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_STANDING_HINDI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_STANDING_HINDI);
            refId = downloadManager.enqueue(request);
            PRDownloader.download(YOGA_POWER_STANDING_URL_HINDI, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_STANDING_HINDI)
                    .build().setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {

                }
            }).setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("फाइल डौन्लोडिंग... " + YOGA_POWER_STANDING_HINDI);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_STANDING_HINDI + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_STANDING_HINDI)) {
                        updateUI("फाइल डाउनलोड कम्प्लेटेड");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_STANDING_HINDI + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();

                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("फाइल डाउनलोड एरर");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_STANDING_HINDI + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });

             */
        }

        // download video D
        if (arrayListName.get(3).equals(YOGA_POWER_SEATING_HINDI) && arrayListStatus.get(3).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_SEATING_URL_HINDI);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_SEATING_HINDI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_SEATING_HINDI);
            refId = downloadManager.enqueue(request);
            updateUI("फाइल डौन्लोडिंग... " + YOGA_POWER_SEATING_HINDI);
            /*
            PRDownloader.download(YOGA_POWER_SEATING_URL_HINDI, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_SEATING_HINDI).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_SEATING_HINDI + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {
                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_SEATING_HINDI)) {
                        updateUI("फाइल डाउनलोड कम्प्लेटेड");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_SEATING_HINDI + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("फाइल डाउनलोड एरर");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_SEATING_HINDI + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });

             */
        }



        // download video E
        if (arrayListName.get(4).equals(YOGA_POWER_LYING_HINDI) && arrayListStatus.get(4).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_LYING_URL_HINDI);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_LYING_HINDI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_LYING_HINDI);
            refId = downloadManager.enqueue(request);
            /*
            PRDownloader.download(YOGA_POWER_LYING_URL_HINDI, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_LYING_HINDI).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("फाइल डौन्लोडिंग... " + YOGA_POWER_LYING_HINDI);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_LYING_HINDI + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {

                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_LYING_HINDI)) {
                        updateUI("फाइल डाउनलोड कम्प्लेटेड");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_LYING_HINDI + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("फाइल डाउनलोड एरर");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_LYING_HINDI + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });

             */
        }



        // download video F
        if (arrayListName.get(5).equals(YOGA_POWER_MEDITATION_HINDI) && arrayListStatus.get(5).equals("No")) {
            Log.d("1234", "allVideoDownload: " + arrayListStatus.get(5));
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_MEDITATION_URL_HINDI);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_MEDITATION_HINDI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_MEDITATION_HINDI);
            refId = downloadManager.enqueue(request);
            /*
            PRDownloader.download(YOGA_POWER_MEDITATION_URL_HINDI, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_MEDITATION_HINDI).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("फाइल डौन्लोडिंग... " + YOGA_POWER_MEDITATION_HINDI);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_MEDITATION_HINDI + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {

                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_MEDITATION_HINDI)) {
                        updateUI("फाइल डाउनलोड कम्प्लेटेड");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_MEDITATION_HINDI + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                        builder.setTitle("Success");
                        builder.setMessage(YOGA_POWER_MEDITATION_HINDI + " डाउनलोड कम्प्लेटेड.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("फाइल डाउनलोड एरर");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_MEDITATION_HINDI + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });

             */
        }
    }




    private void allVideoDownload() {
        // download video A
        if (arrayListName.get(0).equals(ABOUT_YOGA_DAY) && arrayListStatus.get(0).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(StringValueHelper.ABOUT_YOGA_DAY_URL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(ABOUT_YOGA_DAY);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", ABOUT_YOGA_DAY);
            refId = downloadManager.enqueue(request);
            updateUI("Downloading file.. " + ABOUT_YOGA_DAY);
            /*
            PRDownloader.download(StringValueHelper.ABOUT_YOGA_DAY_URL, FileUtils.getDirPath(DashboardActivity.this), ABOUT_YOGA_DAY).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + ABOUT_YOGA_DAY + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {


                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(ABOUT_YOGA_DAY)) {
                        updateUI(ABOUT_YOGA_DAY + " Video Download Completed");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + ABOUT_YOGA_DAY + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("File Download Error");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + video_name + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();

                }
            });
            */

        }



        // download video B
        if (arrayListName.get(1).equals(ABOUT_YOGA) && arrayListStatus.get(1).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(ABOUT_YOGA_URL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(ABOUT_YOGA);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", ABOUT_YOGA);
            refId = downloadManager.enqueue(request);
            /*
            PRDownloader.download(ABOUT_YOGA_URL, FileUtils.getDirPath(DashboardActivity.this), ABOUT_YOGA).build().setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {


                }
            }).setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("Downloading file... " + ABOUT_YOGA);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + ABOUT_YOGA + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(ABOUT_YOGA)) {
                        updateUI(ABOUT_YOGA + " Video Download Completed");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + ABOUT_YOGA + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("File Download Error");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + ABOUT_YOGA + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });
            */
        }



        // download video C
        if (arrayListName.get(2).equals(YOGA_POWER_STANDING) && arrayListStatus.get(2).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_STANDING_URL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_STANDING);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_STANDING);
            refId = downloadManager.enqueue(request);
            /*

            PRDownloader.download(YOGA_POWER_STANDING_URL, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_STANDING).build().setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {

                }
            }).setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("Downloading file... " + YOGA_POWER_STANDING);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_STANDING + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_STANDING)) {
                        updateUI(YOGA_POWER_SEATING + "Video Download Completed");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_STANDING + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("File Download Error");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_STANDING + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });
           */

        }



        // download video D
        if (arrayListName.get(3).equals(YOGA_POWER_SEATING) && arrayListStatus.get(3).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_SEATING_URL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_SEATING);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_SEATING);
            refId = downloadManager.enqueue(request);
            /*
            PRDownloader.download(YOGA_POWER_SEATING_URL, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_SEATING).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("Downloading file... " + YOGA_POWER_SEATING);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_SEATING + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {
                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_SEATING)) {
                        updateUI(YOGA_POWER_SEATING + "Video Download Completed");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_SEATING + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();

                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("File Download Error");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_SEATING + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });

             */
        }

        // download video E
        if (arrayListName.get(4).equals(YOGA_POWER_LYING) && arrayListStatus.get(4).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_LYING_URL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_LYING);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_LYING);
            refId = downloadManager.enqueue(request);
            /*
            PRDownloader.download(YOGA_POWER_LYING_URL, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_LYING).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("Downloading file... " + YOGA_POWER_LYING);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_LYING + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {

                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_LYING)) {
                        updateUI(YOGA_POWER_LYING + "Video Download Completed");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_LYING + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();

                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("File Download Error");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_LYING + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });

             */
        }

        // download video F
        if (arrayListName.get(5).equals(YOGA_POWER_MEDITATION) && arrayListStatus.get(5).equals("No")) {
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final Uri uri = Uri.parse(YOGA_POWER_MEDITATION_URL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(YOGA_POWER_MEDITATION);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(DashboardActivity.this, "Download", YOGA_POWER_MEDITATION);
            refId = downloadManager.enqueue(request);
/*
            PRDownloader.download(YOGA_POWER_MEDITATION_URL, FileUtils.getDirPath(DashboardActivity.this), YOGA_POWER_MEDITATION).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                @Override
                public void onStartOrResume() {
                    updateUI("Downloading file... " + YOGA_POWER_MEDITATION);
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_MEDITATION + "'";
                    value.put("status", "Progress");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            }).setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(Progress progress) {

                }
            }).start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    if (encrypt(YOGA_POWER_MEDITATION)) {
                        updateUI(YOGA_POWER_MEDITATION + "Video Download Completed");
                        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                        ContentValues value = new ContentValues();
                        String whereClause = "name='" + YOGA_POWER_MEDITATION + "'";
                        value.put("status", "Completed");
                        VideoStatusTable.update(db, value, whereClause);
                        db.close();
                    }
                }

                @Override
                public void onError(Error error) {
                    downloadManager.remove(refId);
                    updateUI("File Download Error");
                    MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(DashboardActivity.this);
                    SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
                    ContentValues value = new ContentValues();
                    String whereClause = "name='" + YOGA_POWER_MEDITATION + "'";
                    value.put("status", "No");
                    VideoStatusTable.update(db, value, whereClause);
                    db.close();
                }
            });

 */
        }


    }

}