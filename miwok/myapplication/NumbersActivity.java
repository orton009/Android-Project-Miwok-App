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

public class NumbersActivity extends AppCompatActivity {

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
        wordList.add(new WordClass("one", "lutti", R.drawable.number_one, R.raw.number_one));
        wordList.add(new WordClass("two", "otiiko", R.drawable.number_two,R.raw.number_two));
        wordList.add(new WordClass("three", "tolookosu", R.drawable.number_three,R.raw.number_three));
        wordList.add(new WordClass("four", "oyyisa", R.drawable.number_four,R.raw.number_four));
        wordList.add(new WordClass("five", "massokka", R.drawable.number_five,R.raw.number_five));
        wordList.add(new WordClass("six", "temmokka", R.drawable.number_six,R.raw.number_six));
        wordList.add(new WordClass("seven", "kenekaku", R.drawable.number_seven,R.raw.number_seven));
        wordList.add(new WordClass("eight", "kawinta", R.drawable.number_eight,R.raw.number_eight));
        wordList.add(new WordClass("nine", "wo’e", R.drawable.number_nine,R.raw.number_nine));
        wordList.add(new WordClass("ten", "na’aacha", R.drawable.number_ten,R.raw.number_ten));

        /**LinearLayout rootView = (LinearLayout) findViewById(R.id.num_linear_layout);
         for(int i=0;i<10;i++)
         {
         TextView word = new TextView(this);
         word.setText(wordList.get(i));
         rootView.addView(word);
         }**/

        //create a custom adapter extending ArrayAdapter of type WordClass
        WordAdapter adapter = new WordAdapter(this,wordList,R.color.numbers);
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
                int num = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(mAudioManager.AUDIOFOCUS_REQUEST_GRANTED== num)
                {
                    media = MediaPlayer.create(view.getContext(),word.getAudioResource());
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    media.start();
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                  media.setOnCompletionListener(mMediaPlayer);
                    //restore media to its inital stage

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

   protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}


