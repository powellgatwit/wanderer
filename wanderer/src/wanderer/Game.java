package wanderer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Game {

	//Opening message
	public final static String opening = String.format("Welcome to Wanderer 1.0. To play the game, type short commands below. If you type %nthe word \"look,\" I will describe your surroundings to you. Typing %n\"inventory\" tells you what you're carrying. Typing \"take\" or \"drop\" can help you %ninteract with things. Type \"exit\" to quit \"help\" to see this message again. %nThere are many more commands, so try different things, and see what happens.");

	//Help message
	public final static String help = String.format("To play the game, type short commands below. If you type the word \"look,\" %nI will describe your surroundings to you. Typing \"inventory\" tells you %nwhat you're carrying. Typing \"take\" or \"drop\" can help you %ninteract with things. Type \"exit\" to quit \"help\" to see this message again. There are many more commands, so try different things, and %nsee what happens.");
	
	//Info message
	public final static String info = String.format("You are wandering through a desolate wilderness, with no people around for hundreds of %nmiles. Nearby is an abandoned town where high society once enjoyed peace and prosperity. Historical %ndocuments speak of a strange and powerful artifact that turns ordinary objects into %ngold, but it is rumored that those who venture out to find it never return...");
	
	/**
	 * Contains array of strings and a random number generator
	 * @return String to display when user types in unknown command
	 */
	public static String invalidCommand() {
		String[] responses = {
				"What?",
				"Sorry?",
				"Excuse me?",
				"I don't understand.",  
				"That doesn't make sense.",
		};

		//Create a random number between 0 and responses.length
		int r = (int) (Math.floor(Math.random() * responses.length));

		return responses[r];

	}

	/**
	 * This function creates a coordinate grid of Room objects
	 * @param read Scanner to read content from gamedata file
	 * @return Room[] array of all rooms, each with a unique index.
	 * @throws FileNotFoundException 
	 */
	public static Room[] createMap() throws FileNotFoundException {
		//Declare dimensions of game board (adjust these based on game content)
		int length = 3;
		int height = 3;

		//Open scanner dedicated to reading
		Scanner read = new Scanner(new FileReader("/Users/george/git/wanderer/wanderer/src/wanderer/places"));
		
		//Create array of Rooms based on those dimensions - "game grid"
		Room[] ggrid = new Room[length * height];

		//n keeps track of index for Room array
		int n = 0;
		
		//Outer for loop keeps track of x (N/S)
		for (int i = 0; i < length; i++) {
			//Inner for loop keeps track of y (E/W)
			for (int j = 0; j < height; j++) {
				ggrid[n] = new Room(read.nextLine(), read.nextLine(), i, j);
				n++;
			}
		}
		read.close();
		return ggrid;
	}

	public static String look(Player p, Room[] grid) {
		//for every room that exists:
		for (int i = 0; i < grid.length; i++) {
			//Make the two arrays easier to work with
			int[] player = p.getLoc();
			int[] room = grid[i].getLoc();
			//Compare current player location to room[i]'s location
			if (player[0] == room[0] && player[1] == room[1]) {
				return grid[i].getDesc();
			}
		}
		//if no matching room is found
		return "The player is not in a room...";
	}

	/**
	 * Takes a player and a command. 
	 * If player enters a valid movement command, return 1. Otherwise return 0.
	 * @param Player p
	 * @param String cmd
	 * @return int 0 or 1
	 */
	public static int move(Player p, String cmd) {
		//Declare valid directional commands
		String[] directions = {"n", "north", "s", "south", "e", "east", "w", "west"};

		for (int i = 0; i < directions.length; i++) {
			//Compare user's command with every element of directions[]
			if (cmd.toLowerCase().equals(directions[i])) {
				//Change player's location
				p.changeLoc(cmd);
				//Print location coordinates
				output(p.printLoc());
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Reads and interprets user input
	 * Each section must return true to continue the game 
	 * (and avoid triggering the invalidCommand response)
	 * @param s Scanner
	 * @return boolean (false to quit the program)
	 */
	public static boolean takeInput(Scanner s, Player p, Room[] g) {
		
		//Store user's command in a String
		String cmd = s.nextLine();

		//If user presses enter, print a new line
		if (cmd.equals("")) {
			System.out.printf(">");
			return true;
		}

		//In case the user entered a multi-word command, use String.split
		String[] cmds = cmd.split("\\W+");

		//Reject all commands with more than two words
		if (cmds.length > 2) {
			output(invalidCommand());
			return true;
		}

		//Basic exit and help commands
		if (cmd.equals("exit")) {
			return false;
		} else if (cmd.equals("help")) {
			output(help);
			return true;
		}
		
		//Basic info command
		if (cmd.equals("info")) {
			output(info);
			return true;
		}
		
		//Function for looking around
		if (cmd.equals("look")) {
			output(look(p, g));
			return true;
		}
		
		//Will return 1 if the player enters a movement command
		int movement = move(p, cmd);
		if (movement == 1) {
			return true;
		}

		//Declare valid PREFIX (action) commands. Currently unused
		String[] actions = {"take", "drop", "use"};

		//Function for valid action commands
		for (int i = 0; i < actions.length; i++) {
			//If the player enters a one-word action
			if(cmd.toLowerCase().equals(actions[i])) {
				output(String.format("What do you want to %s?", cmd));
				return true;
			}
			//If the player enters a two-word action
		}


		//Declare invalid magic commands.
		//String[] badMagic = {"xyzzy", "plugh"};
		//Function for all magic commands


		//If the user doesn't enter anything useful
		output(invalidCommand());
		return true;
	}

	/*
	 * Outputs to console with desired formatting
	 */
	public static void output(String s) {
		System.out.printf("%s%n>", s);
	}

	/**
	 * Provides an environment for the game to run
	 * Throws FileNotFoundException if gamedata file is not found
	 * @param args (ignored)
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		//Create input scanner
		Scanner input = new Scanner(System.in);

		//Declare gamestate variable
		boolean game = true;
		
		//Create game map
		Room[] gameMap = createMap();
				
		//Create new player (with starting coords)
		Player george = new Player(0, 0);
		
		//Print game opening
		System.out.printf("%s%n%nType \"info\" for more info.%n>", opening);
		
		//Takes parameters Scanner input, Player, and Room[] game map. Runs game
		do {
			game = takeInput(input, george, gameMap);
		} while (game);
		
		input.close();
	}

}
