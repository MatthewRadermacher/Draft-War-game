import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Draft {
	
	private int round;// 1 = hero, 2-6 unit rounds
	private ArrayList<Piece> heroList, unitList;
	private Player player, computer;
	private Scanner input;
	
	public Draft(Player player, Player computer) {
		round = 0;
		heroList = new ArrayList<Piece>();
		unitList = new ArrayList<Piece>();
		this.player = player;
		this.computer = computer;
		input = new Scanner(System.in);
		
		System.out.println("Loading game data");
		loadData();
	}
	
	private void loadData() {
		File file = new File("src/Data/char_list.txt");
		String data = "";
		
		//read data from file into string
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				String line; 
				while ((line = br.readLine()) != null) {
					data += line + ":";
				}
			}
			catch(IOException e) {
				System.out.println("Error reading data file");
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Error creating buffer reader for file");
			e.printStackTrace();
		}
		
		//test print the data object
		//System.out.println(data);
		
		//parse data, create object, add to data structures
		String[] uniqueData = data.split(";");
		String[] heroData = uniqueData[0].split(":");
		String[] unitData = uniqueData[1].substring(1).split(":");
		
		//parse hero data
		for (String t : heroData) {
			//System.out.println(t);
			String[] heroStats = t.split("\\.");
			Piece h = new Hero(heroStats[0], 
							Integer.parseInt(heroStats[1]), 
							Integer.parseInt(heroStats[2]), 
							Integer.parseInt(heroStats[3]));
			heroList.add(h);
		}
		//parse unit data
		for (String j : unitData) {
			//System.out.println(j);
			String[] unitStats = j.split("\\.");
			Piece u = new Unit(unitStats[0], 
							Integer.parseInt(unitStats[1]), 
							Integer.parseInt(unitStats[2]), 
							Integer.parseInt(unitStats[3]));
			unitList.add(u);
		}
		
	}
	
	public void start() {
		System.out.println("Draft Started\n");
		run();
	}
	
	private void run() {
		do {
			if (round == 0) {
				pickHero();
			}
			else if (round > 0 && round < 6) {
				unitRound();
			}
			round++;
		} while(round < 6);
		input.close();
		
		//show what the user selected as their army
		System.out.println("Your army:");
		//System.out.println(player.getHero().getName());
		for (Piece p : player.getArmy()) {
			System.out.println(p.getName());
		}
		System.out.println();
		
		//show what the computer selected as their army
		System.out.println("Your opponents army:");
		//System.out.println(computer.getHero().getName());
		for (Piece p : computer.getArmy()) {
			System.out.println(p.getName());
		}
	}
	
	private void pickHero() {
		Random rand = new Random();
		//Piece[] heroes = new Piece[5];
		ArrayList<Piece> heroes = new ArrayList<Piece>();
		int n = 0;
		
		//create random list of heroes in draft from heroes in data
		for(int i = 0; i < 5; i++) {
			n = rand.nextInt(heroList.size());
			//heroes[i] = heroList.get(n);
			heroes.add(heroList.get(n));
			heroList.remove(n);
		}
		
		System.out.println("Pick your Hero");
		for(int i = 0; i < heroes.size(); i++) {//changed to fit arraylist implementation
			System.out.println((i + 1) + ". " + heroes.get(i).getName());
		}
		//System.out.println();
		int choice = 1;
		choice = input.nextInt();
		
		//verify choice
		if (choice > 0 && choice < 6) {
			player.setHero(new Hero(heroes.get(choice - 1)));//changed to fit arraylist implementation
			player.appendArmy(new Hero(heroes.get(choice - 1)));
			//remove hero selected from draft pool
			heroes.remove(choice - 1);
		}
		
		//test to see what hero the player has
		System.out.println("You have chosen the " + player.getHero().getName() + " as your hero");
		
		//draft hero for computer
		int f = rand.nextInt(heroes.size());
		computer.setHero(new Hero(heroes.get(f)));//changed to fit arraylist implementation
		computer.appendArmy(new Hero(heroes.get(f)));
		
		//test to see what hero the computer selected
		System.out.println("Your opponent has chosen the " + computer.getHero().getName() + " as their hero");
	}
	
	private void unitRound() {
		System.out.println("\nRound " + round + " of the draft has started");
		Random rand = new Random();
		ArrayList<Piece> unitPool = new ArrayList<Piece>();
		int n = 0;
		
		//generate pool of units for this round of the draft
		for(int i = 0; i < 8; i++) {
			n = rand.nextInt(unitList.size());
			unitPool.add(unitList.get(n));
		}
		
		int choice = 1;
		//rotate picking units in this round until only 4 remain
		while(unitPool.size() > 2) {
			System.out.println("Select a Unit");
			for(int i = 0; i < unitPool.size(); i++) {
				System.out.println((i + 1) + ". " + unitPool.get(i).getName());
			}
			
			//loop until player inputs valid unit number
			while(true) {
				choice = input.nextInt();
				if (choice > 0 && choice <= unitPool.size()) {
					player.appendArmy(new Unit(unitPool.get(choice - 1)));
					unitPool.remove(choice - 1);
					break;
				}
			}
			
			//test to see what unit the player has selected
			//System.out.println("army size: " + player.getArmy().size());
			System.out.println("You have selected a " + player.getArmy().get(player.getArmy().size() - 1).getName());
			
			//draft random unit for computer
			int t = rand.nextInt(unitPool.size());
			computer.appendArmy(new Unit(unitPool.get(t)));
			unitPool.remove(t);
			
			//test to see what unit the computer has selected
			System.out.println("Your opponent has selected a " + computer.getArmy().get(computer.getArmy().size() - 1).getName() + "\n");
		}
	}
}