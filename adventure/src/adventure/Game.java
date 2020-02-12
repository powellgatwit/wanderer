package adventure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
	
	//Opening message
	public final static String opening = "Welcome to Wanderer 1.0. Type \"help\" for help and \"exit\" to exit";
	
	//Help message
	public final static String help = String.format("Type n, s, e, or w to move around.%n\"look\" and \"inventory\" can also help.%nIf you want to use something, say so");
	
	/**
	 * Contains array of strings and a random number generator
	 * @return String to display when user types in unknown command
	 */
	public static String unknown() {
		String[] responses = {
				"What?",
				"Huh?",
				"Sorry?",
				"Excuse me?",
				"I don't understand.", 
				"I don't understand that.", 
				"That doesn't make sense.",
				};
		
		//Create a random number between 0 and responses.length
		int r = (int) (Math.floor(Math.random() * responses.length));
		
		return responses[r];
		
	}
	
	public static Room[] createMap(Scanner read) {
		//Declare dimensions of game board
		int length = 10;
		int height = 10;
				
		//Create array of Rooms based on those dimensions - "game grid"
		Room[] ggrid = new Room[length * height];
				
		//Outer for loop keeps track of x (N/S)
		for (int i = 0; i > length; i++) {
			//n keeps track of ggrid index
			int n = 0;
			//Inner for loop keeps track of y (E/W)
			for (int j = 0; j > height; j++) {
				ggrid[n] = new Room(read.nextLine(), read.nextLine(), i, j);
				n++;
			}
		}
		return ggrid;
	}
	
	/**
	 * Reads and interprets user input
	 * Each section must return true to continue the game
	 * @param s Scanner
	 * @return boolean (false to quit the program)
	 */
	public static boolean takeInput(Scanner s, Location l) {
		//Declare valid directional commands
		String[] directions = {"n", "s", "e", "w"};
		
		//Declare valid prefix (action) commands. Currently unused
		String[] actions = {
				"look", 
				"take", 
				"drop", 
				"use"
				};
		
		//Declare valid magic commands. Currently unused
		String[] badMagic = {"xyzzy", "plugh"};
		
		//Store user's command in a String
		String cmd = s.nextLine();
		
		/*
		 * INPUT SANITIZATION
		 */
		
		//If user presses enter, print a new line
		if (cmd.equals("")) {
			System.out.printf(">");
			return true;
		}
		
		//In case the user entered a multi-word command, use String.split
		String[] cmds = cmd.split("\\W+");
		
		//Reject all commands with more than two words
		if (cmds.length > 1) {
			output(unknown());
			return true;
		}
		
		/*
		 * BASIC COMMANDS
		 */
		
		//Basic exit and help commands
		if (cmd.equals("exit")) {
			//output("Goodbye");
			return false;
		}
		
		if(cmd.equals("help")) {
			output(help);
			return true;
		}
		
		/*
		 * COMPLEX COMMANDS
		 */
		
		//Function to change current room
		
		for (int i = 0; i < directions.length; i++) {
			//Compare user's command with every element of directions[]
			if (cmd.toLowerCase().equals(directions[i])) {
				//Change player's location
				Location.changeLoc(cmd);
				//Print location coordinates
				output(Location.printLoc());
				return true;
			}
		}
		
		//If the user doesn't enter anything useful
		output(unknown());
		return true;
	}
	
	/*
	 * Outputs to console with desired formatting
	 */
	public static void output(String s) {
		System.out.printf("%n%s%n%n>", s);
	}
	
	/**
	 * Provides an environment for the game to run
	 * @param args (ignored)
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		//Declare gamestate variable
		boolean game = true;
		
		//Declare player coordinates (with starting coords)
		Location playerLocation = new Location(0, 0);
		
		//Create all rooms
		Scanner read = null;
		try {
			read = new Scanner(new File("src/adventure/places"));
		} catch (FileNotFoundException e) {
			System.out.printf("File not found.%n");
		}
		
		Room[] ggrid = createMap(read);
		
		//Introduce game
		System.out.printf("%s%n>", opening);
		
		//Start taking user input
		do {
			game = takeInput(input, playerLocation);
		} while (game);
		
		input.close();
	}

}
