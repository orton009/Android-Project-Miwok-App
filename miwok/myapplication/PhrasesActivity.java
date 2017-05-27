package com.orton.myapplication;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    AudioManager mAudioManager ;
    MediaPlayer media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        final ArrayList<WordClass> wordList = new ArrayList<WordClass>();
        wordList.add(new WordClass("Where are you going?", "minto wuksus",R.raw.phrase_where_are_you_going));
        wordList.add(new WordClass("What is your name?", "tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        wordList.add(new WordClass("My name is...", "oyaaset...",R.raw.phrase_my_name_is));
        wordList.add(new WordClass("How are you feeling?", "michәksәs?",R.raw.phrase_how_are_you_feeling));
        wordList.add(new WordClass("I’m feeling good.", "kuchi achit",R.raw.phrase_im_feeling_good));
        wordList.add(new WordClass("Are you coming?", "әәnәs'aa?",R.raw.phrase_are_you_coming));
        wordList.add(new WordClass("Yes, I’m coming.", "hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        wordList.add(new WordClass("I’m coming.", "әәnәm",R.raw.phrase_im_coming));
        wordList.add(new WordClass("Let’s go.", "yoowutis",R.raw.phrase_lets_go));
        wordList.add(new WordClass("Come here.", "әnni'nem",R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this,wordList,R.color.phrases);
        ListView listview = (ListView) findViewById(R.id.list_view);
        //listview.setBackgroundColor(getResources().getColor(R.color.phrases));
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WordClass word = wordList.get(position);
                releaseMediaPlayer();
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
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
        public void onCompletion(MediaPlayer mp) {
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

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
