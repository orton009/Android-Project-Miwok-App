package com.orton.myapplication;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Orton on 23-05-2017.
 */

public class NumbersOnLongClickListener implements View.OnLongClickListener {
    public boolean onLongClick(View v)
    {
        Toast.makeText(v.getContext(),"This shows numbers",Toast.LENGTH_SHORT).show();
        return true;
    }
}
