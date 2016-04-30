package com.example.daivikrohan.chessapp01;;

import java.util.ArrayList;
/**
 * Rook extends the Chess Piece class. It finds the list of moves the Rook can make and this information is used by the move method to move the piece.
 * @author Daivik Sheth | Rohan Patel
 */
public class rook extends ChessPiece {
	/**
	 * Character of the piece as documented in the instructions. R = Rook. 
	 */
	String name  = "R";
	/**
	 * Constructor. Takes in the x, y coordinates and the color of the piece and sends the info to the chess piece class to initialize the object.
	 * @param color Color of the piece. Black or White.
	 * @param x		Thse X coordinate of the piece
	 * @param y		The Y coordinate of the piece
	 */	
	public rook(int color, int x, int y)
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
		String rs = null;
		int rnum= this.row;
		int cnum = this.column;
		isKillLocation = false;
		//up
		while( (rs = addMove(rnum-1,this.column,chessBoard)) != null){
			moves.add(rs);
			if(isKillLocation){
				break;
			}
			rnum--;
		}
		
		//down
		rnum= this.row;
		cnum = this.column;
		isKillLocation = false;
		while( (rs = addMove(rnum+1,this.column,chessBoard)) != null){
			moves.add(rs);
			if(isKillLocation){
				break;
			}
			rnum++;
		}
		
		//left
		rnum= this.row;
		cnum = this.column;
		isKillLocation = false;
		while( (rs = addMove(this.row,cnum-1,chessBoard)) != null){
			moves.add(rs);
			if(isKillLocation){
				break;
			}
			cnum--;
		}
		
		//right
		rnum= this.row;
		cnum = this.column;
		isKillLocation = false;
		while( (rs = addMove(this.row,cnum+1,chessBoard)) != null){
			moves.add(rs);
			if(isKillLocation){
				break;
			}
			cnum++;
		}
		isKillLocation = false;
		return moves;
		
	}
	/**
	 * Based on the color attributes of the piece the to string method will return the name of the piece. If white then wR or bR for black rook. 
	 * @return String
	 */
	public String toString(){
		if(color == 1){
			return "b"+name+" ";
		}
		else return "w" + name +" ";
	}

	public rook copy(){
		rook r = new rook(this.color,this.row,this.column);
		r.moved = this.moved;
		r.isKillLocation = this.isKillLocation;
		return r;
	}
}
