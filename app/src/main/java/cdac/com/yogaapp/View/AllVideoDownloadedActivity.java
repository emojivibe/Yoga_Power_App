package cdac.com.yogaapp.View;

//import android.support.v7.app.AppCompatActivity;

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

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cdac.com.yogaapp.Adapter.CustomAdapter;
import cdac.com.yogaapp.Model.DownloadPojo;
import cdac.com.yogaapp.R;
import cdac.com.yogaapp.helper.PrefUtils;


public class AllVideoDownloadedActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<DownloadPojo> arrayList = new ArrayList<>();
    private PrefUtils prefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video_downloaded);
        prefUtils = new PrefUtils(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList.clear();

        if (prefUtils.getLanguage().equals("English")) {
            setTitle("Downloaded Videos");
            String videoUrlEnglish[] = {ABOUT_YOGA_DAY_URL, ABOUT_YOGA_DAY_URL, YOGA_POWER_STANDING_URL, YOGA_POWER_SEATING_URL, YOGA_POWER_LYING_URL, YOGA_POWER_MEDITATION_URL};
            String videoNameEnglish[] = {ABOUT_YOGA_DAY, ABOUT_YOGA, YOGA_POWER_STANDING, YOGA_POWER_SEATING, YOGA_POWER_LYING, YOGA_POWER_MEDITATION};
            String videoTime[] = {"06:52", "07:28", "11:00", "07:36", "08:42", "17:21"};
            int videoNameImage[] = {R.drawable.aboutyoga, R.drawable.yogaday, R.drawable.standing, R.drawable.sitting, R.drawable.lying, R.drawable.meditation};
            for (int i = 0; i < videoNameEnglish.length; i++) {
                DownloadPojo downloadPojo = new DownloadPojo();
                downloadPojo.setName(videoNameEnglish[i]);
                downloadPojo.setUrl(videoUrlEnglish[i]);
                downloadPojo.setImage(videoNameImage[i]);
                downloadPojo.setTime(videoTime[i]);
                arrayList.add(downloadPojo);
            }
        } else {
            setTitle("डाउनलोड वीडियो");
            String videoUrlHindi[] = {ABOUT_YOGA_DAY_URL_HINDI, ABOUT_YOGA_DAY_URL_HINDI, YOGA_POWER_STANDING_URL_HINDI, YOGA_POWER_SEATING_URL_HINDI, YOGA_POWER_LYING_URL_HINDI, YOGA_POWER_MEDITATION_URL_HINDI};
            String videoNameHindi[] = {ABOUT_YOGA_DAY_HINDI, ABOUT_YOGA_HINDI, YOGA_POWER_STANDING_HINDI, YOGA_POWER_SEATING_HINDI, YOGA_POWER_LYING_HINDI, YOGA_POWER_MEDITATION_HINDI};
            String videoTime[] = {"06:52", "07:28", "11:00", "07:36", "08:42", "17:21"};
            int videoNameImage[] = {R.drawable.aboutyoga, R.drawable.yogaday, R.drawable.standing, R.drawable.sitting, R.drawable.lying, R.drawable.meditation};
            for (int i = 0; i < videoNameHindi.length; i++) {
                DownloadPojo downloadPojo = new DownloadPojo();
                downloadPojo.setName(videoNameHindi[i]);
                downloadPojo.setUrl(videoUrlHindi[i]);
                downloadPojo.setImage(videoNameImage[i]);
                downloadPojo.setTime(videoTime[i]);
                arrayList.add(downloadPojo);
            }
        }

        mAdapter = new CustomAdapter(AllVideoDownloadedActivity.this, arrayList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

