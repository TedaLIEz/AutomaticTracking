package com.hustunique.jianguo.autoeventtracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button = (Button) findViewById(R.id.test);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "I am clicked");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, BlankFragment.newInstance("test", "test1"))
                        .commit();
            }
        });
        Button button1 = (Button) findViewById(R.id.test1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Blank2Fragment())
                        .commit();
                Log.i(TAG, "I am clicked too");
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
