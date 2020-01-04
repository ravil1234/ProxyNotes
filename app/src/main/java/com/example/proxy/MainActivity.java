package com.example.proxy;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
public class MainActivity extends AppCompatActivity {
    int count=0;
    private int Position = 0;
    private static final String PLAYBACK_TIME = "play_time";
    private static final String VIDEO_SAMPLE[] = {"song_one","song_two","song_three","song_four","song_six","song_seven"};
    private VideoView mVideoView;
    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://" + getPackageName() +
                "/raw/" + mediaName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.videoview);
        if (savedInstanceState != null) {
            Position = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        MediaController controller = new MediaController(this);
        mVideoView.setMediaController(controller);
        controller.setMediaPlayer(mVideoView);

        mVideoView.setOnTouchListener(new OnSwipe(MainActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastTop), Toast.LENGTH_SHORT).show();
                Position=0;
                if(count==VIDEO_SAMPLE.length)
                {
                    count=0;
                }
                else
                    count++;
                initializePlayer();
            }
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastRight), Toast.LENGTH_SHORT).show();

            }
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastLeft), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toastBottom), Toast.LENGTH_SHORT).show();
                Position=0;
                if(count==0)
                    count=VIDEO_SAMPLE.length-1;
                else
                    count--;
                initializePlayer();

            }
        });
    }
    private void initializePlayer() {

            Uri videoUri = getMedia(VIDEO_SAMPLE[count]);
            mVideoView.setVideoURI(videoUri);
            if (Position > 0) {
                mVideoView.seekTo(Position);
            } else {
                mVideoView.seekTo(1);
            }
            mVideoView.start();
    }
    private void releasePlayer() {
        mVideoView.stopPlayback();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
       releasePlayer();
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }
    public void save(View v)
    {
        Toast.makeText(getApplicationContext(),"downloading..", Toast.LENGTH_SHORT).show();
    }
    public  void share(View v)
    {
        Toast.makeText(getApplicationContext(),"share video!", Toast.LENGTH_SHORT).show();
    }
    public  void like(View v)
    {
        Toast.makeText(getApplicationContext(),"liked!", Toast.LENGTH_SHORT).show();
    }
}
