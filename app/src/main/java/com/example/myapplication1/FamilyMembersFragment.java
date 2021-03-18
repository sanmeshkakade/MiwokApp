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

public class FamilyMembersFragment extends Fragment {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange ==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
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

        View rootView = inflater.inflate(R.layout.word_list,container,false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //creating arraylist for family members
        final ArrayList<Word> familyArrayList = new ArrayList<Word>();
        //adding data
        familyArrayList.add(new Word("father","әpә", R.drawable.family_father, R.raw.family_father));
        familyArrayList.add(new Word("mother","әṭa", R.drawable.family_mother, R.raw.family_mother));
        familyArrayList.add(new Word("son","angsi", R.drawable.family_son, R.raw.family_son));
        familyArrayList.add(new Word("daughter","tune", R.drawable.family_daughter, R.raw.family_daughter));
        familyArrayList.add(new Word("older brother","taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        familyArrayList.add(new Word("younger brother","chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        familyArrayList.add(new Word("older sister","teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        familyArrayList.add(new Word("younger sister","kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyArrayList.add(new Word("grandmother","ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        familyArrayList.add(new Word("grandfather","paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        //finding the list view for wordadapter
        ListView familyRootView = (ListView) rootView.findViewById(R.id.wordList);

        //creating word adapter object
        WordAdapter familyAdapter = new WordAdapter(getActivity(), familyArrayList,R.color.category_family);

        //setting the adapter for listview
        familyRootView.setAdapter(familyAdapter);

        familyRootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = familyArrayList.get(position);

                releaseMediaPlayer();


                int audiofocusRequest = audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(audiofocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
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

            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
