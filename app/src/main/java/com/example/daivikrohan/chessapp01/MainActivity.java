package com.example.daivikrohan.chessapp01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = (Button) findViewById(R.id.PlayButton);
        Button replay = (Button)findViewById(R.id.ReplayButton);
        if(play != null) {
            play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ChessActivity.class);
                    startActivityForResult(i, 0);
                }
            });
        }
        /*
        if(replay != null) {
            play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ChessActivity.class);
                    startActivityForResult(i, 0);
                }
            });
        }*/

    }

}
