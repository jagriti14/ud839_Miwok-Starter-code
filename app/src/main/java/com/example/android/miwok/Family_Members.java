package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Family_Members extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if ( focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                        if(mMediaPlayer!= null) {
                            mMediaPlayer.pause();
                            mMediaPlayer.seekTo(0);
                        }
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        if(mMediaPlayer!= null)
                        mMediaPlayer.start();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS )
                    {
                        releaseMediaPlayer();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_family__members);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> a = new ArrayList<Word>();
        a.add(new Word("Mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        a.add(new Word ("Son","angsi",R.drawable.family_son,R.raw.family_son));
        a.add(new Word ("Daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        a.add(new Word ("Older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        a.add(new Word ("Younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        a.add(new Word ("Older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        a.add(new Word ("Younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        a.add(new Word ("Father","әpә",R.drawable.family_father,R.raw.family_father));
        a.add(new Word ("Grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        a.add(new Word ("Grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(this, a, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = a.get(i);
                releaseMediaPlayer();


                int result = mAudioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //We have audio focus now
                    mMediaPlayer = MediaPlayer.create(Family_Members.this, word.getMediaPlayer());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }

        });
    }
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
