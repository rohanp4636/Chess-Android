package com.example.daivikrohan.chessapp01;;

import java.util.ArrayList;
/**
 * Pawn extends the Chess Piece class. It finds the list of moves the pawn can make and this information is used by the move method to move the piece.
 * @author Daivik Sheth | Rohan Patel
 */
public class pawn extends ChessPiece {
	/**
	 * Character of the piece as documented in the instructions. P = Pawn. 
	 */
	String name  = "p";
	/**
	 *If Pawn already moved two spaces on the first try, the flag is set true  
	 */
	Boolean advanceTwo = false;
	/**
	 * Characters first move.  
	 */
	Boolean firstMove = false;
	/**
	 * This flag is set true if the opponent can capture this pawn en passant.
	 */
	Boolean canPassant = false;
	/**
	 * Constructor. Takes in the x, y coordinates and the color of the piece and sends the info to the chess piece class to initialize the object.
	 * @param color Color of the piece. Black or White.
	 * @param x		The X coordinate of the piece
	 * @param y		The Y coordinate of the piece
	 */
	public pawn(int color,int x, int y)
	{
		super(color,x,y);
	}
	/**
	 * listMoves method calculates all the moves the piece can make based on the attributes of piece. 
	 * @param chessBoard 	The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move. 
	 * @return ArrayList<String> moves 	- List of all possible moves by the piece
	 * 
	 */	

	public ArrayList<String> listMoves(ChessBoard chessBoard) 
	{
		ArrayList<String> moves= new ArrayList<String>();
		String rs=null;
		if(this.color == 0){
			if(chessBoard.inBounds(this.row-1, this.column) && chessBoard.board[this.row-1][this.column] == null  ){
				rs = addMove(this.row-1,this.column,chessBoard); //up one
				if(rs != null){
					moves.add(rs);
				}
			}

			if((chessBoard.inBounds(this.row-1, this.column-1) && chessBoard.board[this.row-1][this.column-1] != null) || (chessBoard.inBounds(this.row, this.column-1) && chessBoard.board[this.row][this.column-1] != null && chessBoard.board[this.row][this.column-1] instanceof pawn && chessBoard.board[this.row][this.column-1].color != this.color && ((pawn)chessBoard.board[this.row][this.column-1]).canPassant) ){
				rs = addMove(this.row-1,this.column-1,chessBoard); //diagonal left
				if(rs != null){
					moves.add(rs);
				}
			}
			if(( chessBoard.inBounds(this.row-1, this.column+1) && chessBoard.board[this.row-1][this.column+1] != null  )|| (chessBoard.inBounds(this.row, this.column+1) &&  chessBoard.board[this.row][this.column+1] != null && chessBoard.board[this.row][this.column+1] instanceof pawn && chessBoard.board[this.row][this.column+1].color != this.color && ((pawn)chessBoard.board[this.row][this.column+1]).canPassant) ){
				rs = addMove(this.row-1,this.column+1,chessBoard); //diagonal right
				if(rs != null){
					moves.add(rs);
				}
			}
			
			if(moved == 0){
				if(chessBoard.inBounds(this.row-2, this.column) && chessBoard.board[this.row-2][this.column] == null  ){
					rs = addMove(this.row-2,this.column,chessBoard); //up two
					if(rs != null){
						moves.add(rs);
					}
				}
			}
		}
		else{
			if(chessBoard.inBounds(this.row+1, this.column) && chessBoard.board[this.row+1][this.column] == null  ){
				rs = addMove(this.row+1,this.column,chessBoard); //up one
				if(rs != null){
					moves.add(rs);
				}
			}
			
			if( ( chessBoard.inBounds(this.row+1, this.column-1) && chessBoard.board[this.row+1][this.column-1] != null  ) || (chessBoard.inBounds(this.row, this.column-1) &&  chessBoard.board[this.row][this.column-1] != null && chessBoard.board[this.row][this.column-1] instanceof pawn && chessBoard.board[this.row][this.column-1].color != this.color && ((pawn)chessBoard.board[this.row][this.column-1]).canPassant) ){
				rs = addMove(this.row+1,this.column-1,chessBoard); //diagonal left
				if(rs != null){
					moves.add(rs);
				}
			}
		
			if(( chessBoard.inBounds(this.row+1, this.column+1) && chessBoard.board[this.row+1][this.column+1] != null ) || (chessBoard.inBounds(this.row, this.column+1) && chessBoard.board[this.row][this.column+1] != null && chessBoard.board[this.row][this.column+1] instanceof pawn && chessBoard.board[this.row][this.column+1].color != this.color && ((pawn)chessBoard.board[this.row][this.column+1]).canPassant) ){
				rs = addMove(this.row+1,this.column+1,chessBoard); //diagonal right
				if(rs != null){
					moves.add(rs);
				}
			}
		
			if(moved == 0){
				if(chessBoard.inBounds(this.row+2, this.column) && chessBoard.board[this.row+2][this.column] == null && chessBoard.inBounds(this.row+1, this.column) && chessBoard.board[this.row+1][this.column] == null  ){
					rs = addMove(this.row+2,this.column,chessBoard); //up two
					if(rs != null){
						moves.add(rs);
					}
				}
			}
		}
		return moves;
	}
	/**
	 * If the pawn advances all the way to other side of the board it can promote itself to rook, bishop, knight or queen.  
	 * @param row - X coordinate user asked to move the piece
	 * @param col - Y coordinate user asked to move the piece
	 * @param chessBoard - ChessBoard to access the board to see what players are at what locations. 		
	 * @return boolean
	 */
	public boolean promote(int row, int col, ChessBoard chessBoard, char piece){
		ArrayList<String> moves = listMoves(chessBoard);
		int r = 0;
		if(Chess.whiteTurn){
			r = this.row-1;
		}
		else{
			r = this.row+1;
		}
		
		if(moves.contains("" + row + col) && row == r ) {
			Boolean result = this.move(row, col, chessBoard);
			if(!result){
				return false;
			}
			if(piece == 'q' || piece == 'Q'){
				chessBoard.board[row][col] = new queen(this.color,row,col);
			}
			else if(piece == 'r' || piece == 'R'){
				chessBoard.board[row][col] = new rook(this.color,row,col);
			}
			else if(piece == 'b' || piece == 'B'){
				chessBoard.board[row][col] = new bishop(this.color,row,col);
			}
			else if(piece == 'n' || piece == 'N'){
				chessBoard.board[row][col] = new knight(this.color,row,col);
			}
			this.winConditions(chessBoard);
			return true;
		}
		return false;
	}
	/**
	 * Based on the color attributes of the piece the to string method will return the name of the piece. If white then wP or bP for black Pawn. 
	 * @return String
	 */
	public String toString(){
		if(color == 1){
			return "b"+name+" ";
		}
		else return "w" + name +" ";
	}

	public pawn copy(){
		pawn p = new pawn(this.color,this.row,this.column);
		p.moved = this.moved;
		p.isKillLocation = this.isKillLocation;
		p.firstMove = this.firstMove;
		p.advanceTwo = this.advanceTwo;
		p.canPassant = this.canPassant;
		return p;
	}
}

