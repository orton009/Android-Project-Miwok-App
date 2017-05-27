package com.orton.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        NumbersOnLongClickListener clickListener1 = new NumbersOnLongClickListener();
        TextView numbersTextView = (TextView) findViewById(R.id.numbers_text_view);
        numbersTextView.setOnLongClickListener(clickListener1);

        TextView familyMembersTextView = (TextView) findViewById(R.id.family_members_text_view);
        familyMembersTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(),"This shows family members",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        TextView colorTextView = (TextView) findViewById(R.id.color_text_view);
        colorTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(),"This shows colors",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        TextView phrasesTextView = (TextView) findViewById(R.id.phrases_text_view);
        phrasesTextView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v)
            {
                Toast.makeText(v.getContext(),"This shows phrases",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void openNumbersList(View v)
    {
        Intent i = new Intent(this,NumbersActivity.class);
        startActivity(i);

    }
    public void openFamilyMembersList(View v)
    {
        Intent i = new Intent(this,FamilyMembersActivity.class);
        startActivity(i);
    }
    public void openColorList(View v)
    {
        Intent i = new Intent(this,ColorsActivity.class);
        startActivity(i);
    }
    public void openPhrasesList(View v)
    {
        Intent i = new Intent(this,PhrasesActivity.class);
        startActivity(i);
    }
}
