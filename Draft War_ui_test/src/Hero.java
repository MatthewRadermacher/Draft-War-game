public class Hero extends Piece {
	
	public Hero() {
		super();
	}
	
	public Hero(String name, int attack, int health, int range) {
		super(name, attack, health, range);
	}
	
	public Hero(Piece hero) {
		super(hero);
	}
	
}