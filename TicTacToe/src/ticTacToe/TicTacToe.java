package ticTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
	
	static ArrayList<Integer> player = new ArrayList<>();
	static ArrayList<Integer> cpu = new ArrayList<>();
	static boolean isPlayers = false;
	static String player1 = "Player1", player2 = "Player2";
	static int scorePlayer1 = 0, scorePlayer2 = 0, gameNumber = 1;

	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Press 1 to play with computer or 2 to play with another player");
		if(sc.nextInt() == 2) {
			isPlayers = true;
		}
		
		System.out.println("Press 1 if you want to use the default name or press 2 if you want to set player name");
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
		
		while(scorePlayer1 != 2 && scorePlayer2 != 2) {
			setGameBoard();
		}
		
		displayResult();

	}
	
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
		
		System.out.println("Score");
		System.out.println(player1 + " - " + scorePlayer1);
		System.out.println(player2 + " - " + scorePlayer2);
		
		printGameBoard(gameboard);
		
		startGame(gameboard);
		
		gameNumber++;
		
	}
	
	public static void startGame(char[][] gameboard) {
		
		while(true) {
			
			if(gameNumber % 2 == 0) {
				player2Turn(gameboard);
			} else {
				player1Turn(gameboard);
			}
			
			String result = checkWinner();
			if(result.length() > 0) {
				System.out.println(result);
				printGameBoard(gameboard);
				break;
			}
			
			if(gameNumber % 2 == 0) {
				player1Turn(gameboard);
			} else {
				player2Turn(gameboard);
			}
			
			result = checkWinner();
			if(result.length() > 0) {
				System.out.println(result);
				printGameBoard(gameboard);
				break;
			}
			
			printGameBoard(gameboard);
		}
		
	}
	
	public static void player1Turn(char[][] gameboard) {
		
		if(gameNumber % 2 == 0) {
			printGameBoard(gameboard);
		}
		System.out.println(player1 + " : Enter the position between 1-9");
		int playerPos = sc.nextInt();
		while(player.contains(playerPos) || cpu.contains(playerPos)) {
			System.out.println("The Position is already taken! Please enter another position");
			playerPos = sc.nextInt();
		}
		player.add(playerPos);
		placePiece(gameboard, playerPos, player1);
		
	}
	
	public static void player2Turn(char[][] gameboard) {
		
		Random rand = new Random();
		int cpuPos = rand.nextInt(9) + 1;
		if(isPlayers) {
			if(gameNumber % 2 == 1) {
				printGameBoard(gameboard);
			}
			System.out.println(player2 + " : Enter the position between 1-9");
			cpuPos = sc.nextInt();
			while(player.contains(cpuPos) || cpu.contains(cpuPos)) {
				System.out.println("The Position is already taken! Please enter another position");
				cpuPos = sc.nextInt();
			}
		} else {
			while(player.contains(cpuPos) || cpu.contains(cpuPos)) {
				cpuPos = rand.nextInt(9) + 1;
			}
		}
		cpu.add(cpuPos);
		placePiece(gameboard, cpuPos, player2);
		
	}
	
	public static void printGameBoard(char[][] gameboard) {
		
		for(char[] row : gameboard) {
			for(char e : row) {
				System.out.print(e);
			}
			System.out.println();
		}
		
	}
	
	public static void placePiece(char[][] gameboard, int pos, String user) {
		
		char symbol = ' ';
		
		if(user.equals(player1)) {
			symbol = 'X';
		} else if(user.equals(player2)) {
			symbol = 'O';
		}
		
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
	
	public static String checkWinner() {
		
		List<Integer> topRow = Arrays.asList(1, 2, 3);
		List<Integer> midRow = Arrays.asList(4, 5, 6);
		List<Integer> botRow = Arrays.asList(7, 8, 9);
		List<Integer> leftCol = Arrays.asList(1, 4, 7);
		List<Integer> midCol = Arrays.asList(2, 5, 8);
		List<Integer> rightCol = Arrays.asList(3, 6, 9);
		List<Integer> cross1 = Arrays.asList(1, 5, 9);
		List<Integer> cross2 = Arrays.asList(3, 5, 7);
		
		List<List> winning = new ArrayList<>();
		winning.add(topRow);
		winning.add(midRow);
		winning.add(botRow);
		winning.add(leftCol);
		winning.add(midCol);
		winning.add(rightCol);
		winning.add(cross1);
		winning.add(cross2);
		
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
		if(player.size() + cpu.size() == 9) {
			return "CAT";
		}
		
		return "";
	}
	
	public static void displayResult() {
		
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
