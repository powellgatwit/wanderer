package adventure;
/**
 * 
 * Keeps track of player location
 * 
 * @author george
 *
 */
public class Location {
	
	private static int[] playerLoc = {0, 0};
	private static int n, s, e, w;
	
	@SuppressWarnings("unused")
	protected int[] loc = {0, 0};
	
	Location(int x, int y) {
		playerLoc[0] = x;
		playerLoc[1] = y;
	}
	
	/*
	 * How do I create many different rooms efficiently?
	 * Tie them to playerLoc. Each "room" will be a coordinate.
	 */
	
	/**
	 * Changes current x-y coordinates
	 * @param cmd (user-entered command, can only be n, s, e, or w)
	 * @return x and y location
	 */
	public static int[] changeLoc(String cmd) {
		//Create the final location
		int[] newLoc = playerLoc;
		
		//Change based on input
		if (cmd.equals("n")) {
			newLoc[0] += 1;
		}
		if (cmd.equals("s")) {
			newLoc[0] -= 1;
		}
		if (cmd.equals("e")) {
			newLoc[1] += 1;
		}
		if (cmd.equals("w")) {
			newLoc[1] -= 1;
		}
		
		return newLoc;
	}
	
	/**
	 * Makes a x-y coordinate pretend to be a NSEW coordinate.
	 * Prints location like so: (debug only.)
	 * N: 0
	 * S: 0
	 * E: 0
	 * W: 0
	 */
	public static String printLoc() {
		//int n, s, e, w;
		//set north and south
		if (playerLoc[0] >= 0) {
			n = playerLoc[0];
			s = 0;
		} else {
			n = 0;
			s = playerLoc[0];
		}
		//set east and west
		if (playerLoc[1] >= 0) {
			e = playerLoc[1];
			w = 0;
		} else {
			e = 0;
			w = playerLoc[1];
		}
		
		return String.format("N: %d%nS: %d%nE: %d%nW: %d", n, s, e, w);
	}
	
	@Override
	public String toString() {
		return String.format("N: %d%nS: %d%nE: %d%nW: %d", n, s, e, w);
	}
	
}
