package com.example.daivikrohan.chessapp01;;

import java.util.ArrayList;
/**
 * Chess Piece class. Contains all the attributes of the chess pieces. 
 * @author Daivik Sheth | Rohan Patel
 * */
public abstract class ChessPiece {
	/**
	 * 1 is black. 0 is white
	 */
	int color; 
	/**
	 * to check if the piece has moved already. 1 means its moved.
	 */
	int moved; 
	/**
	 * Row coordinate
	 */
	int row;
	/**
	 * column coordinate
	 */
	int column;
	/**
	 * flag used in the addMove method to break from loop if a opposite color piece is encountered
	 */
	boolean isKillLocation = false;
	
	/**
	 * Constructor. Initizlies the piece.
	 * @param color of the piece
	 * @param x coordinate of the piece
	 * @param y coordinate of the piece
	 */
	ChessPiece(int color,int x, int y)
	{
		this.color=color;
		this.moved=0;
		this.row = x;
		this.column = y;
	}

	/**
	 * board calls move. move calls listMoves for a list of all possible moves. call isValid to check if king is under attack. if all this is true then within the move method implement the move
	 * @param row  x coordinate of the piece
	 * @param col y coordinate of the piece
	 * @param chessBoard The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 * @return true if valid move false if not
	 */
	public boolean move(int row,int col,ChessBoard chessBoard){
		ArrayList<String> moves = listMoves(chessBoard);
		
		if(moves.contains(""+ row + col) && isValidMove(row, col, chessBoard)){
			//En passant
			if(chessBoard.board[this.row][this.column] != null && chessBoard.board[this.row][this.column] instanceof pawn){    
				if(this.moved == 0){  
					((pawn)this).firstMove = true;
				}
				else{
					((pawn)this).firstMove = false;
				}
				int x = Math.abs(this.row - row);
				if(x == 2){
					((pawn)this).advanceTwo = true;
					if(moved == 0){
						((pawn)this).canPassant = true;
					}
				}
				else{
					((pawn)this).advanceTwo = false;
				}
				
				if(this.column != col){
					if(chessBoard.board[this.row][col] != null && chessBoard.board[this.row][col] instanceof pawn){
						checkPassant(col,chessBoard);
						if(((pawn)chessBoard.board[this.row][col]).advanceTwo && ((pawn)chessBoard.board[this.row][col]).canPassant && ((pawn)chessBoard.board[this.row][col]).firstMove){
							chessBoard.board[this.row][col] = null;
						}
					}
					
				}
			}
			if(chessBoard.board[this.row][this.column] != null && chessBoard.board[this.row][this.column] instanceof king){
				if(Math.abs(this.column - col) == 2){
					//castling
					if(this.column - col < 0){
						if(!isValidMove(this.row,this.column+1,chessBoard)){
							return false;
						}
						//right
						chessBoard.board[this.row][this.column+1] = chessBoard.board[this.row][7];
						chessBoard.board[this.row][this.column+1].row = this.row;
						chessBoard.board[this.row][this.column+1].column = this.column+1;
						chessBoard.board[this.row][this.column+1].moved = 1;
						
						chessBoard.board[this.row][7] = null;
					}
					else{
						if(!isValidMove(this.row,this.column-1,chessBoard)){
							return false;
						}
						//
						chessBoard.board[this.row][this.column-1] = chessBoard.board[this.row][0];
						chessBoard.board[this.row][this.column-1].row = this.row;
						chessBoard.board[this.row][this.column-1].column = this.column-1;
						chessBoard.board[this.row][this.column-1].moved = 1;
						
						chessBoard.board[this.row][0] = null;
					}
				}
			}
			
			chessBoard.board[row][col] = this;
			chessBoard.board[this.row][this.column] = null;
			
			this.row = row;
			this.column = col;
			this.moved = 1;
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof pawn && ( i != this.row && j != this.column) ){
						((pawn)chessBoard.board[i][j]).canPassant = false;
					}
				}
			}
			if(this.color == 0){
				Chess.whiteCheck = false;
			}
			else{
				Chess.blackCheck = false;
			}
			winConditions(chessBoard);
			return true;
		}
		return false;
		
	}
	/**
	 * Checks is en passant happened
	 * @param col - col of the piece
	 * @param chessBoard - The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 */
	public void checkPassant(int col, ChessBoard chessBoard){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof pawn && chessBoard.board[i][j].color != this.color && this.row != i && col != j){
					((pawn)chessBoard.board[i][j]).canPassant = false;
				}
			}
		}
	}
	
	/**
	 * check if check, checkMate, or stalemate is caused
	 * @param chessBoard - The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 */
	
	public void winConditions( ChessBoard chessBoard){
		int krow = 0;
		int kcol = 0;
		ArrayList<String> moves = new ArrayList<String>();
		ArrayList<String> kingMoves = new ArrayList<String>();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof king && chessBoard.board[i][j].color != this.color){
					krow = i;
					kcol = j;
				}
				else if(chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof ChessPiece && chessBoard.board[i][j].color == this.color){
					moves.addAll(chessBoard.board[i][j].listMoves(chessBoard));
				}
			}
		}
		kingMoves = chessBoard.board[krow][kcol].listMoves(chessBoard);  
		
	
		String kingLoc = "" + krow + kcol;
		Boolean kingHasMove = false;

		for(int n = 0; n < kingMoves.size(); n++){
			int r = Integer.parseInt(kingMoves.get(n).substring(0, 1));
			int c = Integer.parseInt(kingMoves.get(n).substring(1, 2));
			if(chessBoard.board[krow][kcol].isValidMove(r, c, chessBoard)){
				kingHasMove = true;
				break;
			}
		}	
		
		if(moves.contains(kingLoc) && !kingHasMove && !enemyCounterAttack(chessBoard) ){
			if(this.color == 0){
				Chess.blackCheckMate = true;
				Chess.winner = 0;
				Chess.gameOver = true;
				return;
			}
			else{
				Chess.whiteCheckMate = true;
				Chess.winner = 1;
				Chess.gameOver = true;
				return;
			}
		}
		else if(moves.contains(kingLoc)){
			if(this.color == 0){
				Chess.blackCheck = true;
				return;
			}
			else{
				Chess.whiteCheck = true;
				return;
			}
		}
		//stalemate
		else if(isStaleMate(chessBoard)){
			Chess.stalemate = true;
			Chess.isDraw = true;
			Chess.gameOver = true;
		}
				
	}
	/**
	 * Checks if the enemy can get out of check. 
	 * @param chessBoard - The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 * @return true if enemy can counterattack else false. 
	 */
	public boolean enemyCounterAttack( ChessBoard chessBoard) {
		Boolean result = false;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if((chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof ChessPiece && chessBoard.board[i][j].color != this.color)){
					ArrayList<String> moves = chessBoard.board[i][j].listMoves(chessBoard);
					for(int n = 0; n < moves.size(); n++){
						int r = Integer.parseInt(moves.get(n).substring(0, 1));
						int c = Integer.parseInt(moves.get(n).substring(1, 2));
						if(chessBoard.board[i][j].isValidMove(r, c, chessBoard)){
							result = true;
						}
					}
					
				}
			}
		}
		return result;
	}
	/**
	 * Checks if it is stalemate
	 * @param chessBoard The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 * @return true if stalemate else false. 
	 */
	public Boolean isStaleMate(ChessBoard chessBoard){
		ArrayList<String> moves = new ArrayList<String>();
		Boolean stale = true;
		int r = 0;
		int c = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof ChessPiece && chessBoard.board[i][j].color != this.color){
					ArrayList<String> test = chessBoard.board[i][j].listMoves(chessBoard);
					moves.addAll(test);
					for(int k = 0; k < test.size(); k++){
						r = Integer.parseInt(test.get(k).substring(0, 1));
						c = Integer.parseInt(test.get(k).substring(1, 2));
						if( chessBoard.board[i][j].isValidMove(r,c,chessBoard)){
							stale = false;
						}
					}
				}
			}
		}
		if(moves.isEmpty()){
			stale = true;
		}
	
		if(this.color == 0 && !Chess.whiteCheck && stale){
			return true;
			
		}
		else if(this.color == 1 && !Chess.blackCheck && stale){
			return true;
		}
		return false;
	}
	/**
	 * checks if the move is valid and king not under attack
	 * @param row - current row coordinate
	 * @param col - current col coordinate
	 * @param chessBoard - The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 * @return true if valid move false otherwise. 
	 */
	
	public boolean isValidMove(int row,int col,ChessBoard chessBoard)  
	{
		ChessPiece myPiece = this;
		ChessPiece other = chessBoard.board[row][col];
		chessBoard.board[row][col] = myPiece;
		chessBoard.board[this.row][this.column] = null;	
		int krow = 0;
		int kcol = 0;
		ArrayList<String> moves = new ArrayList<String>();	
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof king && chessBoard.board[i][j].color == this.color){
					krow = i;
					kcol = j;
				}
				else if(chessBoard.board[i][j] != null && chessBoard.board[i][j] instanceof ChessPiece && chessBoard.board[i][j].color != this.color){
					moves.addAll(chessBoard.board[i][j].listMoves(chessBoard));
				}
			}
		}
		chessBoard.board[row][col] = other;
		chessBoard.board[this.row][this.column] = myPiece;
		if(moves.contains(""+krow+kcol)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Abstract method implemented by the subclasses that lists all possible moves by a piece
	 * @param chessBoard - The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 * @return Arraylist<String> of all the possible moves by a piece. 
	 */
	public abstract ArrayList<String> listMoves(ChessBoard chessBoard);
	/**
	 * Add move - used by the list move class to check if the move is inbounds and valid. 
	 * @param row - x coordinate of the piece
	 * @param col - y coordinate of the piece
	 * @param chessBoard - The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move.
	 * @return String representation of the location where piece is to be moved. 
	 */
	public String addMove(int row, int col, ChessBoard chessBoard)
	{
		if(chessBoard.inBounds(row, col)){
			if(chessBoard.board[row][col] != null && chessBoard.board[row][col] instanceof ChessPiece){
				if(chessBoard.board[row][col].color != this.color){
					isKillLocation = true;
					return (""+(row)+(col));
				}
			}
			else{
				return(""+(row)+(col));
			}
		}
		return null;
	}

	public abstract ChessPiece copy();

	
}
