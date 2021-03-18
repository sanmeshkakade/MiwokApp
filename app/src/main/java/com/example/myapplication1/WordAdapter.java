package com.example.myapplication1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    int colorId = -1;
    //Constructor

    public WordAdapter(@NonNull Context context, ArrayList<Word> wordArrayList, int color) {
        super(context, 0, wordArrayList);
        this.colorId = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
        }

        //find image view
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.wordImage);
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        View text_fieldView = listItemView.findViewById(R.id.text_field);
        if(colorId != -1){
            text_fieldView.setBackgroundResource(colorId);
        }


        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView wordTextView = (TextView) listItemView.findViewById(R.id.text_view);
        // Get the version name from the current Word object and
        // set this text on the word TextView
        wordTextView.setText(currentWord.getWord());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultWordTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current Word object and
        // set this text on the defaultWord TextView
        defaultWordTextView.setText(currentWord.getDefaultTranslation());

        return listItemView;
    }
}
