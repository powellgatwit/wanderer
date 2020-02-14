package wanderer;
/**
 * 
 * Keeps track of player location
 * 
 * @author george
 *
 */
public class Player {
	
	private static int[] loc = {0, 0};
	private static int n, s, e, w;
	
	//A player can only have 10 items in their inventory (slot 0-9)
	// TODO: change to ArrayList
	private static String[] inv = {"", "", "", "", "", "", "", "", "", ""};
	
	Player(int x, int y) {
		loc[0] = x;
		loc[1] = y;
	}
	
	/**
	 * Changes current x-y coordinates
	 * @param cmd (user-entered command, can only be n, s, e, or w)
	 * @return x and y location
	 */
	public int[] changeLoc(String cmd) {
		//Create the final location
		int[] newLoc = loc;
		
		//Change based on input
		if (cmd.equals("n") || cmd.equals("north")) {
			loc[0] += 1;
		}
		if (cmd.equals("s") || cmd.equals("south")) {
			loc[0] -= 1;
		}
		if (cmd.equals("e") || cmd.equals("east")) {
			loc[1] += 1;
		}
		if (cmd.equals("w") || cmd.equals("west")) {
			loc[1] -= 1;
		}
		
		return newLoc;
	}
	
	/**
	 * Makes a x-y coordinate pretend to be a NSEW coordinate.
	 * Debug purposes only.
	 * Prints location like so:
	 * N: 0
	 * S: 0
	 * E: 0
	 * W: 0
	 */
	public String printLoc() {
		//int n, s, e, w;
		//set north and south
		if (loc[0] >= 0) {
			n = loc[0];
			s = 0;
		} else {
			n = 0;
			s = loc[0];
		}
		//set east and west
		if (loc[1] >= 0) {
			e = loc[1];
			w = 0;
		} else {
			e = 0;
			w = loc[1];
		}
		
		return String.format("N: %d%nS: %d%nE: %d%nW: %d", n, s, e, w);
	}
	
	public String[] getInv() {
		return inv;
	}
	
	public void addInv(String item) {
		// TODO: implement with ArrayList
		//This would be a lot easier with Arraylists
	}
	
	public int[] getLoc() {
		return loc;
	}
	
	@Override
	public String toString() {
		return String.format("N: %d%nS: %d%nE: %d%nW: %d", n, s, e, w);
	}
	
}
