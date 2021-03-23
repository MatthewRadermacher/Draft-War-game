public class Game {
	
	public Player player, computer;
	public Draft draft;
	public int turn = 1;
	
	public Game(String name) {
		init(name);
	}
	
	private void init(String name) {
		player = new Player(name);
		computer = new Player("Computer");
		draft = new Draft(player, computer);
		System.out.println("Game initialized");
	}
	
	public void start() {
		System.out.println("Game Started");
		run();
	}
	
	public void draft() {
		draft.start();
		System.out.println("\nThe draft is complete");
	}
	
	public void run() {
		
	}
	
}