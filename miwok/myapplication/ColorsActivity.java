package com.orton.myapplication;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    AudioManager mAudioManager;
    MediaPlayer media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        final ArrayList<WordClass> wordList = new ArrayList<WordClass>();
        wordList.add(new WordClass("red", "weṭeṭṭi", R.drawable.color_red,R.raw.color_red));
        wordList.add(new WordClass("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        wordList.add(new WordClass("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        wordList.add(new WordClass("green", "chokokki", R.drawable.color_green,R.raw.color_green));
        wordList.add(new WordClass("brown", "ṭakaakki", R.drawable.color_brown,R.raw.color_brown));
        wordList.add(new WordClass("gray", "ṭopoppi", R.drawable.color_gray,R.raw.color_gray));
        wordList.add(new WordClass("black", "kululli", R.drawable.color_black,R.raw.color_black));
        wordList.add(new WordClass("white", "kelelli", R.drawable.color_white,R.raw.color_white));

        /**LinearLayout rootView = (LinearLayout) findViewById(R.id.num_linear_layout);
         for(int i=0;i<10;i++)
         {
         TextView word = new TextView(this);
         word.setText(wordList.get(i));
         rootView.addView(word);
         }**/

        //create a custom adapter extending ArrayAdapter of type WordClass
        WordAdapter adapter = new WordAdapter(this,wordList,R.color.colors);
        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                WordClass word = wordList.get(position);
                mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
               int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
               if(mAudioManager.AUDIOFOCUS_REQUEST_GRANTED == result)
               {
                   media = MediaPlayer.create(view.getContext(), word.getAudioResource());
                   media.start();
                   media.setOnCompletionListener(mMediaPlayer);
               }
            }
        });

    }
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        //override
        public void onAudioFocusChange(int focusChange)
        {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                media.pause();
                media.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                media.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    } ;

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
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
