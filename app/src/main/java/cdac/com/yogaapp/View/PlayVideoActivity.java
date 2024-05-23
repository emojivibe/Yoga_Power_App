package cdac.com.yogaapp.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSourceFactory;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.dash.DashMediaSource;
import androidx.media3.exoplayer.dash.manifest.DashManifest;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.ui.PlayerView;

import cdac.com.yogaapp.R;

@UnstableApi public class PlayVideoActivity extends PlayVideoActi implements Player.Listener{


    ExoPlayer player;
    PlayerView exoPlayerView;

    //public static final int MIN_BUFFER_DURATION = 3000;
    public static final int MIN_BUFFER_DURATION = 3000;
    public static final int MAX_BUFFER_DURATION = 5000;
    public static final int MIN_PLAYBACK_START_BUFFER = 1500;
    //previously,MIN_PLAYBACK_RESUME_BUFFER = 5000;
    public static final int MIN_PLAYBACK_RESUME_BUFFER = 500;
    Handler mHandler;
    Runnable mRunnable;
    ProgressBar progressBar;
    private android.content.Context Context;
    //private Object ExoPlayerFactory;

    //private static final DefaultBandwidthMeter BANDWIDTH_METER =new DefaultBandwidthMeter.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This line calls the onCreate method of the superclass, ensuring that any initialization code in the superclass is executed before the code in this onCreate method.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //This line sets flags on the activity's window to make it fullscreen and keep the screen on, preventing it from sleeping
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//This line adds a flag to dismiss the keyguard (lock screen) when the activity is displayed.
        setContentView(R.layout.activity_play_video);
        exoPlayerView = (PlayerView) findViewById(R.id.exoplayer);
        //exoPlayerView.bringToFront();
        progressBar = (ProgressBar) findViewById(R.id.spinnerVideoDetails);
        //problem here
        initializePlayer();

    }
    /*

    private void setUp() {
        //problem below
        initializePlayer();
        Intent intent = getIntent();
        String uriPath = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");
        Uri uri = Uri.parse(uriPath);
        if (type.equals("Online"))
            buildMediaOnlineSource(uri);
        else
            buildMediaSourceOffline(uri);

    }

     */

    


    private void initializePlayer() {
        if(player==null){
            player = new ExoPlayer.Builder(this).build();
            exoPlayerView.setPlayer(player);
            //exoPlayerView.bringToFront();
            Intent intent = getIntent();
            if(intent!=null){
                String uriPath=intent.getStringExtra("url");
                String type=intent.getStringExtra("type");
                if (uriPath != null) {
                    Uri uri = Uri.parse(uriPath);
                    if ("Online".equals(type)) {
                        buildMediaOnlineSource(uri);
                    } else if ("Offline".equals(type)) {
                        buildMediaSourceOffline(uri);
                    }
                   // buildMediaOnlineSource(uri);
                    // Assuming you have a method to handle media sources
                    //buildMediaSourceOffline(uri);
                } else {

                    Log.e("MainActivity", "Error: uriPath is null");
                }
            }else{
                Log.e("MainActivity", "Error: Intent is null");
            }
        }
        }
        //return null;
       // return null;


    @OptIn(markerClass = UnstableApi.class) private void buildMediaOnlineSource(Uri uri) {
        Log.d("12345", "buildMediaSource: " + uri);
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSource.Factory();
        //Uri uri=Uri.parse("http://192.168.1.41:8000/uploads/courses/20b5c0e0-871e-43a9-a996-9ecbcfbeefc5/index.m3u8");
/*
        HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                .setAllowChunklessPreparation(false)
                .createMediaSource(MediaItem.fromUri(uri));
        player.setMediaSource(hlsMediaSource);
        */



        MediaSource MediaSource = new DashMediaSource.Factory(dataSourceFactory)
                //.setAllowChunklessPreparation(false)
                .createMediaSource(MediaItem.fromUri(uri));
        player.setMediaSource(MediaSource);


        player.prepare();
        // the golden notion which made it work//
        player.play();
        //player.setPlayWhenReady(true);
        Log.d("PlayVideoActivity", "PlayerView visibility: " + exoPlayerView.getVisibility());
        Log.d("PlayVideoActivity", "Player state: " + player.getPlaybackState());
        player.addListener(new Player.Listener() {
            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {
                Object manifest = player.getCurrentManifest();
                if (manifest != null) {
                    //HlsManifest hlsManifest = (HlsManifest) manifest;
                    DashManifest dashManifest = (DashManifest) manifest;
                    // Do something with the manifest.
                }

            }
            @Override
            public void onPlayerError(PlaybackException error) {
                if (error instanceof ExoPlaybackException) {
                    ExoPlaybackException exoPlaybackException = (ExoPlaybackException) error;
                    switch (exoPlaybackException.type) {
                        case ExoPlaybackException.TYPE_SOURCE:
                            Log.e("ExoPlayer", "Source error: " + exoPlaybackException.getSourceException().getMessage());
                            break;
                        case ExoPlaybackException.TYPE_RENDERER:
                            Log.e("ExoPlayer", "Renderer error: " + exoPlaybackException.getRendererException().getMessage());
                            break;
                        case ExoPlaybackException.TYPE_UNEXPECTED:
                            Log.e("ExoPlayer", "Unexpected error: " + exoPlaybackException.getUnexpectedException().getMessage());
                            break;
                    }
                }
            }
        });
    }

    @OptIn(markerClass = UnstableApi.class) private void buildMediaSourceOffline(Uri uri){
        Log.d("1234", "buildMediaSourceOffline: "+uri);
        MediaSource videoSource = new Factory(
                new DefaultDataSourceFactory(PlayVideoActivity.this,"exoplayer-codelab")).
                createMediaSource(uri);

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (player == null) {
            initializePlayer();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
            //initializePlayer();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
/*
    @OptIn(markerClass = UnstableApi.class) private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @OptIn(markerClass = UnstableApi.class) private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    @OptIn(markerClass = UnstableApi.class)
    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resumePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

 */

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {

            case Player.STATE_BUFFERING:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                break;
            case Player.STATE_IDLE:

                break;
            case Player.STATE_READY:
                progressBar.setVisibility(View.GONE);
                exoPlayerView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }


    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }



    private class Factory {
        public Factory(DefaultDataSourceFactory defaultDataSourceFactory) {
        }

        public MediaSource createMediaSource(Uri uri) {
            return null;
        }
    }


}