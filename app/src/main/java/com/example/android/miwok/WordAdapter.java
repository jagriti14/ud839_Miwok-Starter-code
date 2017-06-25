package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 19-06-2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int mBackcolor;
    private static final String LOG_TAG = WordAdapter.class.getSimpleName();
    public WordAdapter(Activity context, ArrayList<Word> androidFlavors, int backcolor) {
        super(context, 0, androidFlavors);
        mBackcolor=backcolor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word currentAndroidFlavor = getItem(position);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.item1);
        nameTextView.setText(currentAndroidFlavor.getMivokLanguage());
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.item2);
        numberTextView.setText(currentAndroidFlavor.getDefaultLanguage());
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.item3);
        if (currentAndroidFlavor.hasImage()) {
            imageView.setImageResource(currentAndroidFlavor.getImageResourceId());
                       imageView.setVisibility(View.VISIBLE);
                    }
        else {

                               imageView.setVisibility(View.GONE);
                }
        View textContainer = listItemView.findViewById(R.id.layout);
        int color = ContextCompat.getColor(getContext(), mBackcolor);
        textContainer.setBackgroundColor(color);
        return listItemView;

    }
}
