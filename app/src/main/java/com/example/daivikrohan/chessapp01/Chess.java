package com.example.daivikrohan.chessapp01;;

import java.util.ArrayList;
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
    static int pturn = 0;
	/**
	 * alternate turns
	 */

	static boolean whiteTurn= true;
    static boolean pwhiteTurn = true;
	/**
	 * set true when second player responds with a draw
	 */
    static boolean askDraw = false;

	static boolean isDraw = false;
    static boolean pisDraw = false;
	/**
	 * resign when a player resigns. Set true when player resigns. s
	 */
	static boolean resign = false;
    static boolean presign = false;
	/**
	 * When one player wins,draws,resign
	 */
	static boolean gameOver = false;
    static boolean pgameOver = false;
	/**
	 * 0 white - 1 black
	 */
	static int winner = -1;
    static int pwinner = -1;
	/**
	 * white in check
	 */
	static boolean whiteCheck = false;
    static boolean pwhiteCheck = false;
	/**
	 * white in checkmate
	 */
	static boolean whiteCheckMate = false;
    static boolean pwhiteCheckMate = false;
	/**
	 * black in check
	 */
	static boolean blackCheck = false;
    static boolean pblackCheck = false;
	/**
	 * black in checkmate
	 */
	static boolean blackCheckMate = false;
    static boolean pblackCheckMate = false;

    /**
	 * Stalemate. True = Draw
	 */
	static boolean stalemate = false;
    static boolean pstalemate = false;

    public static void preserveState(){
        Chess.pisDraw = Chess.isDraw;
        Chess.presign = Chess.resign;
        Chess.pgameOver = Chess.gameOver;
        Chess.pwinner = Chess.winner;
        Chess.pwhiteCheck = Chess.whiteCheck;
        Chess.pwhiteCheckMate = Chess.whiteCheckMate;
        Chess.pblackCheck = Chess.blackCheck;
        Chess.pblackCheckMate = Chess.blackCheckMate;
        Chess.pstalemate = Chess.stalemate;
    }

    public static void oldState(){
        Chess.isDraw = Chess.pisDraw;
        Chess.resign = Chess.presign;
        Chess.gameOver = Chess.pgameOver;
        Chess.winner = Chess.pwinner;
        Chess.whiteCheck = Chess.pwhiteCheck;
        Chess.whiteCheckMate = Chess.pwhiteCheckMate;
        Chess.blackCheck = Chess.pblackCheck;
        Chess.blackCheckMate = Chess.pblackCheckMate;
        Chess.stalemate = Chess.pstalemate;
    }



	public static ChessBoard initChess(){
        Chess.turn = 0;
        Chess.pturn = 0;

        Chess.whiteTurn= true;
        pwhiteTurn = true;

        Chess.askDraw = false;

        Chess.isDraw = false;
        Chess.pisDraw = false;

        Chess.resign = false;
        Chess.presign = false;

        Chess.gameOver = false;
        Chess.pgameOver = false;

        Chess.winner = -1;
        Chess.pwinner = -1;

        Chess.whiteCheck = false;
        Chess.pwhiteCheck = false;

        Chess.whiteCheckMate = false;
        Chess.pwhiteCheckMate = false;

        Chess.blackCheck = false;
        Chess.pblackCheck = false;

        Chess.blackCheckMate = false;
        Chess.pblackCheckMate = false;

        Chess.stalemate = false;
        Chess.pstalemate = false;

        return new ChessBoard();
	}



}
