package com.example.daivikrohan.chessapp01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;


public class ChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chessboard);

        GridView gv = (GridView) findViewById(R.id.GridView);

        gv.setAdapter(new ImageAdapter(this));
    }

}