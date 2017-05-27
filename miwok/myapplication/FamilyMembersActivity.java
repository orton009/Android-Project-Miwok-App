package com.orton.myapplication;
import java.util.*;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FamilyMembersActivity extends AppCompatActivity {

    /** Handles audio focus when playing a sound file */
    AudioManager mAudioManager ;

    MediaPlayer media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //create a list of words using custom class
        final ArrayList<WordClass> wordList = new ArrayList<WordClass>();
        wordList.add(new WordClass("father", "әpә", R.drawable.family_father,R.raw.family_father));
        wordList.add(new WordClass("mother", "әṭa", R.drawable.family_mother,R.raw.family_mother));
        wordList.add(new WordClass("son", "angsi", R.drawable.family_son,R.raw.family_son));
        wordList.add(new WordClass("daughter", "tune", R.drawable.family_daughter,R.raw.family_daughter));
        wordList.add(new WordClass("older brother", "taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        wordList.add(new WordClass("younger brother", "chalitti", R.drawable.family_younger_brother,R.raw.family_younger_brother));
        wordList.add(new WordClass("older sister", "teṭe", R.drawable.family_older_sister,R.raw.family_younger_sister));
        wordList.add(new WordClass("younger sister", "kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        wordList.add(new WordClass("grandmother ", "ama", R.drawable.family_grandmother,R.raw.family_grandmother));
        wordList.add(new WordClass("grandfather", "paapa", R.drawable.family_grandfather,R.raw.family_grandfather));

        /**LinearLayout rootView = (LinearLayout) findViewById(R.id.num_linear_layout);
         for(int i=0;i<10;i++)
         {
         TextView word = new TextView(this);
         word.setText(wordList.get(i));
         rootView.addView(word);
         }**/

        //create a custom adapter extending ArrayAdapter of type WordClass
        WordAdapter adapter = new WordAdapter(this,wordList,R.color.family);
        ListView listview = (ListView) findViewById(R.id.list_view);
        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WordClass word = wordList.get(position);
                releaseMediaPlayer();
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
               int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(mAudioManager.AUDIOFOCUS_REQUEST_GRANTED == result)
                {
                    media = MediaPlayer.create(view.getContext(),word.getAudioResource());
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    media.start();
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    media.setOnCompletionListener(mMediaPlayer);

                }
            }
        });

    }
    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK )
            {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                media.pause();
                media.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                media.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }

        }
    };

    // overriding onCompletionListener so as to setup the action you want to perform when the music is completed
    //here we are cleaning up the resources so as to improve device performance by freeing up ram
    MediaPlayer.OnCompletionListener mMediaPlayer = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (media != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            media.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            media = null;
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    //it is a part of android activity Life Cycle
    //when the user switches to a different screen then the activity goes inn stop state
    //by overriding this method mediaplayer resources are released so that play function is no longer performed and
    //the memory is freed up to increase device efficiency
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}


