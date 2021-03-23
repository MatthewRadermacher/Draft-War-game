public class Unit extends Piece {
	
	public Unit() {
		super();
	}
	
	public Unit(String name, int attack, int health, int range) {
		super(name, attack, health, range);
	}
	
	public Unit(Piece unit) {
		super(unit);
	}
	
}