package com.example.myapplication1;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class PhrasesFragment extends Fragment {

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

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //creating an arrayList of words
        final ArrayList<Word> phraseList = new ArrayList<>();

        //adding elements
        phraseList.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phraseList.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phraseList.add(new Word("My name is...?","oyaaset...", R.raw.phrase_my_name_is));
        phraseList.add(new Word("How are you feeling?","michәksәs?", R.raw.phrase_how_are_you_feeling));
        phraseList.add(new Word("I’m feeling good.","kuchi achit", R.raw.phrase_im_feeling_good));
        phraseList.add(new Word("Are you coming?","әәnәs'aa?", R.raw.phrase_are_you_coming));
        phraseList.add(new Word("Yes, I’m coming.","hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phraseList.add(new Word("I’m coming.","әәnәm", R.raw.phrase_im_coming));
        phraseList.add(new Word("Let’s go.","yoowutis", R.raw.phrase_lets_go));
        phraseList.add(new Word("Come here.","әnni'nem", R.raw.phrase_come_here));

        //finding list view for displaying
        ListView phraseView = (ListView) rootView.findViewById(R.id.wordList);

        //creating wordAdapter object for phraseView
        WordAdapter phraseAdapter = new WordAdapter(getActivity(),phraseList,R.color.category_phrases);

        //setting the adapter for view
        phraseView.setAdapter(phraseAdapter);


        phraseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = phraseList.get(position);

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

            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
