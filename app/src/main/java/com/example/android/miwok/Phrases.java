package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrases extends AppCompatActivity {
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
        setContentView(R.layout.activity_phrases);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> a = new ArrayList<Word>();
        a.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        a.add(new Word ("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        a.add(new Word ("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        a.add(new Word ("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        a.add(new Word ("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        a.add(new Word ("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        a.add(new Word ("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        a.add(new Word ("I’m coming.","әәnәm",R.raw.phrase_im_coming));


        WordAdapter adapter = new WordAdapter(this, a, R.color.category_phrases);

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
                    mMediaPlayer = MediaPlayer.create(Phrases.this, word.getMediaPlayer());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
