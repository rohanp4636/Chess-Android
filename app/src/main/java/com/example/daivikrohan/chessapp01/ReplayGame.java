package com.example.daivikrohan.chessapp01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Home on 5/2/2016.
 */
public class ReplayGame extends AppCompatActivity {

    static ChessBoard chessBoard;
    ArrayList<String> moves;
    GridView gv;
    Toast toast;
    int index;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chessreplay);
        gv=(GridView)findViewById(R.id.GridViewReplay);
        index=0;
        chessBoard = Chess.initChess();
        moves=getIntent().getStringArrayListExtra("GameMoveList");
        gv.setAdapter(new ImageAdapter(this));
        toast = Toast.makeText(getApplicationContext(), "White's Turn", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void nextMove(View view)
    {
        if(moves.size()>index)
        {
            checkInput(moves.get(index), chessBoard);
            if(index==moves.size()-1)
            {
                return;
            }
            afterMove(moves.get(index));
            updateBoard(gv);
            index++;
        }
    }

    public boolean checkInput(String input, ChessBoard cb){
        int file1 = -1;;
        int rank1 = -1;
        int file2 = -1;
        int rank2 = -1;
        String conv = "";
        if(input.matches("[a-hA-H][1-8] [a-hA-H][1-8]")){
            conv = ChessBoard.convertPos(input);
            file1 = Integer.parseInt(conv.substring(0, 1));
            rank1 = Integer.parseInt(conv.substring(1, 2));
            file2 = Integer.parseInt(conv.substring(3, 4));
            rank2 = Integer.parseInt(conv.substring(4, 5));
            Chess.askDraw = false;
            if(cb.board[rank1][file1] != null && cb.board[rank1][file1] instanceof pawn && (rank2 == 7 || rank2 == 0) ){
                return false;
            }
            if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == Chess.turn){
                return cb.board[rank1][file1].move(rank2, file2,cb);
            }

            return false;  //move, check promotion, castling...
        }
        else if(input.matches("[a-hA-H][1-8] [a-hA-H][1-8] [qrbnQRBN]")){
            Chess.askDraw = false;
            conv = ChessBoard.convertPos(input);
            file1 = Integer.parseInt(conv.substring(0, 1));
            rank1 = Integer.parseInt(conv.substring(1, 2));
            file2 = Integer.parseInt(conv.substring(3, 4));
            rank2 = Integer.parseInt(conv.substring(4, 5));
            char c = input.charAt(6);
            Chess.askDraw = false;
            if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == Chess.turn){
                return ((pawn)(cb.board[rank1][file1])).promote(rank2, file2,cb, c);
            }
            return false;  //promotion
        }
        else if(input.matches("checkmate white") || input.matches("checkmate black") || input.matches("stalemate") || input.matches("resign white") || input.matches("resign black") || input.matches("draw"))
        {
            gameOver(input);
            return true;
        }
        else{
            return false;
        }


    }

    public void afterMove(String moveInput){
            updateBoard(gv);
            if(index==moves.size()-1)
            {
                return;
            }
           Chess.whiteTurn = !Chess.whiteTurn;
            if (Chess.whiteTurn) {
                Chess.turn = 0;
                if(toast != null){
                    toast.cancel();
                }
                if(Chess.whiteCheck){
                    toast = Toast.makeText(getApplicationContext(), "White's Turn. Check", Toast.LENGTH_SHORT);
                }
                else{
                    toast = Toast.makeText(getApplicationContext(), "White's Turn", Toast.LENGTH_SHORT);
                }
                toast.show();
            } else {
                Chess.turn = 1;
                if(toast != null){
                    toast.cancel();
                }
                if(Chess.blackCheck){
                    toast = Toast.makeText(getApplicationContext(), "Black's Turn. Check", Toast.LENGTH_SHORT);
                }
                else{
                    toast = Toast.makeText(getApplicationContext(), "Black's Turn", Toast.LENGTH_SHORT);
                }

                toast.show();
            }

        }


    public  void gameOver(String message){
        int title=R.string.tie;
        int mes=R.string.tie;
        if(message.equals("checkmate white")) {
            title=R.string.checkmate;
            mes=(R.string.whiteWin);
        }
        else if(message.equals("checkmate black")) {
            title=R.string.checkmate;
            mes=(R.string.blackWin);
        }
        else if(message.equals("stalemate")) {
            title=R.string.stalemate;
            mes=(R.string.tie);
        }
        else if(message.equals("draw")) {
            title=R.string.draw;
            mes=(R.string.tie);
        }
        else if(message.equals("resign white")) {
            title=R.string.whiteResign;
            mes=(R.string.blackWin);
        }
        else if(message.equals("resign black")) {
            title=R.string.blackResign;
            mes=(R.string.whiteWin);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(mes);

        builder.setPositiveButton(R.string.list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public void updateBoard(GridView gv){
        for(int i = 0; i < 64; i++){
            BoardSquarePiece bsp = (BoardSquarePiece) gv.getChildAt(i).getTag();
            bsp.chessPiece.setImageResource(getPieceNum(chessBoard.board[PosConverter.gridToRow(i)][PosConverter.gridToCol(i)]));

        }
    }

    public int getPieceNum(ChessPiece piece){
        if(piece == null){
            return R.drawable.emptysquare;
        }
        switch (piece.toString()){
            case "wp ": return R.drawable.whitepawn;
            case "wR ": return R.drawable.whiterook;
            case "wN ": return R.drawable.whiteknight;
            case "wB ": return R.drawable.whitebishop;
            case "wQ ": return R.drawable.whitequeen;
            case "wK ": return R.drawable.whiteking;

            case "bp ": return R.drawable.blackpawn;
            case "bR ": return R.drawable.blackrook;
            case "bN ": return R.drawable.blackknight;
            case "bB ": return R.drawable.blackbishop;
            case "bQ ": return R.drawable.blackqueen;
            case "bK ": return R.drawable.blackking;
            default: return R.drawable.emptysquare;

        }
    }
}
