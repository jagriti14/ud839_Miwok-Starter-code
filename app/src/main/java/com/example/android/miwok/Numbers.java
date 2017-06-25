package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Numbers extends AppCompatActivity {

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
        setContentView(R.layout.activity_numbers);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> a = new ArrayList<Word>();
        a.add(new Word("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        a.add(new Word("Two", "Owttiko", R.drawable.number_two, R.raw.number_two));
        a.add(new Word("Three", "Tolokossu", R.drawable.number_three, R.raw.number_three));
        a.add(new Word("Four", "Oyissa", R.drawable.number_four, R.raw.number_four));
        a.add(new Word("Five", "Massoka", R.drawable.number_five, R.raw.number_five));
        a.add(new Word("Six", "Temmoka", R.drawable.number_six, R.raw.number_six));
        a.add(new Word("Seven", "Kenekaku", R.drawable.number_seven, R.raw.number_seven));
        a.add(new Word("Eight", "Kawinta", R.drawable.number_eight, R.raw.number_eight));
        a.add(new Word("Nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        a.add(new Word("Ten", "na'accha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(this, a, R.color.category_numbers);

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
                    mMediaPlayer = MediaPlayer.create(Numbers.this, word.getMediaPlayer());
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
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }


}







