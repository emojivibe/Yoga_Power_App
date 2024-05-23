package cdac.com.yogaapp.View;

import static cdac.com.yogaapp.helper.StringValueHelper.ABOUT_YOGA_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.UnstableApi;

import cdac.com.yogaapp.R;

public class Video_Intent extends AppCompatActivity {
    //private static final String VIDEO_URL = "http://192.168.0.8:8000/uploads/courses/About_Yoga/index.m3u8";
    //private static final String VIDEO_URL = "https://93e8-2405-201-5c06-2976-1d28-f8a3-2bac-952b.ngrok-free.app/assets/English/about_english_yoga/manifest.mpd ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dailog);
        ImageView playButton = findViewById(R.id.Play_Button_1);
        Intent intent=getIntent();
        final String videoURL=intent.getStringExtra("url");
        //Button playButton2 = findViewById(R.id.playBut2);
        playButton.setOnClickListener(new View.OnClickListener() {
            @OptIn(markerClass = UnstableApi.class)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Video_Intent.this, PlayVideoActivity.class);

                intent.putExtra("url",ABOUT_YOGA_URL);
                startActivity(intent);
            }
        });


    }
}