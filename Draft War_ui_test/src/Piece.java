public class Piece {
	
	public String name;
	public int attack, health, range;
	public Point location;
	
	public Piece() {
		name = "undefined";
		attack = 1;
		health = 1;
		range = 1;
		location = new Point(-1, -1);
	}
	
	public Piece(String name, int attack, int health, int spawnCost) {
		this.name = name;
		this.attack = attack;
		this.health = health;
		this.range = spawnCost;
		location = new Point(-1, -1);
	}
	
	public Piece(Piece p) {
		this.name = p.getName();
		this.attack = p.getAttack();
		this.health = p.getHealth();
		this.range = p.getRange();
		this.location = new Point(p.getLocation().x, p.getLocation().y);
	}
	
	public String getName() {
		return name;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getRange() {
		return range;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setLocation(Point location) {
		this.location.x = location.x;
		this.location.y = location.y;
	}
	
	public void setLocation(int x, int y) {
		this.location.x = x;
		this.location.y = y;
	}
}