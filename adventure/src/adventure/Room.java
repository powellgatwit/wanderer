package adventure;

public class Room extends Location {
	
	/**
	 * Create one Room object for each in-game place.
	 * 
	 * Needs:
	 * 
	 * Coords
	 * Things that are in the room ("There is a knife on the table.")
	 * Description of the room (A small, candlelit hut.)
	 * 
	 * Method to return description of room (take coords as input)
	 * 
	 */
	
	private String name = "";
	
	private String desc = "";
	
	Room(String name, String desc, int x, int y) {
		this.name = name;
		this.desc = desc;
		this.loc[0] = x;
		this.loc[1] = y;
	}
	
	public String toString() {
		return String.format("%s: %s", name, desc);
	}
	
}
