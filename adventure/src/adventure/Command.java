package adventure;

public class Command {
	
	//All commands can only be two words.
	private String one;
	private String two;
	
	//List valid commands
	private String[] verbs = {"look", "take", "use"};
	
	private String[] directions = {"n", "s", "e", "w"};
	
	private String[] things = {"lamp", "keys", "food", "package"};
	
	private String[] magic = {"xyzzy", "plugh"};
	
	//Constructors
	public Command(String one) {
		super();
		this.one = one;
	}
	
	public Command(String one, String two) {
		super();
		this.one = one;
		this.two = two;
	}
	
}
