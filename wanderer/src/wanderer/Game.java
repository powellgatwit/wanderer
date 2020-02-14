package wanderer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Game {

	//Opening message
	public static final String opening = String.format("Welcome to Wanderer 1.0. To play the game, type short commands below. If you type %nthe word \"look,\" I will describe your surroundings to you. Typing %n\"inventory\" tells you what you're carrying. Typing \"take\" or \"drop\" can help you %ninteract with things. Type \"exit\" to quit \"help\" to see this message again. %nThere are many more commands, so try different things, and see what happens.");

	//Help message
	public static final String help = String.format("To play the game, type short commands below. If you type the word \"look,\" %nI will describe your surroundings to you. Typing \"inventory\" tells you %nwhat you're carrying. Typing \"take\" or \"drop\" can help you %ninteract with things. Type \"exit\" to quit \"help\" to see this message again. There are many more commands, so try different things, and %nsee what happens.");
	
	//Info message
	public static final String info = String.format("You are wandering through a desolate wilderness, with no people around for hundreds of %nmiles. Nearby is an abandoned town where high society once enjoyed peace and prosperity. %nHistorical documents speak of a strange and powerful artifact that turns ordinary objects %ninto gold, but it is rumored that those who venture out to find it never return...");
	
	//Invalid movement message
	public static final String invalid = String.format("You can't go that direction from here.");
	
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
		//TODO: Find out how to make this portable
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
	
	/**
	 * 
	 * @param p Player
	 * @param grid Game map
	 * @return description of where the player is standing
	 */
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
	public static int move(Player p, String cmd, Room[] grid) {
		//Declare valid directional commands
		String[] directions = {"n", "north", "s", "south", "e", "east", "w", "west"};
		
		for (int i = 0; i < directions.length; i++) {
			//Compare user's command with every element of directions[]
			cmd = cmd.toLowerCase();
			if (cmd.equals(directions[i])) {
				//if user moves n,s,e,w:
				//Change player's location
				p.changeLoc(cmd);
				
				//if the player moves to a location with a valid room:
				if(!(look(p, grid).equals("The player is not in a room..."))) {
					//end function, successful move
					return 1;
				} else {
				//if invalid, change the player xy to what it was before
				//since this is local, it's like the player never moved
					if (cmd.equals("n") || cmd.equals("north")) {
						p.changeLoc("s");
					} else if (cmd.equals("s") || cmd.equals("south")) {
						p.changeLoc("n");
					} else if (cmd.equals("e") || cmd.equals("east")) {
						p.changeLoc("w");
					} else if (cmd.equals("w") || cmd.equals("west")) {
						p.changeLoc("e");
					} else {
						output("Something went wrong when the user tried to move to a Room[] that doesn't exist");
					}
					output(invalid);
				}
				//Print new location coordinates (DEBUG ONLY)
				//output(p.printLoc());
				
				//end function, unsuccessful move
				return -1;
				
			}
		}
		//end function, player did not enter movement command
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
			output("I can't understand more than two words.");
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
		
		// TODO: implement items
		
		//any item can be inside any room. they can be picked up and dropped.
		//picking up an item should ADD it from Player inventory
		//dropping it should REMOVE from Player inventory and ADD to that room
		
		// items are going to be Strings
		
		//Function for displaying inventory
		if (cmd.equals("inventory") || cmd.equals("inv")) {
			
			// TODO: delete this, add ArrayList functionality
			
			String[] inv = p.getInv();
			String inventory = String.format("You are carrying: %n");
			for (int i = 0; i < inv.length; i++) {
				if(!(inv[i].equals(""))) {
					inventory += inv[i] + String.format("%n");
				}
				//yeah, this is going to be deleted. use an arraylist.
			}
			output(inventory);
			return true;
		}
		
		//Function for looking around
		if (cmd.equals("look")) {
			output(look(p, g));
			return true;
		}
		
		//Will return 1 if the player enters a movement command to a valid Room
		int movement = move(p, cmd, g);
		if (movement == 1) {
			output(look(p, g));
			return true;
		//Will return -1 if the player enters a valid movement command to an invalid Room
		} else if (movement == -1) {
			return true;
		}//returns zero otherwise

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

	/**
	 * Format and output a string
	 * @param s String to be formatted and output
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
		Player george = new Player(1, 1);
		
		//Print game opening
		System.out.printf("%s%n%nType \"info\" for more info.%n>", opening);
		
		//Takes parameters Scanner input, Player, and Room[] game map. Runs game
		do {
			game = takeInput(input, george, gameMap);
		} while (game);
		
		input.close();
	}

}
