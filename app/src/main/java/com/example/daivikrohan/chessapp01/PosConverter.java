package com.example.daivikrohan.chessapp01;

/**
 * Created by Home on 4/24/2016.
 */
// utility class to convert grid positions to 2d array positions. and vice-versa
public class PosConverter {

    public static int gridToRow(int pos){
        return pos / 8;
    }

    public static int gridToCol(int pos){
        return pos % 8;
    }

    public static int RCtoGrid(int row, int col){
        return (row*8)+col;
    }

}
