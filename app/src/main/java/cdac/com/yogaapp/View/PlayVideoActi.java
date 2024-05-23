package cdac.com.yogaapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.Timeline;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.TrackSelectionArray;

public abstract class PlayVideoActi extends AppCompatActivity {
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    public void onPlayerError(ExoPlaybackException error) {

    }

    public void onSeekProcessed() {

    }
}
