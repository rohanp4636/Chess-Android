package com.example.daivikrohan.chessapp01;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;


public class ChessActivity extends AppCompatActivity {
    static ChessBoard chessBoard;
    static ChessBoard oldBoard;
    Boolean displayMoves = false;
    ChessPiece selectedPiece;
    int selectedPos;
    ArrayList<String> pieceMoves;
    char promote;
    public Context ct;
    Boolean canMove;
    BoardSquarePiece bsp;
    GridView gv;
    Toast toast;
    Boolean undoTurn;
    ArrayList<String> saveMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chessboard);

        gv = (GridView) findViewById(R.id.GridView);

        ct = this;
        selectedPiece = null;
        promote = ' ';
        bsp = null;
        canMove = false;
        chessBoard = Chess.initChess();
        pieceMoves = new ArrayList<String>();
        undoTurn = false;
        saveMoves = new ArrayList<String>();

        /*Gridview and chess functionality*/
        gv.setAdapter(new ImageAdapter(this));
        toast = Toast.makeText(getApplicationContext(), "White's Turn", Toast.LENGTH_SHORT);
        toast.show();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetTiles(gv);
                bsp = (BoardSquarePiece) gv.getChildAt(position).getTag();
                int row = PosConverter.gridToRow(bsp.boardPos);
                int col = PosConverter.gridToCol(bsp.boardPos);

                if (chessBoard.board[row][col] != null && Chess.turn == chessBoard.board[row][col].color) {
                    ArrayList<String> moves = chessBoard.board[row][col].listMoves(chessBoard);
                    for (int x = 0; x < moves.size(); x++) {
                        String i = moves.get(x);
                        int r = Integer.parseInt(i.substring(0, 1));
                        int c = Integer.parseInt(i.substring(1, 2));
                        if (!chessBoard.board[row][col].isValidMove(r, c, chessBoard)) {
                            moves.remove(x);
                            x--;
                        } else {
                            ((BoardSquarePiece) gv.getChildAt(PosConverter.RCtoGrid(r, c)).getTag()).background.setImageResource(R.drawable.movesquare);
                        }
                    }
                    if (!moves.isEmpty()) {
                        selectedPiece = chessBoard.board[row][col];
                        selectedPos = bsp.getPos();
                        pieceMoves.clear();
                        pieceMoves.addAll(moves);
                    } else {
                        selectedPiece = null;
                        if (pieceMoves != null) {
                            pieceMoves.clear();
                        }
                        resetTiles(gv);
                        selectedPos = -1;
                    }
                    return;
                }
                if (selectedPiece != null) {
                    int rowp = PosConverter.gridToRow(bsp.boardPos);
                    int colp = PosConverter.gridToCol(bsp.boardPos);
                    canMove = false;
                    if (pieceMoves.contains("" + rowp + colp)) {   //else illegal move???
                        if ((selectedPiece instanceof pawn && rowp == 0) || (selectedPiece instanceof pawn && rowp == 7)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                            builder.setTitle(R.string.promoteText)
                                    .setItems(R.array.promotionPieces, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0:
                                                    promote = 'B';
                                                    break;
                                                case 1:
                                                    promote = 'N';
                                                    break;
                                                case 2:
                                                    promote = 'Q';
                                                    break;
                                                case 3:
                                                    promote = 'R';
                                                    break;
                                                default:
                                                    promote = ' ';
                                                    break;

                                            }
                                            canMove = false;
                                            canMove = checkInput(PosConverter.gridToString(selectedPos) + " " + PosConverter.gridToString(bsp.boardPos) + " " + promote, chessBoard);
                                            afterMove(PosConverter.gridToString(selectedPos) + " " + PosConverter.gridToString(bsp.boardPos) + " " + promote);
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            selectedPiece = null;

                        } else {
                            canMove = false;
                            canMove = checkInput(PosConverter.gridToString(selectedPos) + " " + PosConverter.gridToString(bsp.boardPos), chessBoard);
                            afterMove(PosConverter.gridToString(selectedPos) + " " + PosConverter.gridToString(bsp.boardPos));
                            promote = ' ';
                        }

                    }


                }

            }
        });

    }
    public void AIMove(View view) {
        ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
        //get all pieces from Board
        for(int i = 0; i < 64; i++ ){
            if(chessBoard.board[PosConverter.gridToRow(i)][PosConverter.gridToCol(i)] != null && chessBoard.board[PosConverter.gridToRow(i)][PosConverter.gridToCol(i)].color == Chess.turn) {
                pieces.add(chessBoard.board[PosConverter.gridToRow(i)][PosConverter.gridToCol(i)]);
            }
        }
        //get all pieces with possible moves
        ArrayList<String> moves = new ArrayList<String>();
        for(int k = 0; k < pieces.size(); k++) {
            ChessPiece i = pieces.get(k);
            moves.clear();
            moves.addAll(i.listMoves(chessBoard));
            int row = i.row;
            int col = i.column;

            for (int x = 0; x < moves.size(); x++) {
                String index = moves.get(x);
                int r = Integer.parseInt(index.substring(0, 1));
                int c = Integer.parseInt(index.substring(1, 2));
                if (!chessBoard.board[row][col].isValidMove(r, c, chessBoard)) {
                    moves.remove(x);
                    x--;
                }
            }
            if(moves.isEmpty()){
                pieces.remove(k);
            }

        }
        //choose random piece and select random move
        Random randomPiece = new Random();
        int pPos = randomPiece.nextInt(pieces.size());
        ChessPiece i = pieces.get(pPos);
        moves.clear();
        moves.addAll(i.listMoves(chessBoard));
        int row = i.row;
        int col = i.column;
        for (int x = 0; x < moves.size(); x++) {
            String index = moves.get(x);
            int r = Integer.parseInt(index.substring(0, 1));
            int c = Integer.parseInt(index.substring(1, 2));
            if (!chessBoard.board[row][col].isValidMove(r, c, chessBoard)) {
                moves.remove(x);
                x--;
            }
        }
            if(!moves.isEmpty()) {
                Random random = new Random();
                int movePos = random.nextInt(moves.size());
                String index = moves.get(movePos);

                int r = Integer.parseInt(index.substring(0, 1));
                int c = Integer.parseInt(index.substring(1, 2));


                if ((i instanceof pawn && r == 0) || (i instanceof pawn && r == 7)) {
                    canMove = false;
                    Random random2 = new Random();
                    int promoteRandom = random2.nextInt(4);
                    switch (promoteRandom){
                        case 0: promote = 'B';
                                canMove = checkInput(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote, chessBoard);
                                afterMove(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote);
                                break;
                        case 1: promote = 'N';
                                canMove = checkInput(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote, chessBoard);
                                afterMove(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote);
                                break;
                        case 2: promote = 'Q';
                                canMove = checkInput(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote, chessBoard);
                                afterMove(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote);
                                break;
                        case 3: promote = 'R';
                                canMove = checkInput(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote, chessBoard);
                                afterMove(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote);
                                break;
                        default: promote = 'B';
                                canMove = checkInput(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote, chessBoard);
                                afterMove(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)) + " " + promote);
                                break;
                    }

                    return;
                } else {
                    canMove = false;
                    canMove = checkInput(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)), chessBoard);

                    afterMove(PosConverter.gridToString(PosConverter.RCtoGrid(row, col)) + " " + PosConverter.gridToString(PosConverter.RCtoGrid(r, c)));
                    promote = ' ';
                    return;
                }
            }

    }

    public void drawGame(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.draw);
        builder.setMessage(R.string.confirmDraw);
        builder.setPositiveButton(R.string.draw, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                askDraw();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void askDraw(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.draw);
        if(Chess.whiteTurn){
            builder.setMessage(R.string.askDrawWhite);
        }
        else{
            builder.setMessage(R.string.askDrawBlack);
        }
        builder.setPositiveButton(R.string.draw, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Chess.isDraw = true;
                Chess.winner = -1;
                gameOver(R.string.draw, R.string.tie);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void resignGame(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirmresign);
        builder.setPositiveButton(R.string.resign, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Chess.resign = true;
                if (Chess.whiteTurn) {
                    Chess.winner = 1;
                    gameOver(R.string.whiteResign, R.string.blackWin);
                } else {
                    Chess.winner = 0;
                    gameOver(R.string.blackResign, R.string.whiteWin);
                }
                return;
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void undoMove(View view){
        if(oldBoard == null){
            if(toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(getApplicationContext(), "Can't Undo. No Moves Have Been Made.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(undoTurn){
            undoTurn = false;
            chessBoard = oldBoard;
            Chess.oldState();
            updateBoard(gv);
            if(!saveMoves.isEmpty()){
                saveMoves.remove(saveMoves.size()-1);
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
        else{
            if(toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(getApplicationContext(), "Can't Undo. Can Only Undo Last Move.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void afterMove(String moveInput){
        if (!canMove) {
            selectedPiece = null;
            if (pieceMoves != null) {
                pieceMoves.clear();
            }
            resetTiles(gv);

        } else {
            saveMoves.add(moveInput);
            resetTiles(gv);
            updateBoard(gv);
            undoTurn = true;
            selectedPiece = null;

                            if(Chess.blackCheckMate || Chess.whiteCheckMate){
                                if(Chess.winner == 0){
                                    gameOver(R.string.checkmate, R.string.whiteWin);
                                }
                                else{
                                    gameOver(R.string.checkmate, R.string.blackWin);
                                }

                                return;
                            }

                           else if(Chess.stalemate){
                                if(Chess.winner == 0){
                                    gameOver(R.string.stalemate, R.string.whiteWin);
                                }
                                else{
                                    gameOver(R.string.stalemate, R.string.blackWin);
                                }
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

    }

    public void gameOver(int title, int message){
        if(title == R.string.checkmate){
            if(Chess.winner == 0){
                saveMoves.add("checkmate white");
            }
            else {
                saveMoves.add("checkmate black");
            }
        }
        else if(title == R.string.stalemate){
            saveMoves.add("stalemate");
        }
        else if(title == R.string.draw){
            saveMoves.add("draw");
        }
        else if(title == R.string.whiteResign){
            saveMoves.add("resign white");
        }
        else if(title == R.string.blackResign){
            saveMoves.add("resign black");

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                saveGame();
            }
        });
        builder.setNegativeButton(R.string.home, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public void saveGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.save);
        builder.setMessage(R.string.saveMessage);

        final EditText et = new EditText(this);
        builder.setView(et);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String userInput = et.getText().toString();
                if(userInput.equals("")){
                    if(toast != null){
                        toast.cancel();
                    }
                    toast = Toast.makeText(getApplicationContext(), "Enter A Game Name", Toast.LENGTH_SHORT);
                    toast.show();
                    saveGame();

                }
                else {
                    try {
                        File file = getApplicationContext().getFilesDir();
                        file = new File(file,"SavedGames/"+userInput.toLowerCase());
                        if (file.exists()) {
                            if (toast != null) {
                                toast.cancel();
                            }
                            toast = Toast.makeText(getApplicationContext(), "Game With Given Name Already Exists.\nTry Again.", Toast.LENGTH_SHORT);
                            toast.show();
                            saveGame();
                        } else {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);

                            PrintWriter pw = new PrintWriter(fileOutputStream);
                            for (String line : saveMoves) {
                                pw.println(line);
                            }
                            pw.close();
                            if (toast != null) {
                                toast.cancel();
                            }
                            toast = Toast.makeText(getApplicationContext(), "Game Saved.", Toast.LENGTH_SHORT);
                            toast.show();
                            onBackPressed();
                        }

                    } catch (Exception e) {
                        if (toast != null) {
                            toast.cancel();
                        }
                        toast = Toast.makeText(getApplicationContext(), "Game With Given Name Already Exists.\nTry Again.", Toast.LENGTH_SHORT);
                        toast.show();
                        saveGame();
                    }
                }


            }
        });
        builder.setNegativeButton(R.string.home, new DialogInterface.OnClickListener() {
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

    public void resetTiles(GridView gv){
        for(int i = 0; i < 64; i++){
            BoardSquarePiece bsp = (BoardSquarePiece) gv.getChildAt(i).getTag();
            bsp.background.setImageResource(bsp.oldBack);
        }
    }


    static boolean checkInput(String input, ChessBoard cb){
        oldBoard = chessBoard.cloneBoard();
        Chess.preserveState();
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
        else{
            return false;
        }


    }


}