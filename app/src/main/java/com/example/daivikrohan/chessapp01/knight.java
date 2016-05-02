package com.example.daivikrohan.chessapp01;;

import java.util.ArrayList;
/**
 * Knight extends the Chess Piece class. It finds the list of moves the knight can make and this information is used by the move method to move the piece.
 * @author Daivik Sheth | Rohan Patel
 */

public class knight extends ChessPiece {
	/**
	 * Character of the piece as documented in the instructions. N = Knight. 
	 */
	String name  = "N";
	/**
	 * Constructor. Takes in the x, y coordinates and the color of the piece and sends the info to the chess piece class to initialize the object.
	 * @param color Color of the piece. Black or White.
	 * @param x		The X coordinate of the piece
	 * @param y		The Y coordinate of the piece
	 */

	public knight(int color,int x, int y)
	{
		super(color,x,y);
	}
	/**
	 * listMoves method calculates all the moves the piece can make based on the attributes of piece. 
	 * @param chessBoard 	The Chess board. Used to see what pieces are on the board and based on the movement of the player it utilized the board to see where the piece can move. 
	 * @return ArrayList<String> moves 	- List of all possible moves by the piece
	 * 
	 */
	public ArrayList<String> listMoves(ChessBoard chessBoard){
		ArrayList<String> moves = new ArrayList<String>();
		String rs = null;
		
		//up two and right one
		rs = addMove(this.row-2,this.column+1,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		//up two and left one
		rs = addMove(this.row-2,this.column-1,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		//up one and left two
		rs = addMove(this.row-1,this.column-2,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		//up one and right two
		rs = addMove(this.row-1,this.column+2,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		//down two left one
		rs = addMove(this.row+2,this.column-1,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		//down two right one
		rs = addMove(this.row+2,this.column+1,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		//down one left two
		rs = addMove(this.row+1,this.column-2,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		//down one right two
		rs = addMove(this.row+1,this.column+2,chessBoard);
		if(rs != null){
			moves.add(rs);
		}
		
		return moves;
		
	}
	/**
	 * Based on the color attributes of the piece the to string method will return the name of the piece. If white then wN or bN for black Knight. 
	 * @return String
	 */
	public String toString(){
		if(color == 1){
			return "b"+name+" ";
		}
		else return "w" + name +" ";
	}
	public ChessPiece copy(){
		knight k = new knight(this.color,this.row,this.column);
		k.moved = this.moved;
		k.isKillLocation = this.isKillLocation;
		return k;
	}
}
