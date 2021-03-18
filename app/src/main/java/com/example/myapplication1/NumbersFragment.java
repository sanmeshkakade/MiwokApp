package com.example.myapplication1;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersFragment extends Fragment {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> numberArrayList = new ArrayList <Word>();
        //numberArrayList.add("one");
        numberArrayList.add(new Word("one","lutti", R.drawable.number_one,R.raw.number_one));
        numberArrayList.add(new Word("two","otiiko", R.drawable.number_two,R.raw.number_two));
        numberArrayList.add(new Word("three","tolookosu", R.drawable.number_three, R.raw.number_three));
        numberArrayList.add(new Word("four","oyyisa", R.drawable.number_four, R.raw.number_four));
        numberArrayList.add(new Word("five","massokka", R.drawable.number_five, R.raw.number_five));
        numberArrayList.add(new Word("six","temmokka", R.drawable.number_six, R.raw.number_six));
        numberArrayList.add(new Word("seven","kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numberArrayList.add(new Word("eight","kawinta", R.drawable.number_eight, R.raw.number_eight));
        numberArrayList.add(new Word("nine","wo’e", R.drawable.number_nine, R.raw.number_nine));
        numberArrayList.add(new Word("ten","na’aacha", R.drawable.number_ten, R.raw.number_ten));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter =
                new WordAdapter(getActivity(), numberArrayList,R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.wordList);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the object at the given position the user clicked on
                Word word = numberArrayList.get(position);

                //releasing the previously existing mediaPlayer because we are about to start new audio
                releaseMediaPlayer();

                int audioFocusRequest = audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //creating and setting up mediaPlayer for thr audio resource associated with the current word
                    mediaPlayer= MediaPlayer.create(getActivity(), word.getAudioID());

                    //starting the audio file
                    mediaPlayer.start();

                    //setting up a listener on the media player, so that we can stop and release the media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }

            }
        });
        return rootView;
    }





    @Override
    public void onStop (){
            super.onStop();
            releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}