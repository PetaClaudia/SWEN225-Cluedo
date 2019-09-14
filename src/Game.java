/**
 * PLEASE NOTE:
 * To run our game please ensure that all images are NOT in the src file but the main project file.
 * If this is not done images do not load.
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.*; 

/**
 * A class for the main aspects of the game.
 * @author Marianne Trye
 * @author Peta Douglas
 *
 */
public class Game {

	List<Token> players = new ArrayList<Token>();	
	List<Weapon> weaponCards;
	List<Suspect> suspectCards;
	List<Room> roomCards;
	List<Token> otherSuspects = new ArrayList<Token>();	
	List<Token> allweapons = new ArrayList<Token>();
	HashMap<String,String> namemapped=new HashMap<String,String> ();	
	HashMap<String,Room> mapped=new HashMap<String,Room> ();
	HashMap<Room,Integer> numMapped=new HashMap<Room,Integer> ();
	HashMap<Token, Room> previousRoom=new HashMap<Token, Room> ();
	Stack<Card> deck;
	Card envelope[] = new Card[3];
	Board board = new Board();
	boolean gameWon = false;
	boolean successfulMove=false;
	String murderer;
	String weapon;
	String accuse="";
	static JFrame f;
	int n = 0;
	int pn;
	int plCount = 0;
	int count = 0;
	Token suspectA;
	Token weaponA;
	Room roomA;
	Card showedA;


	public void start() {

		JButton start = new JButton("   Start   ");
		JButton help = new JButton("?");
		f.add(start);
		f.add(help);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		File file = new File("cluedo.png");
		BufferedImage img;
		try {
			img = ImageIO.read(file);
			ImageIcon icon = new ImageIcon(img);
			JLabel lbl = new JLabel();
			lbl.setIcon(icon);
			f.add(lbl, 0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.setVisible(true);  
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				f.getContentPane().removeAll();
				f.repaint();
				setup();
			}   
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				JDialog d = new JDialog(f,"help");
				JPanel p = new JPanel(); 
				File file = new File("help.png");
				BufferedImage img;
				try {
					img = ImageIO.read(file);
					ImageIcon icon = new ImageIcon(img);
					JLabel lbl = new JLabel();
					lbl.setIcon(icon);
					p.add(lbl, 0);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JButton b=new JButton("back");
				b.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						d.dispose();
					}
				});
				p.add(b);
				d.add(p); 
				d.setSize(350, 500); 
				d.setVisible(true); 
			}   
		});

	}
	/**
	 * Sets everything up and calls starts the game
	 */
	public void setup() {
		getDeck();
		getNumTokens();

	}


	/**
	 * Fills each list with it's respective cards.
	 * Selects the murderer, crime scene location and murder weapon.
	 * Creates the deck.
	 */
	public void getDeck() {
		weaponCards = new ArrayList<Weapon>();
		suspectCards = new ArrayList<Suspect>();
		roomCards = new ArrayList<Room>();
		deck = new Stack<Card>();

		//add weapons
		weaponCards.add(new Weapon("Candlestick"));
		weaponCards.add(new Weapon("Lead Pipe"));
		weaponCards.add(new Weapon("Revolver"));
		weaponCards.add(new Weapon("Knife"));
		weaponCards.add(new Weapon("Wrench"));
		weaponCards.add(new Weapon("Rope"));



		//add suspects
		suspectCards.add(new Suspect("Mrs. White"));
		suspectCards.add(new Suspect("Miss Scarlett"));
		suspectCards.add(new Suspect("Professor Plum"));
		suspectCards.add(new Suspect("Colonel Mustard"));
		suspectCards.add(new Suspect("Mr. Green"));
		suspectCards.add(new Suspect("Mrs. Peacock"));



		//add rooms
		roomCards.add(new Room("Kitchen"));
		roomCards.add(new Room("Conservatory"));
		roomCards.add(new Room("Study"));
		roomCards.add(new Room("Billiards Room"));
		roomCards.add(new Room("Hall"));
		roomCards.add(new Room("Dining Room"));
		roomCards.add(new Room("Ballroom"));
		roomCards.add(new Room("Lounge"));
		roomCards.add(new Room("Library"));

		mapped.put("Kitchen",roomCards.get(0));
		mapped.put("Conservatory",roomCards.get(1));
		mapped.put("Study",roomCards.get(2));
		mapped.put("Billiards Room",roomCards.get(3));
		mapped.put("Hall",roomCards.get(4));
		mapped.put("Dining Room",roomCards.get(5));
		mapped.put("Ballroom",roomCards.get(6));
		mapped.put("Lounge",roomCards.get(7));
		mapped.put("Library",roomCards.get(8));


		numMapped.put(roomCards.get(0),8);
		numMapped.put(roomCards.get(1),6);
		numMapped.put(roomCards.get(2),3);
		numMapped.put(roomCards.get(3),5);
		numMapped.put(roomCards.get(4),2);
		numMapped.put(roomCards.get(5),9);
		numMapped.put(roomCards.get(6),7);
		numMapped.put(roomCards.get(7),1);
		numMapped.put(roomCards.get(8),4);


		//shuffle cards
		Collections.shuffle(weaponCards);
		Collections.shuffle(suspectCards);
		Collections.shuffle(roomCards);

		//assign finals
		Room crimeScene = roomCards.get(roomCards.size()-1);
		Weapon murderWeapon = weaponCards.get(weaponCards.size()-1);
		Suspect murderer = suspectCards.get(suspectCards.size()-1);

		//remove finals from cards
		weaponCards.remove(weaponCards.size()-1);
		suspectCards.remove(suspectCards.size()-1);
		roomCards.remove(roomCards.size()-1);

		//add finals to envelope
		envelope = new Card[] {murderer, murderWeapon, crimeScene};

		//create the deck
		for(Suspect s : suspectCards) {
			deck.push(s);
		}
		for(Weapon w : weaponCards) {
			deck.push(w);

		}
		for(Room r : roomCards) {
			deck.push(r);
		}

		//shuffle the deck
		Collections.shuffle(deck);
	}


	/**
	 * Finds the number of players.
	 * 
	 */
	private void getNumTokens() {
		JLabel l = new JLabel("How many players?");
		JRadioButton three = new JRadioButton("3");
		JRadioButton four = new JRadioButton("4");
		JRadioButton five = new JRadioButton("5");
		JRadioButton six = new JRadioButton("6");
		ButtonGroup pNum = new ButtonGroup();
		pNum.add(three);
		pNum.add(four);
		pNum.add(five);
		pNum.add(six); 
		f.add(l);
		f.add(three);
		f.add(four);
		f.add(five);
		f.add(six);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  

		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){  
				n = 3;
				f.getContentPane().removeAll();
				f.repaint();
				getTokens();
			}   
		});
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){        
				n = 4;    
				f.getContentPane().removeAll();
				f.repaint();
				getTokens();
			}   
		});
		five.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){        
				n = 5;   
				f.getContentPane().removeAll();
				f.repaint();
				getTokens();
			}   
		});
		six.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){        
				n = 6;    
				f.getContentPane().removeAll();
				f.repaint();
				getTokens();
			}   
		});

	}

	private String[] characters = {null, "1: Mrs. White", "2: Miss Scarlett", "3: Colonel Mustard", 
			"4: Professor Plum", "5: Mrs. Peacock", "6: Mr. Green"};

	/**
	 * Players choose what character they want to be.
	 * Player can add their name
	 * Player is added to the list of players.
	 */
	//nums is a list of numbers which represent which players have been taken
	ArrayList<Integer> nums=new ArrayList<Integer>();
	//nameOfP is a String for the players name
	String nameOfP = "";
	private void getTokens() {
		if(plCount>0) {
			if(nameOfP=="")
				nameOfP=players.get(plCount-1).name;
			namemapped.put(players.get(plCount-1).name,nameOfP);
			nameOfP="";
		}
		JLabel nm = new JLabel("                                          Player "+(plCount+1)+" type your name                                          ");
		f.add(nm);
		JTextField tf1 = new JTextField();
		tf1.setPreferredSize( new Dimension( 200, 24 ) );
		tf1.setBounds(100, 100,200, 24);
		tf1.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent evt){
				nameOfP = ((JTextField)evt.getSource()).getText() + String.valueOf(evt.getKeyChar());
			}
		});
		f.add(tf1);
		JLabel choose = new JLabel("                                                        Player "+(plCount+1)+", choose a character:                                                                      ");
		JRadioButton white = new JRadioButton("Mrs. White");
		JRadioButton scarlett = new JRadioButton("Miss Scarlett");
		JRadioButton mustard = new JRadioButton("Colonel Mustard");
		JRadioButton plum = new JRadioButton("Professor Plum");
		JRadioButton peacock = new JRadioButton("Mrs. Peacock");
		JRadioButton green = new JRadioButton("Mr. Green");
		ButtonGroup chars = new ButtonGroup();

		f.add(choose);
		if(characters[1]!=null) {
			f.add(white);
			chars.add(white);
		}
		if(characters[2]!=null) {
			f.add(scarlett);
			chars.add(scarlett);
		}
		if(characters[3]!=null) {
			f.add(mustard);
			chars.add(mustard);
		}
		if(characters[4]!=null) {
			f.add(plum);
			chars.add(plum);
		}
		if(characters[5]!=null) {
			f.add(peacock);
			chars.add(peacock);
		}
		if(characters[6]!=null) {
			f.add(green);
			chars.add(green);
		}
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  


		white.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				nums.add(1);
				players.add(new Token("Mrs. White", "W", 25, 10));
				characters[1] = null;
				f.getContentPane().removeAll();
				f.repaint();
				plCount++;
				if(plCount<n) {
					getTokens();
				}
				if(plCount == n) {
					sort();
					plCount = 1;
					deal();
				}
			} 
		});
		scarlett.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				nums.add(2);
				players.add(new Token("Miss Scarlett", "S", 1, 8));
				characters[2] = null;
				f.getContentPane().removeAll();
				f.repaint();
				plCount++;
				if(plCount<n) {
					getTokens();
				}
				if(plCount == n) {
					sort();
					plCount = 1;
					deal();
				}
			}  
		});
		mustard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){ 
				nums.add(3);
				players.add(new Token("Colonel Mustard", "M", 8, 1));
				characters[3] = null;
				f.getContentPane().removeAll();
				f.repaint();
				plCount++;
				if(plCount<n) {
					getTokens();
				}
				if(plCount == n) {
					sort();
					plCount = 1;
					deal();
				}
			}
		});
		plum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				nums.add(4);
				players.add(new Token("Professor Plum", "P", 6, 24));
				characters[4] = null;
				f.getContentPane().removeAll();
				f.repaint();
				plCount++;
				if(plCount<n) {
					getTokens();
				}
				if(plCount == n) {
					sort();
					plCount = 1;
					deal();
				}

			}   
		});
		peacock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				nums.add(5);
				players.add(new Token("Mrs. Peacock", "C", 19, 24));
				characters[5] = null;
				f.getContentPane().removeAll();
				f.repaint();
				plCount++;
				if(plCount<n) {
					getTokens();
				}
				if(plCount == n) {
					sort();
					plCount = 1;
					deal();
				}

			}   
		});
		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nums.add(6);
				players.add(new Token("Mr. Green", "G", 25, 16));
				characters[6] = null;
				f.getContentPane().removeAll();
				f.repaint();
				plCount++;
				if(plCount<n) {
					getTokens();
				}
				if(plCount == n) {
					sort();
					plCount = 1;
					deal();
				}
			}
		});

		//the room player was in last
		for(Token p:players) {
			previousRoom.put(p,null);
		}
		Token knife=new Token("Knife","weapon_knife.png",14,12);
		Token wrench=new Token("Wrench", "weapon_wrench.png",14,13);
		Token leadpipe=new Token("Lead Pipe","weapon_leadpipe.png",14,14);
		Token rope=new Token("Rope","weapon_rope.png",15,12);
		Token candlestick=new Token("Candlestick","weapon_candlestick.png",15,13);
		Token revolver=new Token("Revolver","weapon_revolver.png",15,14);
		allweapons.add(knife);
		allweapons.add(wrench);
		allweapons.add(leadpipe);
		allweapons.add(rope);
		allweapons.add(candlestick);
		allweapons.add(revolver);



	}

	/**
	 * Adds players which are not actually playing to a list 
	 * This is so their tokens can be moved around the board
	 */
	private void sort() {
		otherSuspects=new ArrayList<Token>();
		if(plCount<6) {
			if(!nums.contains(1)) {
				otherSuspects.add(new Token("Mrs. White", "W", 25,10));
			}
			if(!nums.contains(2)) {
				otherSuspects.add(new Token("Miss Scarlett", "S", 1,8));
			}
			if(!nums.contains(3)) {
				otherSuspects.add(new Token("Colonel Mustard", "M", 8,1));
			}
			if(!nums.contains(4)) {
				otherSuspects.add(new Token("Professor Plum", "P", 6,24));
			}
			if(!nums.contains(5)) {
				otherSuspects.add(new Token("Mrs. Peacock", "C",19,24));
			}
			if(!nums.contains(6)) {
				otherSuspects.add(new Token("Mr. Green", "G", 25,15));
			}

		}
	}





	/**
	 * Deals the cards to each player.
	 */
	private void deal() {
		boolean a=false;
		while (!deck.isEmpty()) {
			for(Token player : players) {
				player.getHand().add(deck.pop());
				if(deck.isEmpty())
					a=true;
				if(a)
					break;
			}
		}
		board.board(players);
		nextPlayer();
	}


	boolean one=false;
	/**
	 * Gets next player and allows them to have their turn
	 */
	public void nextPlayer() {
		if(!one) {
			if(nameOfP=="")
				nameOfP=players.get(players.size()-1).name;
			namemapped.put(players.get(players.size()-1).name,nameOfP);
			nameOfP="";
			one=true;
			for(String n: namemapped.keySet())
				System.out.println(n+"		"+namemapped.get(n)	);
		}
		if(pn>=n) {
			pn=0;
		}
		Token player = players.get(pn);
		if(player.hasLost) {
			pn++;
			nextPlayer();
			player = players.get(pn);
		}
		else {	
			JButton begin = new JButton("Begin "+namemapped.get(player.name)+"'s turn");
			f.add(begin);
			f.setSize(600,700);    
			f.setLayout(new FlowLayout()); 
			f.setVisible(true);  

			begin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					f.getContentPane().removeAll();
					f.repaint();
					boardit();
					System.out.println("Your hand:");

					Token player = players.get(pn);
					handit(player);

					rollit();
				}
			});
		}

	}

	/**
	 * Makes board visible
	 */
	public void boardit() {
		board.printBoard(players, f, allweapons, otherSuspects);
		f.setVisible(true);

	}

	/**
	 * Makes hand visible
	 * @param player so it is easy to find hand
	 */
	public void handit(Token player) {
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(5+65*player.getHand().size(), 110));
		layeredPane.setBorder(BorderFactory.createTitledBorder(
				"Hand"));
		int i=0;
		for (Card c : player.getHand()) {
			System.out.print(c.name + " / ");
			File file = new File(c.name+".png");
			BufferedImage img;
			try {
				img = ImageIO.read(file);
				ImageIcon icon = new ImageIcon(img);
				JLabel lbl = new JLabel();
				lbl.setIcon(icon);
				lbl.setBounds(6+65*i, 16,
						icon.getIconWidth(),
						icon.getIconHeight());

				layeredPane.add(lbl, 0);
				i++;


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		f.add(layeredPane);
		f.setVisible(true);
	}

	/**
	 * This method has the roll dice button as well as the active mouse listener
	 * So the player can move by clicking board position
	 */
	public void rollit(){
		JButton rollb = new JButton("Roll Dice");
		rollb.setBounds(100, 400, 70, 30);//x axis, y axis, width, height  
		f.add(rollb);
		JLabel l = new JLabel("Press the roll button");
		f.add(l);
		f.setVisible(true);

		rollb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();

				f.getContentPane().removeAll();
				f.repaint();
				boardit();
				handit(players.get(pn));
				int roll = rollDice();

				JLabel l = new JLabel("                                           Press the board to move!                                          ");
				f.add(l);
				JLabel a = new JLabel("This is "+namemapped.get(players.get(pn).name)+"'s turn they are "+players.get(pn).name);
				f.add(a);
				BufferedImage img;
				File file= new File("suspect_"+players.get(pn).token+".png");
				try {
					img = ImageIO.read(file);
					ImageIcon icon = new ImageIcon(img);
					//  f.setLayout(new FlowLayout());
					JLabel lbl = new JLabel();
					lbl.setIcon(icon);
					f.add(lbl);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JLabel b = new JLabel("Position: "+players.get(pn).posY+", "+players.get(pn).posX);
				f.add(b);

				f.addMouseListener( new MouseAdapter() {
					boolean enabled=true;
					@Override
					public void mouseClicked(MouseEvent e) {

						if (!enabled) {
							return;
						}
						int x=e.getX();
						int xP=1;
						int y=e.getY();
						int yP=1;
						if(y>74 && y<500 && x>94 && x<504) {
							x=x-95;
							while(x>17) {
								x=x-17;
								xP++;			        			
							}
							y=y-75;
							while(y>17) {
								y=y-17;
								yP++;			        			
							}
						}
						yP=25-yP+1;
						Token player=players.get(pn);
						move(player, roll, yP,xP);
						if(successfulMove==true) {
							enabled=false;
							playerAct();
						}
						else {
							f.remove(l);
							JLabel l = new JLabel("Not a valid move!");
							f.add(l);
							f.setVisible(true);
						}

					}
				});
				f.setVisible(true);
			}
		});
	}

	/**
	 * Finds what the players next action should be after moving
	 * If in room make suggestion
	 * Otherwise move onto next player
	 */
	public void playerAct() {
		Token player=players.get(pn);

		f.getContentPane().removeAll();
		f.repaint();
		JButton begin = new JButton("Begin "+player.name+"'s turn");
		f.add(begin);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  
		boardit();
		handit(player);
		f.setVisible(true);

		if(getRoom(player.posX, player.posY) == null) {	//not in room, therefore next player's turn

			f.getContentPane().removeAll();
			f.repaint();
			finalAccuse(player);
			pn++;
			nextPlayer();
		}else {	//inside a room, gets to make a suggestion

			f.getContentPane().removeAll();
			f.repaint();
			roomA=getRoom(player.posX, player.posY);
			makeSuggestionMurderer(player);
		}
	}

	/**
	 * Player suggests a murderer
	 * @param player
	 */
	public void makeSuggestionMurderer(Token player) {
		JLabel choose = new JLabel(player.name+" make a suggestion for the murderer:");
		JRadioButton white = new JRadioButton("Mrs. White");
		JRadioButton scarlett = new JRadioButton("Miss Scarlett");
		JRadioButton mustard = new JRadioButton("Colonel Mustard");
		JRadioButton plum = new JRadioButton("Professor Plum");
		JRadioButton peacock = new JRadioButton("Mrs. Peacock");
		JRadioButton green = new JRadioButton("Mr. Green");

		f.add(choose);
		f.add(white);
		f.add(scarlett);
		f.add(mustard);
		f.add(plum);
		f.add(peacock);
		f.add(green);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  

		white.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Mrs. White";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						QItem a=placeinRoom(roomA, p);
						p.posX=a.row;
						p.posY=a.col;
						wap=true;
						suspectA=p;
						makeSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							QItem a=placeinRoom(roomA, p);
							suspectA=p;
							p.posX=a.row;
							p.posY=a.col;
							makeSuggestionWeapon(player);
						}
					}
			}
		});

		scarlett.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Miss Scarlett";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						QItem a=placeinRoom(roomA, p);
						p.posX=a.row;
						p.posY=a.col;
						wap=true;
						suspectA=p;
						makeSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							QItem a=placeinRoom(roomA, p);
							suspectA=p;
							p.posX=a.row;
							p.posY=a.col;
							makeSuggestionWeapon(player);
						}
					}
			}
		});

		mustard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Colonel Mustard";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						QItem a=placeinRoom(roomA, p);
						p.posX=a.row;
						p.posY=a.col;
						wap=true;
						suspectA=p;
						makeSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							QItem a=placeinRoom(roomA, p);
							suspectA=p;
							p.posX=a.row;
							p.posY=a.col;
							makeSuggestionWeapon(player);
						}
					}
			}
		});


		plum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Professor Plum";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						QItem a=placeinRoom(roomA, p);
						p.posX=a.row;
						p.posY=a.col;
						wap=true;
						suspectA=p;
						makeSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							QItem a=placeinRoom(roomA, p);
							suspectA=p;
							p.posX=a.row;
							p.posY=a.col;
							makeSuggestionWeapon(player);
						}
					}
			}
		});


		peacock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Mrs. Peacock";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						QItem a=placeinRoom(roomA, p);
						p.posX=a.row;
						p.posY=a.col;
						wap=true;
						suspectA=p;
						makeSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							QItem a=placeinRoom(roomA, p);
							suspectA=p;
							p.posX=a.row;
							p.posY=a.col;
							makeSuggestionWeapon(player);
						}
					}
			}
		});


		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Mr. Green";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						QItem a=placeinRoom(roomA, p);
						p.posX=a.row;
						p.posY=a.col;
						wap=true;
						suspectA=p;
						makeSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							QItem a=placeinRoom(roomA, p);
							suspectA=p;
							p.posX=a.row;
							p.posY=a.col;
							makeSuggestionWeapon(player);
						}
					}
			}
		});


	}

	/**
	 * Player suggests a weapon
	 * @param player
	 */
	public void makeSuggestionWeapon(Token player) {
		JLabel choose = new JLabel(player.name+" make a suggestion for the weapon:");
		JRadioButton kn = new JRadioButton("Knife");
		JRadioButton le = new JRadioButton("Lead Pipe");
		JRadioButton ro = new JRadioButton("Rope");
		JRadioButton wr = new JRadioButton("wrench");
		JRadioButton ca = new JRadioButton("CandleStick");
		JRadioButton re = new JRadioButton("Revolver");

		f.add(choose);
		f.add(kn);
		f.add(le);
		f.add(ro);
		f.add(wr);
		f.add(ca);
		f.add(re);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  

		kn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				System.out.println("accusing knife");
				accuse="Knife";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						QItem b=placeinRoom(roomA, w);
						w.posX=b.row;
						w.posY=b.col;
						weaponA=w;
					}
				}
				passit(player,pn+1);
			}
		});
		le.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				System.out.println("lead pipe");
				accuse="Lead Pipe";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						QItem b=placeinRoom(roomA, w);
						w.posX=b.row;
						w.posY=b.col;
						weaponA=w;
					}
				}
				passit(player,pn+1);
			}
		});
		ro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				System.out.println("accusing rope");
				accuse="Rope";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						QItem b=placeinRoom(roomA, w);
						w.posX=b.row;
						w.posY=b.col;
						weaponA=w;
					}
				}
				passit(player,pn+1);
			}
		});
		wr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				System.out.println("accusing wrench");
				accuse="Wrench";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						QItem b=placeinRoom(roomA, w);
						w.posX=b.row;
						w.posY=b.col;
						weaponA=w;
					}
				}
				passit(player,pn+1);
			}
		});
		ca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				System.out.println("accusing candlestick");
				accuse="Candlestick";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						QItem b=placeinRoom(roomA, w);
						w.posX=b.row;
						w.posY=b.col;
						weaponA=w;
					}
				}
				passit(player,pn+1);
			}
		});
		re.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Revolver";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						QItem b=placeinRoom(roomA, w);
						w.posX=b.row;
						w.posY=b.col;
						weaponA=w;
					}
				}
				passit(player,pn+1);
			}
		});

	}


	/**
	 * Passes it onto the next player to check if they have suggested cards
	 * @param player is player making suggestion
	 * @param num is the player they are up to passing
	 */
	public void passit(Token player, Integer num) {
		if(num>=players.size())
			num=0;

		Integer number=num+1;
		Token nPlayer=players.get(num);
		JButton begin = new JButton("Pass to "+nPlayer.name);
		f.add(begin);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  
		int oldnum=num;
		begin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(oldnum==pn) {
					//it is back to original player
					System.out.println("Pass back to player");
					f.getContentPane().removeAll();
					f.repaint();
					showit(player,null,null);
				}
				else {
					System.out.println("person");
					f.getContentPane().removeAll();
					f.repaint();
					System.out.println("Your hand:");
					JLabel name = new JLabel(nPlayer.name);
					f.add(name);
					ArrayList<Card> cardsTheyHave=new ArrayList<Card>();

					handit(nPlayer);
					JLabel choose = new JLabel(player.name+" accused "+suspectA.name+" with the "+weaponA.name+" in the "+roomA.name);
					f.add(choose);
					for(Card a:nPlayer.getHand()) {
						if(a.name==suspectA.name||a.name==weaponA.name||a.name==roomA.name) {
							cardsTheyHave.add(a);
						}
					}
					if(cardsTheyHave.size()==0) {
						JLabel nope = new JLabel("You don't have any of the accused cards. Please press 'Pass' button.");
						f.add(nope);
						JButton begin = new JButton("Pass");
						f.add(begin);
						begin.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e){
								f.getContentPane().removeAll();
								f.repaint();
								passit(player,number);
							}
						});
					}
					else {
						for(Card c:cardsTheyHave) {
							JButton a = new JButton("Show "+c.name);
							f.add(a);
							a.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e){
									f.getContentPane().removeAll();
									f.repaint();
									showit(player, c, nPlayer);
								}
							});
						}

					}
					f.setVisible(true);
				}
			}
		}); 

	}


	/**
	 * What player sees after asking others if they have cards
	 * @param player is whose turn it is
	 * @param c is card shown
	 * @param shownBy is who showed that card
	 */
	public void showit(Token player, Card c, Token shownBy) {
		if(c==null) {
			JButton begin = new JButton("No body showed you anything");
			finalAccuse(player);
			f.add(begin);
			f.setSize(600,700);    
			f.setLayout(new FlowLayout()); 
			f.setVisible(true);  
			begin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					f.getContentPane().removeAll();
					f.repaint();

					pn++;
					nextPlayer();
				}
			});		
		}
		else {
			JButton begin = new JButton("Pass to "+player.name+"'s turn");
			f.add(begin);
			f.setSize(600,700);    
			f.setLayout(new FlowLayout()); 
			f.setVisible(true);  
			begin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					System.out.println("person");
					f.getContentPane().removeAll();
					f.repaint();

					JLabel l = new JLabel(shownBy.name+" showed you");
					f.add(l);
					File file = new File(c.name+".png");
					BufferedImage img;
					try {
						img = ImageIO.read(file);
						ImageIcon icon = new ImageIcon(img);
						f.setLayout(new FlowLayout());
						JLabel lbl = new JLabel();
						lbl.setIcon(icon);
						f.add(lbl);
						f.setVisible(true);
						f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					finalAccuse(player);
					JButton next = new JButton("End turn");
					f.add(next);
					f.setVisible(true);
					//TODO final accusationf.add(begin);
					next.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e){
							f.getContentPane().removeAll();
							f.repaint();

							pn++;
							nextPlayer();
						}
					});		

				}
			});
		}
	}

	private Token finalMurderer;
	private Token finalWeapon;
	private Room finalRoom;
	/**
	 * Making the final accusations
	 * @param player is player making final accusation
	 */
	public void finalAccuse(Token player) {
		JButton acccuse = new JButton("Make final Accusation");
		f.add(acccuse);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  
		acccuse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("person");
				f.getContentPane().removeAll();
				f.repaint();
				finalSuggestionMurderer(player);

			}
		});


	}


	/**
	 * Final murderer suggestion
	 * @param player making this suggestion
	 */
	public void finalSuggestionMurderer(Token player) {
		JLabel choose = new JLabel(player.name+" make a suggestion for the murderer:");
		JRadioButton white = new JRadioButton("Mrs. White");
		JRadioButton scarlett = new JRadioButton("Miss Scarlett");
		JRadioButton mustard = new JRadioButton("Colonel Mustard");
		JRadioButton plum = new JRadioButton("Professor Plum");
		JRadioButton peacock = new JRadioButton("Mrs. Peacock");
		JRadioButton green = new JRadioButton("Mr. Green");

		f.add(choose);
		f.add(white);
		f.add(scarlett);
		f.add(mustard);
		f.add(plum);
		f.add(peacock);
		f.add(green);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  

		white.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Mrs. White";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						wap=true;
						finalMurderer=p;
						finalSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							finalMurderer=p;
							finalSuggestionWeapon(player);
						}
					}
			}
		});

		scarlett.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Miss Scarlett";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						wap=true;
						finalMurderer=p;
						finalSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							finalMurderer=p;
							finalSuggestionWeapon(player);
						}
					}
			}
		});

		mustard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Colonel Mustard";
				boolean wap=false;
				for(Token p:players) {
					System.out.println("oh"+p.name+" "+accuse);
					if(p.name.equals(accuse)) {
						wap=true;
						finalMurderer=p;
						finalSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							finalMurderer=p;
							finalSuggestionWeapon(player);
						}
					}
			}
		});


		plum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Professor Plum";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						wap=true;
						finalMurderer=p;
						finalSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							finalMurderer=p;
							finalSuggestionWeapon(player);
						}
					}
			}
		});


		peacock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Mrs. Peacock";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						wap=true;
						finalMurderer=p;
						finalSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							finalMurderer=p;
							finalSuggestionWeapon(player);
						}
					}
			}
		});


		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Mr. Green";
				boolean wap=false;
				for(Token p:players) {
					if(p.name.equals(accuse)) {
						wap=true;
						finalMurderer=p;
						finalSuggestionWeapon(player);
					}
				}
				if(!wap)
					for(Token p:otherSuspects) {
						if(p.name.equals(accuse)) {
							finalMurderer=p;
							finalSuggestionWeapon(player);
						}
					}
			}
		});
	}

	/**
	 * Final suggestion of weapon
	 * @param player making suggestion
	 */
	public void finalSuggestionWeapon(Token player) {
		JLabel choose = new JLabel(player.name+" make an accusation for the weapon:");
		JRadioButton kn = new JRadioButton("Knife");
		JRadioButton le = new JRadioButton("Lead Pipe");
		JRadioButton ro = new JRadioButton("Rope");
		JRadioButton wr = new JRadioButton("wrench");
		JRadioButton ca = new JRadioButton("CandleStick");
		JRadioButton re = new JRadioButton("Revolver");

		f.add(choose);
		f.add(kn);
		f.add(le);
		f.add(ro);
		f.add(wr);
		f.add(ca);
		f.add(re);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  

		kn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Knife";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						finalWeapon=w;
					}
				}
				finalSuggestionRoom(player);
			}
		});
		le.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Lead Pipe";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						finalWeapon=w;
					}
				}
				finalSuggestionRoom(player);
			}
		});
		ro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Rope";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						finalWeapon=w;
					}
				}
				finalSuggestionRoom(player);
			}
		});
		wr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Wrench";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						finalWeapon=w;
					}
				}
				finalSuggestionRoom(player);
			}
		});
		ca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Candlestick";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						finalWeapon=w;
					}
				}
				finalSuggestionRoom(player);
			}
		});
		re.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Revolver";
				for(Token w:allweapons) {
					if(w.name==accuse) {
						finalWeapon=w;
					}
				}
				finalSuggestionRoom(player);
			}
		});

	}



	/**
	 * Final suggestion of room
	 * @param player
	 */
	public void finalSuggestionRoom(Token player) {
		JLabel choose = new JLabel(player.name+" make an accusation for the room:");
		JRadioButton ba= new JRadioButton("Ballroom");
		JRadioButton bi= new JRadioButton("Billiards Room");
		JRadioButton co= new JRadioButton("Conservatory");
		JRadioButton di= new JRadioButton("Dining Room");
		JRadioButton ha= new JRadioButton("Hall");
		JRadioButton ki= new JRadioButton("Kitchen");
		JRadioButton li= new JRadioButton("Library");
		JRadioButton lo= new JRadioButton("Lounge");
		JRadioButton st= new JRadioButton("Study");

		f.add(choose);
		f.add(ba);
		f.add(bi);
		f.add(co);
		f.add(di);
		f.add(ha);
		f.add(ki);
		f.add(li);
		f.add(lo);
		f.add(st);
		f.setSize(600,700);    
		f.setLayout(new FlowLayout()); 
		f.setVisible(true);  

		ba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Ballroom";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
		bi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Billiards Room";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
		co.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Conservatory";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
		di.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Dining Room";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});

		ha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Hall";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
		ki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Kitchen";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
		li.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Library";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
		lo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Lounge";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
		st.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				f.getContentPane().removeAll();
				f.repaint();
				accuse="Study";
				for(Room r:numMapped.keySet()) {
					if(r.name==accuse) {
						finalRoom=r;
					}
				}
				finalSuggestion(player);
			}
		});
	}


	/**
	 * The actual final suggestion
	 * @param player
	 */
	public void finalSuggestion(Token player){
		System.out.println(player.name+" accuses "+finalMurderer.name+" with the "+finalWeapon.name+" in the "+finalRoom.name);
		if (finalMurderer.name.equals(envelope[0].name) && finalWeapon.name.equals(envelope[1].name)
				&& finalRoom.name.equals(envelope[2].name)){
			gameWon=true;
			JLabel win = new JLabel(player.name+" has won! CONGRATULATIONS");
			f.add(win);
			f.setVisible(true);
		}
		else {
			JButton begin = new JButton("You lost");
			player.hasLost=true;
			f.add(begin);
			f.setSize(600,700);    
			f.setLayout(new FlowLayout()); 
			f.setVisible(true);  

			begin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					f.getContentPane().removeAll();
					f.repaint();
					pn++;
					nextPlayer();
				}
			});

		}

	}


	/**
	 * Rolls the dice.
	 * @return result	A number between 2 and 12 (Possibilities from 2 dice).
	 */
	public int rollDice() {
		int d1 = 1 + (int)(Math.random() * 5);
		int d2 = 1 + (int)(Math.random() * 5);

		File file = new File("dice_"+d1+".png");
		BufferedImage img;
		try {
			img = ImageIO.read(file);
			ImageIcon icon = new ImageIcon(img);
			f.setLayout(new FlowLayout());
			JLabel lbl = new JLabel();
			lbl.setIcon(icon);
			f.add(lbl);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file = new File("dice_"+d2+".png");
		try {
			img = ImageIO.read(file);
			ImageIcon icon = new ImageIcon(img);
			f.setLayout(new FlowLayout());
			JLabel lbl = new JLabel();
			lbl.setIcon(icon);
			f.add(lbl);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d1+d2;
	}


	/**
	 * User inputs where they want to go. If they try and move too far, 
	 * they have to choose again unill they've chosen a position they can reach.
	 * 
	 * @param player 	The current player whose move it is
	 * @param roll 		The dice result, max number of spaces they can move
	 */
	private void move(Token player, int roll, int yP, int xP) {

		System.out.println("H5EX: "+xP);
		System.out.println("HrY: "+yP);
		int row=player.posX;
		int column=player.posY;
		Room room=getRoom(yP,xP);
		Room currentRoom=getRoom(player.posX, player.posY);
		if(room!=null) {
			System.out.println("IN A ROOM");

			if(previousRoom.get(player)!=null) {
				if(room.name==previousRoom.get(player).name) {
					System.out.println("You tried to visit the room you were just in");

					successfulMove=false;
				}
			}
			else {	//put player in the room
				List<QItem> doors=getDoors(room);
				boolean vm=false;
				if(currentRoom==null) {
					for(QItem d: doors) {
						int validMove = board.minDistance(player.posX, player.posY, d.row, d.col);
						if(validMove < roll && validMove!=-1) {
							previousRoom.put(player, room);
							vm=true;
							QItem a=placeinRoom(room,player);
							row=a.row;
							column=a.col;
							successfulMove=true;
							break;
						}
					}
				}
				else {
					//player is already in the room
					List<QItem> cDoors=getDoors(currentRoom);
					for(QItem c: cDoors) {
						for(QItem d: doors) {
							int validMove = board.minDistance(c.row, c.col, d.row, d.col);

							if(validMove!=-1 && validMove < roll-1) {
								previousRoom.put(player, room);
								vm=true;
								successfulMove=true;
								QItem a=placeinRoom(room,player);
								row=a.row;
								column=a.col;
								break;
							}
						}
						if(vm)
							break;
					}
				}
				if(!vm) {
					System.out.println("Sorry, you could not get to that room");

					successfulMove=false;
				}
			}
		}
		else {
			if(currentRoom==null) {
				//boolean validMove = roll>board.minDistance(board.board, player.posX, player.posY, row, column);
				int validMove = board.minDistance(player.posX, player.posY, yP, xP);
				System.out.println(roll);
				System.out.println("min"+validMove);
				if(validMove > roll) {
					successfulMove=false;
					System.out.println("Move is greater than dice roll. Try again.");
				}


				else {
					System.out.println("valid m");
					successfulMove=true;
					row=yP;
					column=xP;
				}
			}
			else {
				List<QItem> cDoors=getDoors(room);
				boolean vm=false;
				for(QItem c: cDoors) {
					//boolean validMove = roll>board.minDistance(board.board, player.posX, player.posY, row, column);
					int validMove = board.minDistance(c.row, c.col, yP, xP);

					if((validMove > roll) || (!(board.board[row][column]==(null)||board.board[row][column]==("_"))) || (!(getRoom(row,column)==null))) {
						successfulMove=false;
					}
					else {
						successfulMove=true;
						vm=true;
						break;
					}
				}
				if(vm) {
					System.out.println("valid m");
					successfulMove=true;
					row=yP;
					column=xP;
				}else {

					successfulMove=false;
					System.out.println("Invalid move from "+currentRoom.name);

				}
			}
		}
		board.board[player.posX][player.posY]="_";
		player.posX = row;
		player.posY = column;
		System.out.println(player.name);
		System.out.println("HEREX: "+player.posX);
		System.out.println("HEREY: "+player.posY);
	}


	/**
	 * Finds which squares make up each room. If null, they character isn't in a room.
	 * @param row 	X position for 2D array
	 * @param col 	Y position for 2D array
	 * @return Room that player is in
	 */
	private Room getRoom(int row, int col) {
		//Hall
		for(int i=0; i<8; i++){
			for(int j=10; j<16; j++){
				if(row==i&&col==j) {
					return mapped.get("Hall");
				}
			}
		}

		//Lounge
		for(int i=0; i<7; i++){
			for(int j=0; j<8; j++){
				if(row==i&&col==j) {
					return mapped.get("Lounge");
				}
			}
		}
		//Study
		for(int i=0; i<5; i++){
			for(int j=18; j<25; j++){
				if(row==i&&col==j && !(i==1&&j==18)) {
					return mapped.get("Study");
				}
			}
		}


		//Library
		for(int i=7; i<12; i++){
			for(int j=18; j<25; j++){
				if(row==i&&col==j && (!(i==7&&j==24))&&(!(i==7&&j==18))&&(!(i==11&&j==24))&&(!(i==11&&j==18))){
					return mapped.get("Library");
				}
			}
		}

		//Billards Room
		for(int i=13; i<18; i++){
			for(int j=19; j<25; j++){
				if(row==i&&col==j ) {
					return mapped.get("Billiards Room");
				}
			}
		}

		//conservatory room
		for(int i=20; i<25; i++){
			for(int j=19; j<25; j++){
				if(row==i&&col==j&& (!(i==20&&j==24))&&(!(i==20&&j==19))) {
					return mapped.get("Conservatory");
				}
			}
		}
		//ballroom
		for(int i=18; i<25; i++){
			for(int j=9; j<17; j++){
				if(row==i&&col==j&& (!(i==24&&j==9))&& (!(i==24&&j==10))&& (!(i==24&&j==15)&& (!(i==24&&j==16)))) {
					return mapped.get("Ballroom");
				}
			}
		}

		//kitchen
		for(int i=19; i<25; i++){
			for(int j=0; j<7; j++){
				if(row==i&&col==j && (!(j==1 && i==19))) {
					return mapped.get("Kitchen");
				}
			}
		}

		//Dining Room
		for(int i=10; i<17; i++){
			for(int j=0; j<9; j++){
				if(row==i&&col==j && (!(j==6 && i==16))&& (!(j==8 && i==16))&& (!(j==7 && i==16))) {
					return mapped.get("Dining Room");
				}
			}
		}
		return null;

	}






	/**
	 * Make a final accusation for who, how and where the victim was killed. 
	 * If they are right, the player wins the game. If wrong, they lose.
	 * @param player		The current Player
	 * @param murderer		Suspect they are accusing
	 * @param murderWeapon	Weapon they are accusing
	 * @param crimeScene	Location they are accusing
	 */
	public void accuse(Token player, String murderer, String murderWeapon, String crimeScene) {
		if (murderer.equals(envelope[0].name) && murderWeapon.equals(envelope[1].name)
				&& crimeScene.equals(envelope[2].name)) {
			System.out.println("Correct! The murder was committed by "+murderer+", with the "
					+murderWeapon+", in the "+crimeScene+".");
			playerWins(player);
		}
		else {
			System.out.println("Incorrect. You Lose.");
			playerLoses(player);
		}
	}


	/**
	 * Finds all the doors x and y locations in a room
	 * @param room 	The room that we want to find the doors for
	 * @return List of the doors in the room
	 */
	public List<QItem> getDoors(Room room) {
		//this will return all the doors square before
		List<QItem> a=new ArrayList<QItem>();
		if(room.name=="Lounge") {//Lounge
			QItem door=new QItem(7,7);//67
			a.add(door);
			return a;
		}
		else if(room.name=="Hall") {//Hall

			QItem door=new QItem(8,12);//712
			a.add(door);
			door=new QItem(8,13);//713
			a.add(door);
			door=new QItem(5,16);//515
			a.add(door);
			return a;
		}
		else if(room.name=="Study") {//Study
			QItem door=new QItem(5,18);//418
			a.add(door);
			return a;
		}
		else if(room.name=="Library") {//Library
			QItem door=new QItem(9,17);//918
			a.add(door);
			door=new QItem(12,21);//1121
			a.add(door);
			return a;
		}
		else if(room.name=="Billiards Room") {//Billards Room
			QItem door=new QItem(12,23);//1323
			a.add(door);
			door=new QItem(16,18);//1619
			a.add(door);
			return a;
		}
		else if(room.name=="Conservatory") {//Conservatory
			QItem door=new QItem(20,19);//2119
			a.add(door);
			return a;
		}
		else if(room.name=="Ballroom") {//Ball Room
			QItem door=new QItem(17,10);//1810
			a.add(door);
			door=new QItem(17,15);//1815
			a.add(door);
			door=new QItem(20,8);//209
			a.add(door);
			door=new QItem(20,17);//2016
			a.add(door);
			return a;
		}
		else if(room.name=="Kitchen") {//Kitchen
			QItem door=new QItem(18,5);//195
			a.add(door);
			return a;
		}
		else if(room.name=="Dining Room") {//Dining Room
			QItem door=new QItem(9,7);//107
			a.add(door);
			door=new QItem(13,9);//138
			a.add(door);
			return a;
		}
		return null;
	}



	//Press 1: lounge, 2: hall, 3: study, 4: library, 5: billards room, 6: conservatory,  7: ballroom, 8: kitchen, 9: dining room");
	private QItem placeinRoom(Room room,Token player) {
		int x=0;
		int y=0;




		if(room.name=="Lounge") {//Lounge
			for(int i=1; i<6; i++){
				for(int j=1; j<7; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Hall") {//Hall
			for(int i=1; i<7; i++){
				for(int j=11; j<15; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Study") {//Study
			for(int i=1; i<4; i++){
				for(int j=19; j<24; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Library") {//Library
			for(int i=8; i<11; i++){
				for(int j=19; j<24; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Billiards Room") {//Billards Room
			for(int i=14; i<17; i++){
				for(int j=20; j<24; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Conservatory") {//Conservatory
			for(int i=21; i<24; i++){
				for(int j=20; j<24; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Ballroom") {//Ball Room
			for(int i=19; i<24; i++){
				for(int j=10; j<16; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Kitchen") {//Kitchen
			for(int i=20; i<24; i++){
				for(int j=1; j<6; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}
		else if(room.name=="Dining Room") {//Dining Room
			for(int i=11; i<16; i++){
				for(int j=1; j<8; j++){
					if(board.board[i][j]==null||board.board[i][j]=="_") {
						x=i;
						y=j;
					}
				}
			}
		}


		return new QItem(x,y);
	}



	/**
	 * Checks if input is an integer 
	 * @param s		The string input that we are checking is an int
	 * @return		Boolean stating if it's an int or not
	 */
	public static boolean isInteger(String s) {
		boolean vi=false;
		try{
			Integer.parseInt(s);
			vi = true;
		}
		catch (NumberFormatException ex)
		{
			// s is not an integer
		}

		return vi;
	}





	/**
	 * Sets gameWon to true. Prints the winner.
	 * @param player	Current player
	 */
	private void playerWins(Token player) {
		gameWon = true;
		System.out.println(player.name+" has won!");
	}

	/**
	 * Sets hasLost to true.
	 * @param player	Current player
	 */
	private void playerLoses(Token player) {
		player.hasLost = true;
	}	






	public static void main(String[] args) throws MalformedURLException, IOException {
		//frame
		f=new JFrame("Cluedo");

		//menu
		JMenu menu;  
		JMenuItem newGame, quit;  

		JMenuBar mb=new JMenuBar();  
		menu=new JMenu("Options");  
		newGame=new JMenuItem("New Game");  
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JDialog d = new JDialog(f, "new game"); 
				JLabel l = new JLabel("Are you sure you want to start a new game?"); 
				JButton y = new JButton("Yes"); 
				JButton n = new JButton("No"); 

				y.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						f.getContentPane().removeAll();
						f.repaint();
						d.dispose();
						Game game = new Game();
						game.setup();
					}
				});
				n.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						d.dispose();
					}
				});
				JPanel p = new JPanel(); 
				p.add(l); 
				p.add(y);
				p.add(n); 
				d.add(p); 
				d.setSize(300, 100); 
				d.setVisible(true); 


			}
		});
		quit=new JMenuItem("Quit");  
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JDialog d = new JDialog(f, "quit"); 
				JLabel l = new JLabel("Are you sure you want to quit?"); 
				JButton y = new JButton("Yes"); 
				JButton n = new JButton("No"); 
				y.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						System.exit(0);
					}
				});
				n.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						d.dispose();
					}
				});
				JPanel p = new JPanel(); 

				p.add(l); 
				p.add(y);
				p.add(n); 
				d.add(p); 
				d.setSize(200, 100); 
				d.setVisible(true);
			}
		});
		menu.add(newGame); 
		menu.add(quit);

		mb.add(menu);  
		f.setJMenuBar(mb);  
		f.setSize(600,700);  
		f.setLayout(null);  
		f.setVisible(true);

		Game game = new Game();
		game.start();
	}

}

