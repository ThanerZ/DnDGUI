package randoStuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JPanel {
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//INSTANTIATION
	//Clicky Instantiation
	HandlerClass handler = new HandlerClass();
	
	
	static boolean lClickD = false, rClickD = false, mClickD = false;
	static int clicks = 0;
	
	//Startup
	boolean startupChecker = true;
	
	//JFrame
	static int width = 1000;
	static int height = 600;
	static int sleeper = 100;
	
	//Sanity
	int tracker = 0;
	
	//Clicking Variables
	boolean toggleHeroControl = true;
	
	
	//Map
	//makes it 80% of JFrame
	static int mapWidth = (width/5)*4;
	static int mapHeight = (height/5)*4;
	
	
	//Make gridWidth a number that evenly divides 1600 or scale "mapWidth" accordingly
	int gridWidth = 48;
	int gridHeight = 20;
	
	//number of obstacles
	int obstacles = 10;
	int[][] map = new int[gridWidth][gridHeight];
	
	ArrayList<Hero> party = new ArrayList<Hero>();
	int partyLength = 0;
	int partySelection = 0;
	
	ArrayList<Enemy> horde = new ArrayList<Enemy>();
	int hordeLength = 0;
	int hordeSelection = 0;
	
	ArrayList<Obstacle> course = new ArrayList<Obstacle>();
	int courseLength = 0;

	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//GUI DRAWING
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		
			
		//startup ritual
		if(startupChecker==true)
		{
			try {
				onStartup();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//fuckingCool(g);
		
			//Drawing the stuff
			drawBackground(g);
			drawMap(g);
			drawGrid(g);
			
			drawPriorityBar(g);
			
			drawPartyBar(g);
			
			drawParty(g);
			drawPriority(g);
			
			drawHorde(g);
			
			//sanity line
			g2d.setColor(this.randColor());
			g2d.drawLine(tracker, 0, tracker, mapHeight);
			tracker++;
			if (tracker>=mapWidth)
				tracker = 0;

	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//METHEODS
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Drawing
	public void fuckingCool(Graphics g)
	{
		for(int i=0; i<gridWidth; i++)
		{
			g.drawLine(i*(mapWidth/gridWidth), 0, mapHeight, i*(mapWidth/gridWidth));
		}
	}
	public void generateGrid()
	{
		for(int i=0; i<gridWidth; i++)
		{
			for(int j=0; j<gridHeight; j++)
			{
				map[i][j] = 0;
			}
		}

	}
	
	public void drawBackground(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, mapWidth, mapHeight);
	}
	public void drawGrid(Graphics g)
	{
		//Paint Background
		//paint lines/create grid
		g.setColor(Color.BLACK);
		for(int i=0; i<gridWidth; i++)
		{
			g.drawLine((i+1)*(mapWidth/gridWidth), 0, (i+1)*(mapWidth/gridWidth), mapHeight);
		}
		for(int i=0; i<gridHeight; i++)
		{
			g.drawLine(0, (i+1)*(mapHeight/gridHeight), mapWidth, (i+1)*(mapHeight/gridHeight));
		}
	}
	public void drawMap(Graphics g)
	{
		int xSpot = 0;
		int ySpot = 0;
		int tempWidth = 0;
		int tempHeight = 0;
		for(int i=0; i<courseLength; i++)
		{
			g.setColor(((Obstacle) course.get(i)).getColor());
			
				xSpot = ((Obstacle) course.get(i)).getXPos();
				ySpot = ((Obstacle) course.get(i)).getYPos();
				tempWidth = ((Obstacle) course.get(i)).getWidth();
				tempHeight = ((Obstacle) course.get(i)).getHeight();
				
			g.fillRect(xSpot*(mapWidth/gridWidth)+1, ySpot*(mapHeight/gridHeight)+1, tempWidth*(mapWidth/gridWidth)-1, tempHeight*(mapHeight/gridHeight)-1);
		}
		
	}
	
	public void drawParty(Graphics g)
	{
		int xSpot = 0;
		int ySpot = 0;
		for(int i=0; i<partyLength; i++)
		{
			g.setColor(((Hero) party.get(i)).getColor());
			xSpot = ((Hero) party.get(i)).getXPos();
			ySpot = ((Hero) party.get(i)).getYPos();
			g.fillRect(xSpot*(mapWidth/gridWidth)+1, ySpot*(mapHeight/gridHeight)+1, (mapWidth/gridWidth)-1, (mapHeight/gridHeight)-1);
		}
		
	}
	public void drawHorde(Graphics g)
	{
		
			int xSpot = 0;
			int ySpot = 0;
			int health = 0;
			for(int i=0; i<hordeLength; i++)
			{
				g.setColor(((Enemy) horde.get(i)).getColor());
				xSpot = ((Enemy) horde.get(i)).getXPos();
				ySpot = ((Enemy) horde.get(i)).getYPos();
				health = ((Enemy) horde.get(i)).getHealth();
				
				
				g.fillRect(xSpot*(mapWidth/gridWidth)+1, ySpot*(mapHeight/gridHeight)+1, (mapWidth/gridWidth)-1, (mapHeight/gridHeight)-1);
			}
			
		
	}
	
	
	
	
	public void drawPartyBar(Graphics g)
	{
		for(int i=0; i<partyLength; i++)
		{
			
			g.setColor(((Hero) party.get(i)).getColor());
			g.fillRect(mapWidth + (width-mapWidth)/8, mapHeight/(partyLength+1)*(i+1), (mapWidth/gridWidth)-1, (mapHeight/gridHeight)-1);
			g.setColor(Color.BLACK);
			g.drawString((party.get(i).getName()), (mapWidth + (width-mapWidth)/8) + (mapWidth/gridWidth*2), mapHeight/(partyLength+1)*(i+1) + (mapHeight/gridHeight/2));
			g.drawString("" + ((Hero) party.get(i)).getHealth(), mapWidth + (width-mapWidth)/8, (mapHeight/(partyLength+1)*(i+1)) + (mapHeight/gridHeight/2));
			
		}
		
		
	}
	public void drawHordeBar(Graphics g)
	{
		for(int i=0; i<hordeLength; i++)
		{
			g.setColor(((Enemy) horde.get(i)).getColor());
			g.fillRect(mapWidth + (width-mapWidth)/8, mapHeight/(hordeLength+1)*(i+1), (mapWidth/gridWidth)-1, (mapHeight/gridHeight)-1);
			
		}
	}
	
	public void drawPriority(Graphics g)
	{
		g.setColor(Color.YELLOW);
		int xSpot = 0;
		int ySpot = 0;
		
		if(toggleHeroControl)
		{
			xSpot = ((Hero) party.get(partySelection)).getXPos();
			ySpot = ((Hero) party.get(partySelection)).getYPos();
			g.drawRect(xSpot*(mapWidth/gridWidth), ySpot*(mapHeight/gridHeight), (mapWidth/gridWidth), (mapHeight/gridHeight));
		}
		else
		{
			xSpot = ((Enemy) horde.get(hordeSelection)).getXPos();
			ySpot = ((Enemy) horde.get(hordeSelection)).getYPos();
			g.drawRect(xSpot*(mapWidth/gridWidth), ySpot*(mapHeight/gridHeight), (mapWidth/gridWidth), (mapHeight/gridHeight));
		}
		
		
	}
	public void drawPriorityBar(Graphics g)
	{
		if(toggleHeroControl)
			g.setColor(Color.YELLOW);
		else
			g.setColor(new Color(255, 255, 130));
			g.fillRect(mapWidth, mapHeight/(partyLength+1)*(partySelection+1) - ((mapHeight/gridHeight)/8), width-mapWidth, (mapHeight/gridHeight)-1 + ((mapHeight/gridHeight)/4));
		
	}
	
	

	//---------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Reading in Party/Map and communicating with Handler class
	
	public void mapCreation() throws FileNotFoundException
	{
		Scanner file = new Scanner(new File("data/Map.txt"));
		gridWidth = file.nextInt();
		gridHeight = file.nextInt();

		while(file.hasNext())
			{
			file.nextLine();
			file.nextLine();
				Obstacle boxxxy = new Obstacle(file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt());

				course.add(boxxxy);
				courseLength++;
			}
	}
	
	public void hordeCreation() throws FileNotFoundException
	{
		Scanner file = new Scanner(new File("data/EnemyMap.txt"));
		
	while(file.hasNext())
		{
			Enemy Eminem = new Enemy(file.nextInt(), file.nextInt(), file.nextInt());
			
			//Color is set based on the Max HP of the Enemy
			Eminem.setColor(255-(Eminem.getHealth()*2), 0, 0);
			
			horde.add(Eminem);
			hordeLength++;
		}
	}
	
	public void partyCreation() throws FileNotFoundException
	{
		Scanner partyFile = new Scanner (new File("data/party.txt"));
		ArrayList<String> tempParty = new ArrayList<String>();
		
		while(partyFile.hasNext())
		{
			String name = partyFile.nextLine();
			tempParty.add(name);
			Scanner heroDataFile = new Scanner(new File("data/Characters/" + tempParty.get(partyLength) + ".txt"));
			
			
				Hero player = new Hero(heroDataFile.nextLine(), heroDataFile.nextInt(), heroDataFile.nextInt(), heroDataFile.nextInt(), heroDataFile.nextInt(), heroDataFile.nextInt(), heroDataFile.nextInt());
				
				heroDataFile.nextLine();
				party.add(player);
				partyLength++;
			
		}
		
		
	}



	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//CLICKING MACRO
	public void partySelectUp()
	{
		if(partySelection==partyLength-1)
			partySelection = 0;
		else
			partySelection++;
	}
	public void partySelectDown()
	{
		if(partySelection==0)
			partySelection = partyLength-1;
		else
			partySelection--;
	}
	public int getPartySelection()
	{
		return partySelection;
	}
	
	public void hordeSelectUp()
	{
		if(hordeSelection==hordeLength-1)
			hordeSelection = 0;
		else
			hordeSelection++;
	}
	public void hordeSelectDown()
	{
		if(hordeSelection==0)
			hordeSelection = hordeLength-1;
		else
			hordeSelection--;
	}
	public int getHordeSelection()
	{
		return hordeSelection;
	}
	
	public void toggleHeroControl()
	{
		if(toggleHeroControl)
		{
			toggleHeroControl = false;
			System.out.println("Contrl Toggled from Hero to Enemy");
		}
		else
		{
			toggleHeroControl = true;
			System.out.println("Contrl Toggled from Enemy to Hero");
		}
		
	}
	
	
	public boolean checkMapOccupation(int x, int y)
	{
		//returns false for a grid-space being occupied
		//occupied = false
			for(int i = 0; i<partyLength; i++)
			{
				if(x == ((Hero) party.get(i)).getXPos()  &&  y == ((Hero) party.get(i)).getYPos())
					return false;
			}
			for(int i = 0; i<hordeLength; i++)
			{
				if(x == ((Enemy) horde.get(i)).getXPos()  &&  y == ((Enemy) horde.get(i)).getYPos())
					return false;
			}
			return true;
		
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		
	//Macro's
	public void onStartup() throws FileNotFoundException
	{
		generateGrid();
		partyCreation();
		hordeCreation();
		mapCreation();
		startupChecker = false;
	}
	
	public int convertXToGrid(int x)
	{
		return x/(mapWidth/gridWidth);
	}
	public int convertYToGrid(int y)
	{
		return y/(mapHeight/gridHeight);
	}
	
	public Color randColor() {
		return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//FRAMING
	static JFrame frame = new JFrame("Thane is pretty cool");

	public static void frame() throws InterruptedException {
		GUI game = new GUI();
		
		Font f = new Font("Engravers MT", Font.BOLD, 23);
		
		//CONSTRUCTION
		/*
		text.setEditable(false);
		text.setBackground(Color.YELLOW);
		text.setFont(f);
		text.setText("Work In Progress ~~~~~~~ Under Construction");
		*/
		frame.add(game);
		//frame.add(text, BorderLayout.SOUTH);
		
		

		//adds "zergy.jpg" to the frame
		/*
	 	JLabel label = new JLabel("", image, JLabel.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add( label, BorderLayout.CENTER );
		frame.add(panel, BorderLayout.CENTER);
		*/
		
		
		
		frame.setResizable(false);
		frame.setSize(width, height);
		frame.setLocation(100, 10);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//CLICKY STUFF
		frame.addMouseListener(game.handler);
		frame.addKeyListener(game.handler);
		//frame.addMouseMotionListener(game.handler);
		
		
		
		while (true) {
			game.repaint();
			
			//STALLING
			Thread.sleep(sleeper);
		}
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
		frame();
		

	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//CLICKY CLASS

	private class HandlerClass implements MouseListener, MouseMotionListener, KeyListener {
		
		public void mouseClicked(MouseEvent event) {
		}
		public void mousePressed(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			
			if (event.getButton() == MouseEvent.BUTTON1) {
				if(toggleHeroControl)
				{
					if(x<mapWidth && y<mapHeight)
					{
						if(checkMapOccupation(convertXToGrid(x), convertYToGrid(y)))
							((Hero) party.get(getPartySelection())).setPosition(convertXToGrid(x), convertYToGrid(y));
					}
						
				}
				else
				{
					if(x<mapWidth && y<mapHeight)
					{
						if(checkMapOccupation(convertXToGrid(x), convertYToGrid(y)))
							((Enemy) horde.get(getHordeSelection())).setPosition(convertXToGrid(x), convertYToGrid(y));
					}
				}
			}
			if (event.getButton() == MouseEvent.BUTTON3) {
				
			}
			if (event.getButton() == MouseEvent.BUTTON2) {
				
			}
		}
		public void mouseReleased(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1) {
			}
			if (event.getButton() == MouseEvent.BUTTON3) {
			}
		}
		public void mouseEntered(MouseEvent event) {
		}
		public void mouseExited(MouseEvent event) {
		}
		// These are mouse motion events
		public void mouseDragged(MouseEvent event) {
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			
			//System.out.println(x + ", " + y);
			
			if (lClickD) {
			} else if (rClickD) {
			} else if (mClickD) {
			}
		}
			

		public void keyPressed(KeyEvent event) {
			// TODO Auto-generated method stub
			if(event.getKeyCode() == KeyEvent.VK_SPACE)
				toggleHeroControl();
				
			if(event.getKeyCode() == KeyEvent.VK_A)
			{
				if (toggleHeroControl) 
					partySelectDown();
				else
					hordeSelectDown();
			}
			if(event.getKeyCode() == KeyEvent.VK_D)
			{
				if (toggleHeroControl) 
					partySelectUp();
				else
					hordeSelectUp();
			}
		
		}
	
		
			
		
			
		@Override
		public void mouseMoved(MouseEvent event) {
			// TODO Auto-generated method stub

			
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	

}