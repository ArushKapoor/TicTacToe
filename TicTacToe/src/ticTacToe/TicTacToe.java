package ticTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This is a basic form of the popular game TicTacToe. You can play
 * with both a computer as well as your friend.
 * 
 * @author arushkapoor
 *
 */

public class TicTacToe {
	
	/**
	 * ArrayList to store all the positions entered by the player.
	 */
	static ArrayList<Integer> player = new ArrayList<>();
	
	/**
	 * ArrayList to store all the positions entered by the computer 
	 * or the second player.
	 */
	static ArrayList<Integer> cpu = new ArrayList<>();
	
	/**
	 * Boolean to store whether two players are playing or not.
	 */
	static boolean isPlayers = false;
	
	/**
	 * String to store the name of the players.
	 */
	static String player1 = "Player1", player2 = "Player2";
	
	/**
	 * Integers to store the number of wins by player1 and by computer
	 * or player2.
	 */
	static int scorePlayer1 = 0, scorePlayer2 = 0;
	
	/**
	 * Integer to store the current game number.
	 */
	static int gameNumber = 1;

	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		/**
		 * Asking the user whether they want to play with the computer or 
		 * another player and storing the result in isPlayers.
		 */
		System.out.println("Press 1 to play with computer or 2 to play with"
				+ " another player");
		if(sc.nextInt() == 2) {
			isPlayers = true;
		}
		
		/**
		 * Asking the user whether they want to use the default names or set
		 * their own names.
		 */
		System.out.println("Press 1 if you want to use the default name or "
				+ "press 2 if you want to set player name");
		if(sc.nextInt() == 2) {
			sc.nextLine();
			if(isPlayers) {
				System.out.println("Enter Player1 Name: ");
				player1 = sc.nextLine();
				System.out.println("Enter Player2 Name: ");
				player2 = sc.nextLine();
			} else {
				System.out.println("Enter Player Name: ");
				player1 = sc.nextLine();
				player2 = "CPU";
			}
		} else {
			if(!isPlayers) {
				player1 = "Player";
				player2 = "CPU";
			}
		}
		
		/**
		 * To run the game while someone wins 2 rounds out of 3.
		 */
		while(scorePlayer1 != 2 && scorePlayer2 != 2) {
			setGameBoard();
		}
		
		/**
		 * To display Winner of the overall game.
		 */
		displayResult();

	}
	
	/**
	 * This function sets all the variables to their default value
	 * and displays the score so far.
	 */
	public static void setGameBoard() {
		
		player.clear();
		cpu.clear();
		
		char gameboard[][] = {
				{' ', '|', ' ', '|', ' '},
				{'-', '+', '-', '+', '-'},
				{' ', '|', ' ', '|', ' '},
				{'-', '+', '-', '+', '-'},
				{' ', '|', ' ', '|', ' '},
		};
		
		/**
		 * Prints the score so far in the game.
		 */
		System.out.println("Score");
		System.out.println(player1 + " - " + scorePlayer1);
		System.out.println(player2 + " - " + scorePlayer2);
		
		/**
		 * Prints the current gameboard.
		 */
		printGameBoard(gameboard);
		
		/**
		 * Starts the round.
		 */
		startGame(gameboard);
		
		/**
		 * After the round is finished increments the value of gameNumber.
		 */
		gameNumber++;
		
	}
	
	/**
	 * This function is the main part where the round begins. Calls the
	 * player whose turn it is to play. After each turn it checks whether
	 * there is a winner or not. If the match has ended it exits the 
	 * infinite loop.
	 * 
	 * @param gameboard Stores the current state of the game board.
	 */
	public static void startGame(char[][] gameboard) {
		
		while(true) {
			
			/**
			 * It rotates which player will play the first turn every
			 * round.
			 */
			if(gameNumber % 2 == 0) {
				player2Turn(gameboard);
			} else {
				player1Turn(gameboard);
			}
			
			/**
			 * Checks whether the match has ended or not.
			 */
			String result = checkWinner();
			if(result.length() > 0) {
				System.out.println(result);
				printGameBoard(gameboard);
				break;
			}
			
			/**
			 * The second player will play his turn.
			 */
			if(gameNumber % 2 == 0) {
				player1Turn(gameboard);
			} else {
				player2Turn(gameboard);
			}
			
			/**
			 * Again checks if the match has ended or not.
			 */
			result = checkWinner();
			if(result.length() > 0) {
				System.out.println(result);
				printGameBoard(gameboard);
				break;
			}
			
			/**
			 * Prints the gameboard after every turn.
			 */
			printGameBoard(gameboard);
		}
		
	}
	
	/**
	 * This function is used to tell player1 that its his turn.
	 * 
	 * @param gameboard Stores the current state of the game board.
	 */
	public static void player1Turn(char[][] gameboard) {
		
		/**
		 * Prints the gameboard once if the first turn was not his.
		 */
		if(gameNumber % 2 == 0) {
			printGameBoard(gameboard);
		}
		
		/**
		 * Asks the user to enter the position at which he would like
		 * to put his 'X'.
		 */
		System.out.println(player1 + " : Enter the position between 1-9");
		int playerPos = sc.nextInt();
		while(player.contains(playerPos) || cpu.contains(playerPos)) {
			System.out.println("The Position is already taken! Please "
					+ "enter another position");
			playerPos = sc.nextInt();
		}
		
		/**
		 * Adds the position entered by the player to the player list.
		 */
		player.add(playerPos);
		
		/**
		 * Places the piece on the board.
		 */
		placePiece(gameboard, playerPos, player1);
		
	}
	
	/**
	 * This function is used to tell player2 or computer that
	 * its his turn to play.
	 * 
	 * @param gameboard Stores the current state of the game board.
	 */
	public static void player2Turn(char[][] gameboard) {
		
		/**
		 * Takes a random position and places its 'O'.
		 */
		Random rand = new Random();
		int cpuPos = rand.nextInt(9) + 1;
		
		/**
		 * If the second player is not computer then the player is asked
		 * to enter the position at which he would like to add his 'O'.
		 */
		if(isPlayers) {
			if(gameNumber % 2 == 1) {
				printGameBoard(gameboard);
			}
			System.out.println(player2 + " : Enter the position between 1-9");
			cpuPos = sc.nextInt();
			while(player.contains(cpuPos) || cpu.contains(cpuPos)) {
				System.out.println("The Position is already taken! Please "
						+ "enter another position");
				cpuPos = sc.nextInt();
			}
		} else {
			while(player.contains(cpuPos) || cpu.contains(cpuPos)) {
				cpuPos = rand.nextInt(9) + 1;
			}
		}
		
		/**
		 * Adds the position to the cpu list.
		 */
		cpu.add(cpuPos);
		
		/**
		 * Places the piece on the board.
		 */
		placePiece(gameboard, cpuPos, player2);
		
	}
	
	/**
	 * This method is used to print the current state of the game board.
	 * 
	 * @param gameboard Stores the current state of the game board.
	 */
	public static void printGameBoard(char[][] gameboard) {
		
		/**
		 * Prints the game board.
		 */
		for(char[] row : gameboard) {
			for(char e : row) {
				System.out.print(e);
			}
			System.out.println();
		}
		
	}
	
	/**
	 * Places the piece on the board according to the position given to it.
	 * 
	 * @param gameboard Stores the current state of the game board.
	 * @param pos Stores the position at which the piece is to placed.
	 * @param user Stores the player for which the piece is placed.
	 */
	public static void placePiece(char[][] gameboard, int pos, String user) {
		
		char symbol = ' ';
		
		/**
		 * Chooses the piece to be placed according to the user.
		 */
		if(user.equals(player1)) {
			symbol = 'X';
		} else if(user.equals(player2)) {
			symbol = 'O';
		}
		
		/**
		 * Places the piece on the board according to the position given
		 * to it.
		 */
		switch(pos) {
			case 1:
				gameboard[0][0] = symbol;
				break;
			case 2:
				gameboard[0][2] = symbol;
				break;
			case 3:
				gameboard[0][4] = symbol;
				break;
			case 4:
				gameboard[2][0] = symbol;
				break;
			case 5:
				gameboard[2][2] = symbol;
				break;
			case 6:
				gameboard[2][4] = symbol;
				break;
			case 7:
				gameboard[4][0] = symbol;
				break;
			case 8:
				gameboard[4][2] = symbol;
				break;
			case 9:
				gameboard[4][4] = symbol;
				break;
		}
		
	}
	
	/**
	 * Checks if the game has ended and if there is a winner or its
	 * a tie.
	 * 
	 * @return the status of the game.
	 */
	public static String checkWinner() {
		
		/**
		 * Stores all the winning conditions in lists.
		 */
		List<Integer> topRow = Arrays.asList(1, 2, 3);
		List<Integer> midRow = Arrays.asList(4, 5, 6);
		List<Integer> botRow = Arrays.asList(7, 8, 9);
		List<Integer> leftCol = Arrays.asList(1, 4, 7);
		List<Integer> midCol = Arrays.asList(2, 5, 8);
		List<Integer> rightCol = Arrays.asList(3, 6, 9);
		List<Integer> cross1 = Arrays.asList(1, 5, 9);
		List<Integer> cross2 = Arrays.asList(3, 5, 7);
		
		/**
		 * Store all the winning conditions in a single List.
		 */
		List<List> winning = new ArrayList<>();
		winning.add(topRow);
		winning.add(midRow);
		winning.add(botRow);
		winning.add(leftCol);
		winning.add(midCol);
		winning.add(rightCol);
		winning.add(cross1);
		winning.add(cross2);
		
		/**
		 * Checks the winning conditions for each player and returns 
		 * the status.
		 */
		for(List l: winning) {
			if(player.containsAll(l)) {
				scorePlayer1++;
				return "Congratulations " + player1 + "! You Won";
			} else if(cpu.containsAll(l)) {
				scorePlayer2++;
				if(isPlayers) {
					return "Congratulations " + player2 + "! You Won";
				}
				return "CPU won! Sorry :(";
			}
		}
		
		/**
		 * Returns CAT if its a draw.
		 */
		if(player.size() + cpu.size() == 9) {
			return "CAT";
		}
		
		/**
		 * Returns an empty string if the match has not yet ended.
		 */
		return "";
	}
	
	/**
	 * Displays the final result on the screen.
	 */
	public static void displayResult() {
		
		/**
		 * Checks which player has won the overall game.
		 */
		if(scorePlayer1 == 2) {
			System.out.println("CONGRATULATIONS " + player1 + " YOU WON IT ALL!!");
		} else {
			if(isPlayers) {
				System.out.println("CONGRATULATIONS " + player2 + " YOU WON IT ALL!!");
			} else {
				System.out.println("SORRY YOU LOST :(");
			}
		}
		
	}

}
