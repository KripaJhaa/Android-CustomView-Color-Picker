package com.example.kripajha.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.kripajha.customviewdemo.Views.IcolorPalletListener;
import com.example.kripajha.customviewdemo.Views.CustomView;

public class MainActivity extends AppCompatActivity implements IcolorPalletListener{

    CustomView customViewObj;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customViewObj = findViewById(R.id.customView);
        layout = findViewById(R.id.bacgroundId);
        customViewObj.listenToColorPallet(this);
    }

    @Override
    public void onEvent(int color,int touchXPercentage,int touchYPercentage,int Action) {
        customViewObj.setProgressText(touchYPercentage+" k");
        Log.d("Listening ...", "value " + color+" touch X :"+touchXPercentage+" touch Y :"+touchYPercentage);
        layout.setBackgroundColor(color);
    }
}

