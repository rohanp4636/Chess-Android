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
    int selectedPos;
    ArrayList<String> pieceMoves;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chessboard);

       final GridView gv = (GridView) findViewById(R.id.GridView);
        selectedPiece = null;
        chessBoard = Chess.initChess();
        pieceMoves = new ArrayList<String>();
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
                        selectedPos = bsp.getPos();
                        pieceMoves.clear();
                        pieceMoves.addAll(moves);
                    }
                    else{
                        selectedPiece = null;
                        if(pieceMoves != null){
                            pieceMoves.clear();
                        }
                        resetTiles(gv);
                        selectedPos = -1;
                    }
                    return;
                }
                if(selectedPiece != null){
                    int rowp = PosConverter.gridToRow(bsp.boardPos);
                    int colp = PosConverter.gridToCol(bsp.boardPos);
                    Boolean canMove = false;
                    if(pieceMoves.contains(""+rowp+colp)){   //else illegal move???
                        if(selectedPiece instanceof pawn && rowp == 0){
                            canMove = checkInput(PosConverter.gridToString(selectedPos)+" "+PosConverter.gridToString(bsp.boardPos) + " Q",chessBoard);
                        }else{
                            canMove = checkInput(PosConverter.gridToString(selectedPos)+" "+PosConverter.gridToString(bsp.boardPos),chessBoard);
                        }

                        if(!canMove){
                            selectedPiece = null;
                            if(pieceMoves != null){
                                pieceMoves.clear();
                            }
                            resetTiles(gv);
                        }
                        else{
                            resetTiles(gv);
                            if(selectedPos != -1){
                                bsp.chessPiece.setImageResource( ((BoardSquarePiece)gv.getChildAt(selectedPos).getTag()).piece  );
                                ((BoardSquarePiece)gv.getChildAt(selectedPos).getTag()).chessPiece.setImageResource(R.drawable.emptysquare);
                                bsp.piece =  ((BoardSquarePiece)gv.getChildAt(selectedPos).getTag()).piece;
                                ((BoardSquarePiece)gv.getChildAt(selectedPos).getTag()).piece = R.drawable.emptysquare;
                            }
                        /*
                            if(Chess.blackCheckMate || Chess.whiteCheckMate){
                                System.out.println("");
                                chessBoard.printBoard();
                                System.out.println("Checkmate\n");
                            }
                            else if(Chess.blackCheck || Chess.whiteCheck ){
                                    System.out.println("");
                                    chessBoard.printBoard();
                                    System.out.println("Check\n");
                            }
                           else if(Chess.stalemate){
                                    System.out.println("");
                                    chessBoard.printBoard();
                                    System.out.println("Stalemate");
                           }



                            */

                            Chess.whiteTurn = !Chess.whiteTurn;
                            if(Chess.whiteTurn){
                                Chess.turn = 0;
                            }
                            else{
                                Chess.turn = 1;
                            }



                        }
                        /*
                        if(winner == 1){
                            System.out.println("Black wins");

                        }
                        else if(winner == 0){
                            System.out.println("White wins");
                        }
                        else{
                            System.out.println("\nDraw");

                        }*/

                    }


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


    static boolean checkInput(String input, ChessBoard cb){
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
        else if(input.matches("[a-hA-H][1-8] [a-hA-H][1-8] [qrbnQRBN] [dD][rR][aA][wW][?]") || input.matches("[a-hA-H][1-8] [a-hA-H][1-8] [dD][rR][aA][wW][?]") ){
            //ask second user for draw
            Chess.askDraw = true;
            conv = ChessBoard.convertPos(input);
            file1 = Integer.parseInt(conv.substring(0, 1));
            rank1 = Integer.parseInt(conv.substring(1, 2));
            file2 = Integer.parseInt(conv.substring(3, 4));
            rank2 = Integer.parseInt(conv.substring(4, 5));
            char c = input.charAt(6);
            if(c == 'd' || c == 'D'){
                if(cb.board[rank1][file1] != null && cb.board[rank1][file1] instanceof pawn && (rank2 == 7 || rank2 == 0) ){
                    return false;
                }
                if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == Chess.turn){
                    return cb.board[rank1][file1].move(rank2, file2,cb);
                }
            }
            else{
                if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == Chess.turn && cb.board[rank1][file1] instanceof pawn){
                    return ((pawn)(cb.board[rank1][file1])).promote(rank2, file2,cb, c);
                }
            }

            return false;
        }
        else if(input.equalsIgnoreCase("resign")){
            Chess.resign = true;
            System.out.println("");
            if(Chess.whiteTurn){
                Chess.winner = 1;

            }
            else{
                Chess.winner = 0;
            }
            Chess.gameOver = true;
            return true;
        }
        else if(input.equalsIgnoreCase("draw")){
            if(Chess.askDraw){
                Chess.isDraw = true;
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }


    }


}