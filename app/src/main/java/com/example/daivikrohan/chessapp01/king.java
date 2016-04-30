package com.example.daivikrohan.chessapp01;;

import java.util.ArrayList;
/**
 * King extends the Chess Piece class. It finds the list of moves the king can make and this information is used by the move method to move the piece.
 * @author Daivik Sheth | Rohan Patel
 */

public class king extends ChessPiece{
	/**
	 * Character of the piece as documented in the instructions. K = King. 
	 */
	String name  = "K";
	/**
	 * Constructor. Takes in the x, y coordinates and the color of the piece and sends the info to the chess piece class to initialize the object.
	 * @param color Color of the piece. Black or White.
	 * @param x		The X coordinate of the piece
	 * @param y		The Y coordinate of the piece
	 */
	public king(int color,int x, int y)
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
		rs = addMove(this.row-1,this.column,chessBoard); //up one
		if(rs != null){
			moves.add(rs);
		}
		rs = addMove(this.row-1,this.column-1,chessBoard); //diagonal left
		if(rs != null){
			moves.add(rs);
		}
		rs = addMove(this.row-1,this.column+1,chessBoard); //diagonal right
		if(rs != null){
			moves.add(rs);
		}
		rs = addMove(this.row,this.column+1,chessBoard); //right
		if(rs != null){
			moves.add(rs);
		}
		rs = addMove(this.row,this.column-1,chessBoard); //left
		if(rs != null){
			moves.add(rs);
		}
		rs = addMove(this.row+1,this.column,chessBoard); //down one
		if(rs != null){
			moves.add(rs);
		}
		rs = addMove(this.row+1,this.column-1,chessBoard); //diagonal bottom left
		if(rs != null){
			moves.add(rs);
		}
		rs = addMove(this.row+1,this.column+1,chessBoard); //diagonal bottom right
		if(rs != null){
			moves.add(rs);
		}
		if(moved==0)//check white check or black
		{
			if(Chess.whiteTurn && !Chess.whiteCheck )
			{
				if((chessBoard.board[7][0] != null && chessBoard.board[7][0] instanceof rook) && chessBoard.board[7][0].moved==0 )
				{
					if(chessBoard.board[7][1]==null && chessBoard.board[7][2]==null && chessBoard.board[7][3]==null) 
					{
						rs = addMove(this.row,this.column-1,chessBoard); 
						if(rs != null){
						moves.add(rs);
						}
						rs = addMove(this.row,this.column-2,chessBoard); 
						if(rs != null){
							moves.add(rs);
						}

					}
				}
				if((chessBoard.board[7][7] != null && chessBoard.board[7][7] instanceof rook) && chessBoard.board[7][7].moved==0 )
				{
					if(chessBoard.board[7][5]==null && chessBoard.board[7][6]==null)
					{
						rs = addMove(this.row,this.column+1,chessBoard); 
						if(rs != null){
						moves.add(rs);
						}
						rs = addMove(this.row,this.column+2,chessBoard); 
						if(rs != null){
							moves.add(rs);
						}
					}
				}
			}
			if(!Chess.whiteTurn && !Chess.blackCheck )
			{
				if( (chessBoard.board[0][0] != null && chessBoard.board[0][0] instanceof rook) && chessBoard.board[0][7].moved==0)
				{
					if(chessBoard.board[0][1]==null && chessBoard.board[0][2]==null && chessBoard.board[0][3]==null)
					{
						rs = addMove(this.row,this.column-1,chessBoard); 
						if(rs != null){
						moves.add(rs);
						}
						rs = addMove(this.row,this.column-2,chessBoard); 
						if(rs != null){
							moves.add(rs);
						}
					}
				}
				if((chessBoard.board[0][7] != null && chessBoard.board[0][7] instanceof rook) && chessBoard.board[0][7].moved==0)
				{
					if(chessBoard.board[0][5]==null && chessBoard.board[0][6]==null)
					{
						rs = addMove(this.row,this.column+1,chessBoard); 
						if(rs != null){
						moves.add(rs);
						}
						rs = addMove(this.row,this.column+2,chessBoard); 
						if(rs != null){
							moves.add(rs);
						}
					}
				}
			}	
		}
		return moves;
		
	}
	/**
	 * Based on the color attributes of the piece the to string method will return the name of the piece. If white then wK or bK for black king. 
	 * @return String
	 */
	public String toString(){
		if(color == 1){
			return "b"+name+" ";
		}
		else return "w" + name +" ";
	}

	public king copy(){
		king k = new king(this.color,this.row,this.column);
		k.moved = this.moved;
		k.isKillLocation = this.isKillLocation;
		return k;
	}
}
