package com.orton.myapplication;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Orton on 25-05-2017.
 */

public class WordAdapter extends ArrayAdapter<WordClass> {

    int colorResourceId ;
    //context:is used to inflate the layout file
    //and word is the data we want to populate in the list
    //color id : gets the color resource id from colors.xml
    public WordAdapter(Activity context, ArrayList<WordClass> word, int colorId)
    {
        super(context,0,word);
        colorResourceId = colorId ;
    }

    //position: The AdapterView Position that is requesting a view
    //convertView : the recycled view to populate
    //parent : that is used for inflation
    private MediaPlayer media ;
    public View getView(int position, @Nullable final View convertView,  ViewGroup parent) {


        final WordClass currentWord = getItem(position);
        View listItemView = convertView ;
        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item,parent,false);
        }

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_translation);
        defaultTextView.setText(currentWord.getDefault());
        defaultTextView.setBackgroundResource(colorResourceId);
        TextView miwoktextView = (TextView) listItemView.findViewById(R.id.miwok_translation);
        miwoktextView.setText(currentWord.getMiwok());
        miwoktextView.setBackgroundResource(colorResourceId);

        //play audio when the button is pressed and the context which is passed to create method can be derived from view in
        //onClick method which is passed when the button is clicked and it contains the context that we want in getContext method
        //Remember resource id is always an integer so that can be fetched from currentWord class

        /**
         *
        if(currentWord.getAudioResource()!=-1) {
            playButton.setOnClickListener(new View.OnClickListener(){
                  public void onClick(View v){
                      Context context=WordAdapter.this.getContext();
                      media = MediaPlayer.create(v.getContext(),currentWord.getAudioResource());
                      media.start();
                  }

            });
        }
         *
         **/
        ImageView imageSourceView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.findImage()==true)
        {
            imageSourceView.setImageResource(currentWord.getImageResource());
            imageSourceView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageSourceView.setVisibility(View.GONE);
        }
        return listItemView ;
    }
}
