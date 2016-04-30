package com.example.daivikrohan.chessapp01;;
/**
 * ChessBoard where chess pieces are placed.  
 * @author Daivik Sheth | Rohan Patel
 */
public class ChessBoard {
	/**
	 * Board = 2d array. 
	 */
	ChessPiece[][] board = new ChessPiece[8][8];
	/**
	 * 2d array representation of the chessboard. Initizlies all the pices in its place.
	 */
	public ChessBoard(){	
		board[0][0] = new rook(1,0,0);
		board[0][1] = new knight(1,0,1);
		board[0][2] = new bishop(1,0,2);
		board[0][3] = new queen(1,0,3);
		board[0][4] = new king(1,0,4);
		board[0][5] = new bishop(1,0,5);
		board[0][6] = new knight(1,0,6);
		board[0][7] = new rook(1,0,7);
	
		for(int i = 0; i < 8; i++){
			board[1][i] = new pawn(1,1,i);
			board[6][i] = new pawn(0,6,i);
		}
	
		board[7][0] = new rook(0,7,0);
		board[7][1] = new knight(0,7,1);
		board[7][2] = new bishop(0,7,2);
		board[7][3] = new queen(0,7,3);
		board[7][4] = new king(0,7,4);
		board[7][5] = new bishop(0,7,5);
		board[7][6] = new knight(0,7,6);
		board[7][7] = new rook(0,7,7);
	
	}
	/**
	 * Prints the chess board.
	 * 	 
	 * */
	void printBoard(){	
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(board[i][j] != null && board[i][j] instanceof ChessPiece){
					System.out.print(board[i][j].toString());
				}
				else{
					if( ((i+1)%2==0 && j%2 == 0) || ((i+1)%2 != 0 && j%2 != 0) ){
						System.out.print("   ");
					}
					else{
						System.out.print("## ");
					}
				}
				if(j == 7){
					System.out.println(8-i);
				}	
			}
		}
		System.out.println(" a  b  c  d  e  f  g  h\n");
	}
	
	/**
	 * 	Convert Pos takes a string and returns a position. 
	 * @param s takes in user input 
	 * @return the array of the location
	 */
	
	static String convertPos(String s){
		char posChar1 = s.toLowerCase().charAt(0);
		String int1 = s.substring(1, 2);
		int posInt1 = Integer.parseInt(int1);
		posInt1 = 8-posInt1;
		
		char posChar2 = s.toLowerCase().charAt(3);
		String int2 = s.substring(4, 5);
		int posInt2 = Integer.parseInt(int2);
		posInt2 = 8-posInt2;
		
		String pos1 = charToInt(posChar1)+posInt1;
		String pos2 = charToInt(posChar2)+posInt2;
		
		return pos1 + " " + pos2;
		
	}
	/*
	 * @param c file of the chess piece
	 * @return string the row in the array the file reporesents
	 */
	static String charToInt(char c){
		String s = "";
		switch(c){
			case 'a': s = "0";
					  break;
			case 'b': s = "1";
					  break;
			case 'c': s = "2";
					  break;
			case 'd': s = "3";
					  break;
			case 'e': s = "4";
					  break;
			case 'f': s = "5";
					  break;
			case 'g': s = "6";
					 break;
			case 'h': s = "7";
					  break;
			default: break;		
		}
		return s;
	}
	/**
	 * Checks if a given row and col is in the bounds of the array. 
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return boolean true if inbound false if not. 
	 */
	boolean inBounds(int row, int col){
		if(row < 0 || row > 7){
			return false;
		}
		if(col < 0 || col > 7 ){
			return false;
		}
		return true;
	}
	
}
