package main;
/*
 * @author: Robert Gabriel Macalintal
 * Student Number: 041096069
 * Algonquin College - CET Computing Science Level 4
 * CST8221 - Java Applications Programming
 * Battleship Project
 */

import controller.Controller;

public class Main {
	
	
	/**
	 * Initializes the Main UI for the Battleship game
	 * 
	 * @param  args - command line arguments
	 */
	public static void main(String[] args) {
		
		Controller gameController = new Controller();
		gameController.start();
		
	}

}
