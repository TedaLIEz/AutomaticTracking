package com.hustunique.jianguo.autoeventtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button button, button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn_track);
        button1 = (Button) findViewById(R.id.btn_track2);
        Button button2 = (Button) findViewById(R.id.btn_startAct);
        Button mergeBtn = (Button) findViewById(R.id.btn_merge);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        mergeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void startMain2(View v) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            Log.d(TAG, "button clicked");
        } else if (v == button1) {
            Log.d(TAG, "button1 clicked");
        }
    }
}
