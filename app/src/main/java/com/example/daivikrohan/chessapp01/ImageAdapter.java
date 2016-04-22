package com.example.daivikrohan.chessapp01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

/**
 *
 */
public class ImageAdapter extends BaseAdapter {
    private Context ct;
    private LayoutInflater inflater;


    //this array stores the squares for the chess board.
    private Integer[] boardID={
            R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,
            R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare,
            R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,
            R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare,
            R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,
            R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare,
            R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,
            R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare, R.drawable.darksquare,  R.drawable.lightsquare
    };


    public ImageAdapter(Context c) {
        this.ct = c;
        inflater = LayoutInflater.from(this.ct);
    }

    public int getCount() {
        return boardID.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View BoardSquare = convertView;

        if(BoardSquare == null){
            BoardSquare = inflater.inflate(R.layout.chesspiece,null);
            BoardSquarePiece bsp = new BoardSquarePiece( position,boardID, BoardSquare);
            BoardSquare.setTag(bsp);
            return BoardSquare;
        }
        return BoardSquare;
    }
}
