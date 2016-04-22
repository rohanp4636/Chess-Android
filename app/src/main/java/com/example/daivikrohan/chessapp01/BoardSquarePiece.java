package com.example.daivikrohan.chessapp01;

import android.view.View;
import android.widget.ImageView;


public class BoardSquarePiece {
    public ImageView background;
    public ImageView chessPiece;
    int boardPos;


    public BoardSquarePiece(int pos, Integer[] board, View context){
        this.boardPos = pos;
        this.background = (ImageView) context.findViewById(R.id.chessTile);
        this.background.setImageResource(board[pos]);
            if(pos == 0){
                this.background.setImageResource(R.drawable.movesquare);
            }
        this.chessPiece = (ImageView) context.findViewById(R.id.chessPiece);
        this.chessPiece.setImageResource(getPosition(pos));
        //set resource for both
    }

    public static int getPosition(int pos){
        switch (pos){
            case 0: return R.drawable.blackrook;
            case 1: return R.drawable.blackknight;
            case 2: return R.drawable.blackbishop;
            case 3: return R.drawable.blackqueen;
            case 4: return R.drawable.blackking;
            case 5: return R.drawable.blackbishop;
            case 6: return R.drawable.blackknight;
            case 7: return R.drawable.blackrook;

            case 8: return R.drawable.blackpawn;
            case 9: return R.drawable.blackpawn;
            case 10: return R.drawable.blackpawn;
            case 11: return R.drawable.blackpawn;
            case 12: return R.drawable.blackpawn;
            case 13: return R.drawable.blackpawn;
            case 14: return R.drawable.blackpawn;
            case 15: return R.drawable.blackpawn;

            case 48: return R.drawable.whitepawn;
            case 49: return R.drawable.whitepawn;
            case 50: return R.drawable.whitepawn;
            case 51: return R.drawable.whitepawn;
            case 52: return R.drawable.whitepawn;
            case 53: return R.drawable.whitepawn;
            case 54: return R.drawable.whitepawn;
            case 55: return R.drawable.whitepawn;

            case 56: return R.drawable.whiterook;
            case 57: return R.drawable.whiteknight;
            case 58: return R.drawable.whitebishop;
            case 59: return R.drawable.whitequeen;
            case 60: return R.drawable.whiteking;
            case 61: return R.drawable.whitebishop;
            case 62: return R.drawable.whiteknight;
            case 63: return R.drawable.whiterook;
            default: return R.drawable.emptysquare;

        }

    }

}
