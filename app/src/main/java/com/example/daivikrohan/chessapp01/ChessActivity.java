package com.example.daivikrohan.chessapp01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;


public class ChessActivity extends AppCompatActivity {
    ChessBoard chessBoard;
    Boolean displayMoves = false;
    ChessPiece selectedPiece;
    ArrayList<String> pieceMoves = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chessboard);

       final GridView gv = (GridView) findViewById(R.id.GridView);
        selectedPiece = null;
        chessBoard = Chess.initChess();

        gv.setAdapter(new ImageAdapter(this));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetTiles(gv);
                BoardSquarePiece bsp = (BoardSquarePiece) gv.getChildAt(position).getTag();
                int row = PosConverter.gridToRow(bsp.boardPos);
                int col = PosConverter.gridToCol(bsp.boardPos);

                if (chessBoard.board[row][col] != null && Chess.turn == chessBoard.board[row][col].color) {
                    ArrayList<String> moves = chessBoard.board[row][col].listMoves(chessBoard);
                    for(int x = 0; x < moves.size(); x++){
                        String i = moves.get(x);
                        int r = Integer.parseInt(i.substring(0, 1));
                        int c = Integer.parseInt(i.substring(1, 2));
                        if(!chessBoard.board[row][col].isValidMove(r,c,chessBoard)){
                            moves.remove(x);
                            x--;
                        }
                        else{
                            ((BoardSquarePiece)gv.getChildAt(PosConverter.RCtoGrid(r,c)).getTag()).background.setImageResource(R.drawable.movesquare);
                        }
                    }
                    if(!moves.isEmpty()){
                        selectedPiece = chessBoard.board[row][col];
                        pieceMoves.addAll(moves);
                    }
                    else{
                        selectedPiece = null;
                        if(pieceMoves != null){
                            pieceMoves.clear();
                        }
                        resetTiles(gv);
                    }
                    return;
                }
                if(selectedPiece != null){

                }
            }
        });


    }

    public void resetTiles(GridView gv){
        for(int i = 0; i < 64; i++){
            BoardSquarePiece bsp = (BoardSquarePiece) gv.getChildAt(i).getTag();
            bsp.background.setImageResource(bsp.oldBack);
        }
    }



}