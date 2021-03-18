package com.example.myapplication1;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    // media player instance to playback
    // the media file from the raw folder
    private MediaPlayer mediaPlayer = new MediaPlayer();

    // Audio manager instance to manage or
    // handle the audio interruptions
    private AudioManager audioManager;

    // Audio attributes instance to set the playback
    // attributes for the media player instance
    // these attributes specify what type of media is
    // to be played and used to callback the audioFocusChangeListener
    //AudioAttributes playbackAttributes;

    // media player is handled according to the
    // change in the focus which Android system grants for
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN ) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                    || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
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

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list,container,false);

        // get the audio system service for
        // the audioManger instance
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);






        //declaring ArrayList for colors
        final ArrayList<Word> colorList = new ArrayList<Word>();
        //Adding members to list
        colorList.add(new Word("Red","weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        colorList.add(new Word("green","chokokki", R.drawable.color_green, R.raw.color_green));
        colorList.add(new Word("brown","ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        colorList.add(new Word("gray","ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        colorList.add(new Word("black","kululli", R.drawable.color_black, R.raw.color_black));
        colorList.add(new Word("white","kelelli", R.drawable.color_white, R.raw.color_white));
        colorList.add(new Word("dusty yellow","ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorList.add(new Word("mustard yellow","chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //Declaring the View for GridView
        ListView colorRootView = (ListView) rootView.findViewById(R.id.wordList);

        //Creating ArrayAdapter for handling colors
        WordAdapter colorAdapter = new WordAdapter (getActivity(), colorList,R.color.category_colors);

        //Setting the Adapter
        colorRootView.setAdapter(colorAdapter);

        colorRootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = colorList.get(position);

                releaseMediaPlayer();

                // initiate the audio playback attributes
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//                    playbackAttributes = new AudioAttributes.Builder()
//                            .setUsage(AudioAttributes.USAGE_GAME)
//                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                            .build();
//                    // set the playback attributes for the focus requester
//                    AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
//                            .setAudioAttributes(playbackAttributes)
//                            .setAcceptsDelayedFocusGain(true)
//                            .setOnAudioFocusChangeListener(audioFocusChangeListener)
//                            .build();
//
//                    // request the audio focus and
//                    // store it in the int variable
//                    int audioFocusRequest = audioManager.requestAudioFocus(focusRequest);
//                }else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {}
                int audioFocusRequest = audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_RING,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioID());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //when the activity is stopped, release the media player resource because we won't be playing any more sounds
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

            //abandon the audio focus when playback is complete
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
