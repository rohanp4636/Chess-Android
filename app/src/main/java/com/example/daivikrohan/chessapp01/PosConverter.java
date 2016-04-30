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

    public static String gridToString(int pos){
        int r = Math.abs(gridToRow(pos) - 8);
        int c = gridToCol(pos);
        switch (c){
            case 0: return "a"+r;
            case 1: return "b"+r;
            case 2: return "c"+r;
            case 3: return "d"+r;
            case 4: return "e"+r;
            case 5: return "f"+r;
            case 6: return "g"+r;
            case 7: return "h"+r;
            default: return "";
        }
    }


}
