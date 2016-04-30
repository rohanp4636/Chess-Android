package com.example.daivikrohan.chessapp01;;

import java.util.Scanner;
/**
 * Chess I/O or control flow of the program. 
 * @author Daivik Sheth | Rohan Patel
 */
public class Chess {
	/**
	 * alternate turns. 0 is white. 1 is Black
	 */
	static int turn = 0;
	/**
	 * alternate turns
	 */
	static boolean whiteTurn= true; 
	/**
	 * Set true when first player asks for a draw
	 */
	static boolean askDraw = false;  
	/**
	 * set true when second player responds with a draw
	 */
	static boolean isDraw = false; 
	/**
	 * resign when a player resigns. Set true when player resigns. s
	 */
	static boolean resign = false; 
	/**
	 * When one player wins,draws,resign
	 */
	static boolean gameOver = false; 
	/**
	 * 0 white - 1 black
	 */
	static int winner = -1; 
	/**
	 * white in check
	 */
	static boolean whiteCheck = false;   
	/**
	 * white in checkmate
	 */
	static boolean whiteCheckMate = false; 
	/**
	 * black in check
	 */
	static boolean blackCheck = false;  
	/**
	 * black in checkmate
	 */
	static boolean blackCheckMate = false;  
	/**
	 * Stalemate. True = Draw
	 */
	static boolean stalemate = false;
	/**
	 * Checks if the input matches the appropriate format. 
	 * @param input takes in the user input
	 * @param cb = access board, list of players at each box.
	 * @return true if valid input. False otherwise. 
	 */
	
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
			askDraw = false;
			if(cb.board[rank1][file1] != null && cb.board[rank1][file1] instanceof pawn && (rank2 == 7 || rank2 == 0) ){
				return false;
			}
			if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == turn){
				return cb.board[rank1][file1].move(rank2, file2,cb);
			}
	
			return false;  //move, check promotion, castling...
		}
		else if(input.matches("[a-hA-H][1-8] [a-hA-H][1-8] [qrbnQRBN]")){
			askDraw = false;
			conv = ChessBoard.convertPos(input);
			file1 = Integer.parseInt(conv.substring(0, 1));
			rank1 = Integer.parseInt(conv.substring(1, 2));
			file2 = Integer.parseInt(conv.substring(3, 4));
			rank2 = Integer.parseInt(conv.substring(4, 5));
			char c = input.charAt(6);
			askDraw = false;
			if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == turn){
					return ((pawn)(cb.board[rank1][file1])).promote(rank2, file2,cb, c);
			}
			return false;  //promotion
		}
		else if(input.matches("[a-hA-H][1-8] [a-hA-H][1-8] [qrbnQRBN] [dD][rR][aA][wW][?]") || input.matches("[a-hA-H][1-8] [a-hA-H][1-8] [dD][rR][aA][wW][?]") ){
			//ask second user for draw
			askDraw = true;
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
				if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == turn){
					return cb.board[rank1][file1].move(rank2, file2,cb);
				}
			}
			else{
				if(cb.board[rank1][file1] != null && cb.board[rank1][file1].color == turn && cb.board[rank1][file1] instanceof pawn){
					return ((pawn)(cb.board[rank1][file1])).promote(rank2, file2,cb, c);
				}
			}
			
			return false;
		}
		else if(input.equalsIgnoreCase("resign")){
			resign = true;
			System.out.println("");
			if(whiteTurn){
				winner = 1;
				
			}
			else{
				winner = 0;
			}
			gameOver = true;
			return true;
		}
		else if(input.equalsIgnoreCase("draw")){
			if(askDraw){
				isDraw = true;
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
	public static ChessBoard initChess(){
        return new ChessBoard();
	}

	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String whitePrompt = "White's move: ";
		String blackPrompt = "Black's move: ";
		ChessBoard chessBoard = new ChessBoard();
		for(int i = 0 ; i < 6; i ++){
			for(int j = 0; j < 8; j++){
				chessBoard.board[i][j] = null;
			}
		}
		chessBoard.board[7][3]  = null;
		chessBoard.board[6][7]  = null;
		chessBoard.board[6][4]  = null;
		
		chessBoard.board[0][5]  = new bishop(1,0,5);
		chessBoard.board[0][6]  = new knight(1,0,6);
		chessBoard.board[0][7]  = new rook(1,0,7);
		chessBoard.board[1][4]  = new pawn(1,1,4);
		chessBoard.board[1][6]  = new pawn(1,1,6);
		chessBoard.board[1][7]  = new queen(1,1,7);
		chessBoard.board[2][3]  = new queen(0,2,3);
		chessBoard.board[2][5]  = new pawn(1,2,5);
		chessBoard.board[2][5].moved = 1;

		chessBoard.board[2][6]  = new king(1,2,6);
		chessBoard.board[2][7]  = new rook(1,2,7);
		chessBoard.board[3][7]  = new pawn(1,3,7);
		chessBoard.board[4][7]  = new pawn(0,4,7);
		chessBoard.board[5][3]  = new pawn(0,5,3);
		
		
		
		chessBoard.printBoard();
		Scanner scanner = new Scanner(System.in);
		String userInput = "";
		while(!gameOver){
			
			if(whiteTurn){
				System.out.print(whitePrompt);
			}
			else{
				System.out.print(blackPrompt);
			}
			userInput = scanner.nextLine();
			//System.out.println("");
			//System.out.println(userInput+"\n");
			boolean input = checkInput(userInput,chessBoard);
			if(!input){
				System.out.println("\nIllegal move, try again\n");
			}
			else{
				if(blackCheckMate || whiteCheckMate){
					System.out.println("");
					chessBoard.printBoard();
					System.out.println("Checkmate\n");
				}
				else if((blackCheck || whiteCheck) && !isDraw ){
					System.out.println("");
					chessBoard.printBoard();
					System.out.println("Check\n");
				}
				else if(stalemate){
					System.out.println("");
					chessBoard.printBoard();
					System.out.println("Stalemate");
				}
				if(gameOver || isDraw){
					break;
				}
				whiteTurn = !whiteTurn;
				if(whiteTurn){
					turn = 0;
				}
				else{
					turn = 1;
				}
				if(!(blackCheck || whiteCheck)){
					System.out.println("");
					chessBoard.printBoard();
				}
			}
		}
		if(winner == 1){
			System.out.println("Black wins");

		}
		else if(winner == 0){
			System.out.println("White wins");
		}
		else{
			System.out.println("\nDraw");

		}
		
		scanner.close();
	}
	
	*/

}
