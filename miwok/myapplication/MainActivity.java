package com.orton.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Orton on 26-05-2017.
 */

public class MainActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPrime(View v)
    {
        Intent i = new Intent(this,Main2Activity.class);
        startActivity(i);

    }
}