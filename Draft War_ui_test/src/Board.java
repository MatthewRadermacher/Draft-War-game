import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Board extends JPanel {
	
	private JButton[][] buttonGrid = new JButton[8][8];
	private JButton[] spawnButtonGrid = new JButton[16];
	private JPanel grid;
	private JPanel userInputBox;
									//-1 is empty
	private ImageIcon barbarian;	// 0
	private ImageIcon hunter;		// 1 
	private ImageIcon knight;		// 2
	private ImageIcon necromancer;	// 3
	private ImageIcon rogue;		// 4
	private ImageIcon wizard;		// 5
	
	private ImageIcon archer;		// 6
	private ImageIcon ballista;		// 7
	private ImageIcon brute;		// 8
	private ImageIcon calvary;		// 9
	private ImageIcon dragon;		//10
	private ImageIcon mage;			//11
	private ImageIcon ogre;			//12
	private ImageIcon skeleton;		//13
	
	public Point lastButton = new Point(999, 999);
	//public int[][] system = new int[8][8];
	public Piece[][] system = new Piece[8][8];
	public int[] spawnArr = new int[16];
	//public int[] cpuSpawnArr = new int[16];
	public ArrayList<Integer> cpuSpawnArr = new ArrayList<Integer>();
	private Game game;
	private ArrayList<String> linker;
	public Border lastBorder = UIManager.getBorder("Button.border");
	int spawned = 0;
	public JLabel label;
	
	
	public Board(Game game) {
		
		this.game = game;
		loadImages();
		initUI();
	}

	private void initUI() {
		
		label = new JLabel();
		//label.setText("TEST");
		
		//initialize system
		linker = new ArrayList<String>();
		linker.add("barbarian");
		linker.add("hunter");
		linker.add("knight");
		linker.add("necromancer");
		linker.add("rogue");
		linker.add("wizard");
		linker.add("archer");
		linker.add("ballista");
		linker.add("brute");
		linker.add("calvary");
		linker.add("dragon");
		linker.add("mage");
		linker.add("ogre");
		linker.add("skeleton");
		
		//initialize system
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				system[i][j] = new Piece();
			}
		}
		
		//initialize spawnArr
		for(int i = 0; i < 16; i++) {
			spawnArr[i] = -1;
		}
		
		setPreferredSize(new Dimension(600, 800));
		setLayout(new BorderLayout());
		
		//set up game board
		grid = new JPanel(new GridLayout(8, 8));
		int c = 1;
		for(int i = 0; i < buttonGrid.length; i++) {
			for(int j = 0; j < buttonGrid[i].length; j++) {
				buttonGrid[i][j] = new JButton();
				if (c > 0) {
					buttonGrid[i][j].setBackground(Color.WHITE);
					c *= -1;
				}
				else {
					buttonGrid[i][j].setBackground(Color.LIGHT_GRAY);
					c *= -1;
				}
				//add button to grid panel
				buttonGrid[i][j].putClientProperty("position", new Point(i, j));
				grid.add(buttonGrid[i][j]);
				buttonGrid[i][j].addActionListener(new ClickAction());
				buttonGrid[i][j].putClientProperty("playerpiece", 0);
				
				//add mouse over effect
				buttonGrid[i][j].getModel().addChangeListener(new ChangeHandler(new Point(i, j), label));
			}
			c *= -1;
		}
		grid.setPreferredSize(new Dimension(600, 600));
		//grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(grid, BorderLayout.PAGE_START);
		
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new BorderLayout());
		descriptionPanel.setPreferredSize(new Dimension(600, 100));
		
		//label for description
		descriptionPanel.add(label, BorderLayout.CENTER);
		add(descriptionPanel, BorderLayout.CENTER);
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial", Font.PLAIN, 24));
		//label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		//set up user input area at the bottom of the screen
		
		userInputBox = new JPanel();
		userInputBox.setLayout(new BorderLayout());
		userInputBox.setPreferredSize(new Dimension(600, 160));
		//userInputBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(userInputBox, BorderLayout.PAGE_END);
		
		JPanel spawnGrid = new JPanel();
		spawnGrid.setLayout(new GridLayout(2, 8, 0, 0));
		userInputBox.add(spawnGrid, BorderLayout.PAGE_END);
		
		//add army to userInputBox
		for(int i = 0; i < 16; i++) {
			spawnButtonGrid[i] = new JButton();
			spawnButtonGrid[i].setPreferredSize(new Dimension(80, 80));
			spawnButtonGrid[i].setBackground(Color.WHITE);
			
			/*
			if(i == 0) {
				
				//add hero to spawn camp
				String p = game.player.getHero().getName();
				System.out.println("HERO: " + p);
				int n = linker.indexOf(p);
				spawnButtonGrid[i].setIcon(getScaleIcon(intToPiece(n)));
				spawnArr[i] = n;
				
			}
			else {
				
				//add piece to spawn camp
				String p = game.player.getArmy().get(i - 1).getName();
				int n = linker.indexOf(p);
				spawnButtonGrid[i].setIcon(getScaleIcon(intToPiece(n)));
				spawnArr[i] = n;
			}
			*/
			
			//add piece to spawn camp
			String p = game.player.getArmy().get(i).getName();
			int n = linker.indexOf(p);
			spawnButtonGrid[i].setIcon(getScaleIcon(intToPiece(n)));
			spawnArr[i] = n;
			
			spawnButtonGrid[i].putClientProperty("position", new Point(-1, i));
			
			//add button to spawnGrid
			spawnGrid.add(spawnButtonGrid[i]);
			spawnButtonGrid[i].addActionListener(new ClickAction());
			
			//add mouse over effect
			spawnButtonGrid[i].getModel().addChangeListener(new ChangeHandler(new Point(-1, i), label));
		}
		
		/*
		//log spawnArr
		System.out.println("spawnArr");
		for(int i = 0; i < 16; i++) {
			System.out.println(spawnArr[i]);
		}
		
		//log armies
		System.out.println("PLAYER ARMY");
		for (Piece u : game.player.army) {
			System.out.println(u.name);
		}
		*/
		System.out.println();
	}
	
	private void loadImages() {
		
		barbarian = new ImageIcon("data/heroes/barbarian.png");
		hunter = new ImageIcon("data/heroes/hunter.png");
		knight = new ImageIcon("data/heroes/knight.png");
		necromancer = new ImageIcon("data/heroes/necromancer.PNG");
		rogue = new ImageIcon("data/heroes/rogue.PNG");
		wizard = new ImageIcon("data/heroes/wizard.png");
		
		archer = new ImageIcon("data/units/archer.PNG");
		ballista = new ImageIcon("data/units/ballista.png");
		brute = new ImageIcon("data/units/brute.PNG");
		calvary = new ImageIcon("data/units/calvary.jpg");
		dragon = new ImageIcon("data/units/dragon.PNG");
		mage = new ImageIcon("data/units/mage.PNG");
		ogre = new ImageIcon("data/units/ogre.PNG");
		skeleton = new ImageIcon("data/units/skeleton.jpg");
	}
	
	private ImageIcon getScaleIcon(ImageIcon i) {
		
		return new ImageIcon(i.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH));
	}
	
	private ImageIcon intToPiece(int i) {
		
		switch(i) {
			case 0: 
				return barbarian;
			case 1:
				return hunter;
			case 2:
				return knight;
			case 3:
				return necromancer;
			case 4:
				return rogue;
			case 5:
				return wizard;
			case 6:
				return archer;
			case 7:
				return ballista;
			case 8:
				return brute;
			case 9:
				return calvary;
			case 10:
				return dragon;
			case 11:
				return mage;
			case 12:
				return ogre;
			case 13:
				return skeleton;
			default:
				return barbarian;
		}
	}
	
	public double getDistance(int x1, int y1, int x2, int y2) {
		return (Math.sqrt((Math.pow((x2 - x1), 2)) + (Math.pow((y2 - y1), 2))));
	}
	
	public double getDistance(Piece p1, Piece p2) {
		return (Math.sqrt((Math.pow((p2.location.x - p1.location.x), 2)) + (Math.pow((p2.location.y - p1.location.y), 2))));
	}
	
	//simulate next more for cpu
	private void nextMove() {
		Random rand = new Random();
		//if it's the first turn
		if(game.turn == 1) {
			
			//load cpu army into cpuSpawnArr
			for(int i = 0; i < 16; i++) {
				
				if(i == 0) {	
					//add hero to cpu spawn camp
					String h = game.computer.getHero().getName();
					int n = linker.indexOf(h);
					//cpuSpawnArr[i] = n;
					cpuSpawnArr.add(n);
					
				}
				else {
					
					//add piece to cpu spawn camp
					String p = game.computer.getArmy().get(i - 1).getName();
					int n = linker.indexOf(p);
					//cpuSpawnArr[i] = n;
					cpuSpawnArr.add(n);
				}
			}
			
			//test log the cpu spawn array
			//System.out.println("cpuSpawnArr: ");
			//for(int i = 0; i < 16; i++) {
			//	System.out.println(cpuSpawnArr.get(i));
			//}
			
			//get spawn space for hero
			int r = rand.nextInt(8);
			
			//spawn hero for cpu
			//system[0][r] = cpuSpawnArr.get(0);
			system[0][r] = game.computer.army.get(0);
			buttonGrid[0][r].setIcon(getScaleIcon(intToPiece(linker.indexOf(system[0][r].name))));
			buttonGrid[0][r].setBorder(BorderFactory.createLineBorder(Color.RED));
			
			//update position of hero
			game.computer.army.get(0).location.x = 0;
			game.computer.army.get(0).location.y = r;
			
			//remove hero from cpu spawn arr
			//cpuSpawnArr[0] = -1;
			//cpuSpawnArr.remove(0);
			
			//claim ownership for cpu
			buttonGrid[0][r].putClientProperty("playerpiece", 2);
			
			spawned++;
			
			System.out.println("Computer spawned " + game.computer.army.get(0).name);
		}
		else {// not first turn
			boolean actionPerformed = false;
			int tries = 0;
			
			//try to do an action until success
			do {
				tries++;
				//get the action to try
				int r = rand.nextInt(3); // 0 = spawn, 1 = move, 2 == attack
				
				//if all units are spawned pick new action
				if (r == 0 && spawned == game.computer.army.size()) {
					r = rand.nextInt(2) + 1;
				}
				
				//if spawn action
				if(r == 0) {
					//create a random space to spawn on
					int rngSpaceY = rand.nextInt(8);
					int rngSpaceX = rand.nextInt(2);

					//System.out.println("spawn space: " + (rngSpace % 2) + ", " + rngSpace);
					
					//check if empty space
					if(system[rngSpaceX][rngSpaceY].name.equals("undefined")) {
						//spawn unit
						
						//pick random unit in army
						//int x = rand.nextInt(cpuSpawnArr.size());
						int x = rand.nextInt(game.computer.army.size());
						
						int timesTested = 0;
						//loop until unspawned piece is chosen
						while (game.computer.army.get(x).location.x != -1) {
							x++;
							timesTested++;
							if (x >= game.computer.army.size()) x = 0;
							//if no more units left to spawn
							if (timesTested > 20) {
								break;
							}
						}
						
						//spawn unit if there is one to spawn
						if (timesTested <= 20) {
							//spawn unit
							system[rngSpaceX][rngSpaceY] = game.computer.army.get(x);
							buttonGrid[rngSpaceX][rngSpaceY].setIcon(getScaleIcon(intToPiece(linker.indexOf(system[rngSpaceX][rngSpaceY].name))));
							buttonGrid[rngSpaceX][rngSpaceY].setBorder(BorderFactory.createLineBorder(Color.RED));
							
							//update position of piece
							game.computer.army.get(x).location.x = rngSpaceX;
							game.computer.army.get(x).location.y = rngSpaceY;
								
							//remove unit from cpu spawn arr
							//cpuSpawnArr[x] = -1;
							//cpuSpawnArr.remove(x);
								
							//claim ownership for cpu
							buttonGrid[rngSpaceX][rngSpaceY].putClientProperty("playerpiece", 2);
								
							//cpu performed action
							actionPerformed = true;
							spawned++;
							
							System.out.println("Computer spawned " + game.computer.army.get(x).name);
						}
					}
				}
				else if(r == 1) {// if move action
					//pick random unit in army
					int x = rand.nextInt(game.computer.army.size());
					
					//if chosen unit is spawned and is alive
					if (game.computer.army.get(x).location.x != -1 && game.computer.army.get(x).health > 0) {
						//get coords of piece to be moved
						int pieceX = game.computer.army.get(x).location.x;
						int pieceY = game.computer.army.get(x).location.y;
						
						//pick random place to move
						int m = rand.nextInt(4);
						
						//move to space, 0 = up, 1 = right, 2 = down, 3 = left, if space is on the board
						if (m == 0 && pieceX - 1 >= 0) {
							//if space is empty
							if (((int) buttonGrid[pieceX - 1][pieceY].getClientProperty("playerpiece")) == 0) {
								//move the piece from lastButton to current button
								system[pieceX - 1][pieceY] = system[pieceX][pieceY];
								system[pieceX][pieceY] = new Piece();
								
								//update postion of unit
								system[pieceX - 1][pieceY].location.x = pieceX - 1;
								system[pieceX - 1][pieceY].location.y = pieceY;
								
								//update icons
								buttonGrid[pieceX - 1][pieceY].setIcon(getScaleIcon(intToPiece(linker.indexOf(system[pieceX - 1][pieceY].name))));
								buttonGrid[pieceX - 1][pieceY].setBorder(BorderFactory.createLineBorder(Color.RED));
								
								//clear old space
								buttonGrid[pieceX][pieceY].setIcon(null);
								//buttonGrid[lastButton.x][lastButton.y].setBorder(UIManager.getBorder("Button.border"));
								buttonGrid[pieceX][pieceY].setBorder(UIManager.getBorder("Button.border"));
								
								//update ownership of space for cpu
								buttonGrid[pieceX - 1][pieceY].putClientProperty("playerpiece", 2);
								
								//update ownership of empty space on board
								buttonGrid[pieceX][pieceY].putClientProperty("playerpiece", 0);
								
								//computer performed action
								actionPerformed = true;
								
								System.out.println("Computer moved " + system[pieceX - 1][pieceY].name + " " + pieceX + ", " + pieceY + " to " + (pieceX - 1) + ", " + pieceY);
							}
						}
						else if (m == 1 && pieceY + 1 <= 7) {
							//if space is empty
							if (((int) buttonGrid[pieceX][pieceY + 1].getClientProperty("playerpiece")) == 0) {
								//move the piece from lastButton to current button
								system[pieceX][pieceY + 1] = system[pieceX][pieceY];
								system[pieceX][pieceY] = new Piece();
								
								//update postion of unit
								system[pieceX][pieceY + 1].location.x = pieceX;
								system[pieceX][pieceY + 1].location.y = pieceY + 1;
								
								//update icons
								buttonGrid[pieceX][pieceY + 1].setIcon(getScaleIcon(intToPiece(linker.indexOf(system[pieceX][pieceY + 1].name))));
								buttonGrid[pieceX][pieceY + 1].setBorder(BorderFactory.createLineBorder(Color.RED));
								
								//clear old space
								buttonGrid[pieceX][pieceY].setIcon(null);
								//buttonGrid[lastButton.x][lastButton.y].setBorder(UIManager.getBorder("Button.border"));
								buttonGrid[pieceX][pieceY].setBorder(UIManager.getBorder("Button.border"));
								
								//update ownership of space for cpu
								buttonGrid[pieceX][pieceY + 1].putClientProperty("playerpiece", 2);
	
								//update ownership of empty space on board
								buttonGrid[pieceX][pieceY].putClientProperty("playerpiece", 0);
								
								//computer performed action
								actionPerformed = true;
								System.out.println("Computer moved " + system[pieceX][pieceY + 1].name + " " + pieceX + ", " + pieceY + " to " + pieceX + ", " + (pieceY + 1));
							}
						}
						else if (m == 2 && pieceX + 1 <= 7) {
							//if space is empty
							if (((int) buttonGrid[pieceX + 1][pieceY].getClientProperty("playerpiece")) == 0) {
								//move the piece from lastButton to current button
								system[pieceX + 1][pieceY] = system[pieceX][pieceY];
								system[pieceX][pieceY] = new Piece();
								
								//update postion of unit
								system[pieceX + 1][pieceY].location.x = pieceX + 1;
								system[pieceX + 1][pieceY].location.y = pieceY;
								
								//update icons
								buttonGrid[pieceX + 1][pieceY].setIcon(getScaleIcon(intToPiece(linker.indexOf(system[pieceX + 1][pieceY].name))));
								buttonGrid[pieceX + 1][pieceY].setBorder(BorderFactory.createLineBorder(Color.RED));
								
								//clear old space
								buttonGrid[pieceX][pieceY].setIcon(null);
								//buttonGrid[lastButton.x][lastButton.y].setBorder(UIManager.getBorder("Button.border"));
								buttonGrid[pieceX][pieceY].setBorder(UIManager.getBorder("Button.border"));
								
								//update ownership of space for cpu
								buttonGrid[pieceX + 1][pieceY].putClientProperty("playerpiece", 2);
	
								//update ownership of empty space on board
								buttonGrid[pieceX][pieceY].putClientProperty("playerpiece", 0);
								
								//computer performed action
								actionPerformed = true;
								System.out.println("Computer moved " + system[pieceX + 1][pieceY].name + " " + pieceX + ", " + pieceY + " to " + (pieceX + 1) + ", " + pieceY);
							}
						}
						else if (m == 3 && pieceY - 1 >= 0) {
							//if space is empty
							if (((int) buttonGrid[pieceX][pieceY - 1].getClientProperty("playerpiece")) == 0) {
								//move the piece from lastButton to current button
								system[pieceX][pieceY - 1] = system[pieceX][pieceY];
								system[pieceX][pieceY] = new Piece();
								
								//update postion of unit
								system[pieceX][pieceY - 1].location.x = pieceX;
								system[pieceX][pieceY - 1].location.y = pieceY - 1;
								
								//update icons
								buttonGrid[pieceX][pieceY - 1].setIcon(getScaleIcon(intToPiece(linker.indexOf(system[pieceX][pieceY - 1].name))));
								buttonGrid[pieceX][pieceY - 1].setBorder(BorderFactory.createLineBorder(Color.RED));
								
								//clear old space
								buttonGrid[pieceX][pieceY].setIcon(null);
								//buttonGrid[lastButton.x][lastButton.y].setBorder(UIManager.getBorder("Button.border"));
								buttonGrid[pieceX][pieceY].setBorder(UIManager.getBorder("Button.border"));
								
								//update ownership of space for cpu
								buttonGrid[pieceX][pieceY - 1].putClientProperty("playerpiece", 2);
	
								//update ownership of empty space on board
								buttonGrid[pieceX][pieceY].putClientProperty("playerpiece", 0);
								
								//computer performed action
								actionPerformed = true;
								System.out.println("Computer moved " + system[pieceX][pieceY - 1].name + " " + pieceX + ", " + pieceY + " to " + pieceX + ", " + (pieceY - 1));
							}
						}
					}
				}
				else if (r == 2) {//if attack action
					//get random unit for attack
					int x = rand.nextInt(game.computer.army.size());
					
					//if chosen unit is spawned and is alive
					if (game.computer.army.get(x).location.x != -1 && game.computer.army.get(x).health > 0) {
						//loop through player army
						for (int a = 0; a < game.player.army.size(); a++) {
							//if player unit is spawned and alive
							if (game.player.army.get(a).location.x > -1 && game.player.army.get(a).health > 0) {
								//if player unit is in range
								if (getDistance(game.computer.army.get(x), game.player.army.get(a)) <= game.computer.army.get(x).range) {
									//if player unit dies from attack
									if (game.computer.army.get(x).attack >= game.player.army.get(a).health) {
										//if player hero dies
										if (game.player.army.get(a).name.equals(game.player.hero.name)) {
											//find coords of hero
											int hx = game.player.army.get(a).location.x;
											int hy = game.player.army.get(a).location.y;
											
											//kill hero
											system[hx][hy].health = 0;
											game.player.hero.health = 0;
											
											//remove hero from game board
											system[hx][hy] = new Piece();
											buttonGrid[hx][hy].setIcon(null);
											
											//alert player of victory
											JOptionPane.showMessageDialog(null, "Defeat", "Defeat", JOptionPane.INFORMATION_MESSAGE);
											//exit game
											System.exit(0);
										}
										else {//if player unit dies
											//find coords of unit
											int hx = game.player.army.get(a).location.x;
											int hy = game.player.army.get(a).location.y;
											
											//kill unit
											system[hx][hy].health = 0;
											
											System.out.println("Computer's " + game.computer.army.get(x).name + " killed player's " + system[hx][hy].name);
											
											//remove unit from game board
											system[hx][hy] = new Piece();
											buttonGrid[hx][hy].setIcon(null);
											buttonGrid[hx][hy].setBorder(UIManager.getBorder("Button.border"));
											buttonGrid[hx][hy].putClientProperty("playerpiece", 0);
											
											//performed action
											actionPerformed = true;
										}
									}
									else {//if player unit is wounded from attack
										//find coords of unit
										int hx = game.player.army.get(a).location.x;
										int hy = game.player.army.get(a).location.y;
										
										//damage unit
										system[hx][hy].health -= game.computer.army.get(x).attack;
										
										System.out.println("Computer's " + game.computer.army.get(x).name + " attacked player's " + system[hx][hy].name);
										
										//performed action
										actionPerformed = true;
									}
								}
							}
						}
					}
				}
				//if(tries > 1000) {
				//	System.out.println("too many tries");
				//	break;
				//}
			}while(!actionPerformed);
		}
		
		//increment turn timer
		game.turn++;
	}
	
	private class ClickAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton b = (JButton) e.getSource();
			Point p = (Point) b.getClientProperty("position");
			//System.out.println(p.x + ", " + p.y);
			
			boolean actionPerformed = false;
			
			//user clicked on main board
			if(p.x >= 0) {
				//if user clicked on empty space
				if(system[p.x][p.y].name.equals("undefined")) {
					//if last click was on spawn grid
					if(lastButton.x < 0) {
						//if last click was on unit in spawn grid
						if(spawnArr[lastButton.y] >= 0) {
							
							//System.out.println("button index: " + lastButton.y + "army size: " + game.player.army.size());
							
							//spawn unit
							system[p.x][p.y] = game.player.army.get(lastButton.y);
							
							//update position of unit
							game.player.army.get(lastButton.y).location.x = p.x;
							game.player.army.get(lastButton.y).location.y = p.y;
							
							//update icons
							buttonGrid[p.x][p.y].setIcon(getScaleIcon(intToPiece(spawnArr[lastButton.y])));
							buttonGrid[p.x][p.y].setBorder(BorderFactory.createLineBorder(Color.BLUE));
							spawnButtonGrid[lastButton.y].setIcon(null);
							
							//remove unit from spawnArr
							spawnArr[lastButton.y] = -1;
							
							//claim player ownership for player
							buttonGrid[p.x][p.y].putClientProperty("playerpiece", 1);
							
							//player performed action
							actionPerformed = true;
							
							System.out.println("Player spawned " + system[p.x][p.y].name);
						}
					}
					else if(lastButton.x >= 0 && lastButton.x != 999 && (!system[lastButton.x][lastButton.y].name.equals("undefined"))) {// if last click was on spawned unit
						//if unit was the player's
						if((int) buttonGrid[lastButton.x][lastButton.y].getClientProperty("playerpiece") == 1) {
							//if the distance is 1 space
							if(getDistance(lastButton.x, lastButton.y, p.x, p.y) <= 1.0) {
								//move the piece from lastButton to current button
								system[p.x][p.y] = system[lastButton.x][lastButton.y];
								system[lastButton.x][lastButton.y] = new Piece();
								
								//update position of unit
								system[p.x][p.y].location.x = p.x;
								system[p.x][p.y].location.y = p.y;
								
								//update icons
								buttonGrid[p.x][p.y].setIcon(getScaleIcon(intToPiece(linker.indexOf(system[p.x][p.y].name))));
								buttonGrid[p.x][p.y].setBorder(BorderFactory.createLineBorder(Color.BLUE));
								
								//clear old space
								buttonGrid[lastButton.x][lastButton.y].setIcon(null);
								//buttonGrid[lastButton.x][lastButton.y].setBorder(UIManager.getBorder("Button.border"));
								lastBorder = UIManager.getBorder("Button.border");
								
								//update ownership of spaces
								buttonGrid[p.x][p.y].putClientProperty("playerpiece", 1);
								buttonGrid[lastButton.x][lastButton.y].putClientProperty("playerpiece", 0);
								
								//player performed action
								actionPerformed = true;
								
								System.out.println("Player moved " + system[p.x][p.y].name + " " + lastButton.x + ", " + lastButton.y + " to " + p.x + ", " + p.y);
							}
						}
					}
				}
				else if ((int) buttonGrid[p.x][p.y].getClientProperty("playerpiece") == 2) {//if user clicked on enemy unit
					//if last click was player piece
					if ((int) buttonGrid[lastButton.x][lastButton.y].getClientProperty("playerpiece") == 1) {
						//if in range
						if (getDistance(system[lastButton.x][lastButton.y], system[p.x][p.y]) <= system[lastButton.x][lastButton.y].range) {
							//if attack kills unit
							if (system[lastButton.x][lastButton.y].attack >= system[p.x][p.y].health) {
								//if killed unit is hero
								if (system[p.x][p.y].name.equals(game.computer.hero.name)) {
									//kill hero
									system[p.x][p.y].health = 0;
									game.computer.hero.health = 0;
									
									//remove hero from game board
									system[p.x][p.y] = new Piece();
									buttonGrid[p.x][p.y].setIcon(null);
									
									//alert player of victory
									JOptionPane.showMessageDialog(null, "Victory", "Victory", JOptionPane.INFORMATION_MESSAGE);
									//exit game
									System.exit(0);
								}
								else {//if killed unit is not hero
									//kill unit
									system[p.x][p.y].health = 0;
									
									System.out.println("Player's " + system[lastButton.x][lastButton.y].name + " killed enemy " + system[p.x][p.y].name);
									
									//remove unit from game board
									system[p.x][p.y] = new Piece();
									buttonGrid[p.x][p.y].setIcon(null);
									buttonGrid[p.x][p.y].setBorder(UIManager.getBorder("Button.border"));
									buttonGrid[p.x][p.y].putClientProperty("playerpiece", 0);
									
									//player performed action
									actionPerformed = true;
								}
							}
							else if (system[lastButton.x][lastButton.y].attack < system[p.x][p.y].health) {//if attack wounds unit
								//damage unit
								system[p.x][p.y].health -= system[lastButton.x][lastButton.y].attack;
								
								System.out.println("Player's " + system[lastButton.x][lastButton.y].name + " attacked enemy " + system[p.x][p.y].name);
								
								//player performed action
								actionPerformed = true;
							}
						}
					}
				}
			}
			
			//change border of last button to it's previous stats
			if(lastButton.x >= 0 && lastButton.x <= 7 && lastButton.y >= 0 && lastButton.y <= 7) {
				buttonGrid[lastButton.x][lastButton.y].setBorder(lastBorder);
			}
			
			//update last button
			lastButton.x = p.x;
			lastButton.y = p.y;
			
			if(p.x >= 0 && p.x <= 7 && p.y >= 0 && p.y <= 7) {
				lastBorder = buttonGrid[p.x][p.y].getBorder();
				buttonGrid[p.x][p.y].setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			}
			
			if(actionPerformed) {
				nextMove();
			}
		}
		
	}
	
	public class ChangeHandler implements ChangeListener {

        private Point point;
        private JLabel label;

        public ChangeHandler(Point point, JLabel label) {
        	this.point = point;
        	this.label = label;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            ButtonModel model = (ButtonModel) e.getSource();
            String output = "";
            int x = point.x;
            int y = point.y;
            
            if (model.isRollover()) {
                //set icon to stats of unit
            	
            	//if in main board
            	if (x > -1) {
            		//if space is not empty
            		if (((int) buttonGrid[x][y].getClientProperty("playerpiece") != 0)) {
            			//if piece is a hero
            			if (system[x][y].name.equals(game.player.hero.name) || system[x][y].name.equals(game.computer.hero.name)) {
            				output = "hero | " + system[x][y].name + " | attack: " + system[x][y].attack + " | health: " + system[x][y].health + " | range: " + system[x][y].range;
            			}
            			else {
            				output = system[x][y].name + " | attack: " + system[x][y].attack + " | health: " + system[x][y].health + " | range: " + system[x][y].range;
            			}
            		}
            	}
            	else {//if on spawngrid
            		//if space is not empty
            		if (game.player.army.get(y).location.x == -1) {
            			//if piece is hero
            			if (game.player.army.get(y).name.equals(game.player.hero.name)) {
            				output = "hero | " + game.player.army.get(y).name + " | attack: " + game.player.army.get(y).attack + " | health: " + game.player.army.get(y).health + " | range: " + game.player.army.get(y).range;
            			}
            			else {
            				output = game.player.army.get(y).name + " | attack: " + game.player.army.get(y).attack + " | health: " + game.player.army.get(y).health + " | range: " + game.player.army.get(y).range;
            			}
            		}
            	}
            	
            	
            	label.setText(output);
            } else {
                label.setText("");
            }
        }

    }
	
}