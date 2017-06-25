package com.example.android.miwok;

import android.media.MediaPlayer;

/**
 * Created by Dell on 19-06-2017.
 */

public class Word {
    private String mDefaultLanguage;
    private String mMivokLanguage;
    private int mMediaPlayer;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultLanguage , String mivokLanguage, int mediaPlayer)
    {
        mDefaultLanguage=defaultLanguage;
        mMivokLanguage=mivokLanguage;
        mMediaPlayer = mediaPlayer;

    }
    public Word(String defaultLanguage , String mivokLanguage, int imageResourceId,int mediaPlayer)
    {
        mDefaultLanguage=defaultLanguage;
        mMivokLanguage=mivokLanguage;
        mImageResourceId=imageResourceId;
        mMediaPlayer = mediaPlayer;
    }
    public String getDefaultLanguage()
    {
        return mDefaultLanguage;
    }
    public String getMivokLanguage()
    {
        return mMivokLanguage;
    }
    public int getImageResourceId() {
        return mImageResourceId;
    }
    public boolean hasImage() {
               return mImageResourceId != NO_IMAGE_PROVIDED;
            }
    public int getMediaPlayer() { return mMediaPlayer; }
}
