import java.util.ArrayList;

public class Player {
	
	public String name = "undefined";
	public ArrayList<Piece> army;
	public Hero hero;
	
	public Player(String name) {
		this.name = name;
		army = new ArrayList<Piece>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Piece> getArmy() {
		return army;
	}
	
	public Hero getHero() {
		return hero;
	}
	
	public void appendArmy(Piece p) {
		army.add(p);
	}
	
	public void removeFromArmy(Piece p) {
		army.remove(p);
	}
	
	public void setHero(Hero hero) {
		this.hero = hero;
	}
}