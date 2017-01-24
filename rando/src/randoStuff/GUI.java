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
	
	
	//Map
	//makes it 80% of JFrame
	static int mapWidth = (width/5)*4;
	static int mapHeight = (height/5)*4;
	
	
	//Make gridWidth a number that evenly divides 1600 or scale "mapWidth" accordingly
	int gridWidth = 48;
	int gridHeight = 20;
	
	static //number of obstacles
	int obstacles = 10;
	int[][] map = new int[gridWidth][gridHeight];
	
	ArrayList<Hero> party = new ArrayList<Hero>();
	int partyLength = 0;
	int partySelection = 0;
	ArrayList<Obstacle> course = new ArrayList<Obstacle>();
	int courseLength = 0;
	ArrayList<String> names = new ArrayList<String>();
	int cursorXPos, cursorYPos;
	
	
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
			drawOrderPriority(g);
			drawOrderBar(g);
			drawName(g);
			//drawMotionArrow(g);
			drawParty(g);
			drawPartyPriority(g);
			
			
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
	
	public void drawPartyPriority(Graphics g)
	{
		g.setColor(Color.YELLOW);
		int xSpot = 0;
		int ySpot = 0;
		xSpot = ((Hero) party.get(partySelection)).getXPos();
		ySpot = ((Hero) party.get(partySelection)).getYPos();
		g.drawRect(xSpot*(mapWidth/gridWidth), ySpot*(mapHeight/gridHeight), (mapWidth/gridWidth), (mapHeight/gridHeight));
		g.setColor(new Color(255, 255, 0));
		
	}
	
	public void drawMotionArrow(Graphics g)
	{
		g.setColor(Color.YELLOW);
		
		int heroXSpot = 0;
		int heroYSpot = 0;
		heroXSpot = ((Hero) party.get(partySelection)).getXPos();
		heroYSpot = ((Hero) party.get(partySelection)).getYPos();
		g.drawLine(heroXSpot, heroYSpot, cursorXPos, cursorYPos);

	}
	
	public void drawOrderBar(Graphics g)
	{
		for(int i=0; i<partyLength; i++)
		{
			g.setColor(((Hero) party.get(i)).getColor());
			g.fillRect(mapWidth + (width-mapWidth)/8, mapHeight/(partyLength+1)*(i+1), (mapWidth/gridWidth)-1, (mapHeight/gridHeight)-1);
			
		}
		
		
	}
	
	public void drawOrderPriority(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillRect(mapWidth, mapHeight/(partyLength+1)*(partySelection+1) - ((mapHeight/gridHeight)/8), width-mapWidth, (mapHeight/gridHeight)-1 + ((mapHeight/gridHeight)/4));
	}
	
	public void drawName(Graphics g)
	{
		for(int i=0; i<partyLength; i++)
		{
			JLabel label = new JLabel(party.get(i).getName());
			label.setBounds(mapWidth + ((width-mapWidth)/4), mapHeight/(partyLength+1)*(i+1), (mapWidth/gridWidth)-1, (mapHeight/gridHeight)-1);
			frame.add(label);

		}
	}
	/*public void winner(Graphics g)
	{
		String output ="Blueberry Did It!";
		g.setColor(Color.YELLOW);
		g.clearRect(0, 0, mapWidth, mapHeight);
		g.fillRect(0, 0, mapWidth, mapHeight);
		g.setColor(Color.BLACK);
		g.drawString(output, (mapWidth/2)-(output.length()*3), (mapHeight/2)-5);
		
	}*/
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Reading in Party/Map and communicating with Handler class
	
	public void mapCreation() throws FileNotFoundException
	{
		Scanner file = new Scanner(new File("images/Map.txt"));
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
	
	/*public void partyCreation() throws FileNotFoundException
	{
		Scanner file = new Scanner(new File("images/Hero.txt"));
		
	while(file.hasNext())
		{
			Hero player = new Hero(file.nextLine(), file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt());
			
			file.nextLine();
			party.add(player);
			partyLength++;
		}
	}
	*/
	public void partyCreation() throws FileNotFoundException
	{
		Scanner partyFile = new Scanner (new File("partyData/party.txt"));
		ArrayList<String> tempParty = new ArrayList<String>();
		
		while(partyFile.hasNext())
		{
			String name = partyFile.nextLine();
			tempParty.add(name);
			Scanner heroDataFile = new Scanner(new File("partyData/Characters/" + tempParty.get(partyLength) + ".txt"));
			
			
				Hero player = new Hero(heroDataFile.nextLine(), heroDataFile.nextInt(), heroDataFile.nextInt(), heroDataFile.nextInt(), heroDataFile.nextInt(), heroDataFile.nextInt());
				
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
	
	public void setCursorPosition(int x, int y)
	{
		cursorXPos = x;
		cursorYPos = y;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	//---------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	
	public void startScreen(Graphics g)
	{
		g.setColor(this.randColor());
		g.fillRect(0, 0, mapWidth, mapHeight);
	}
	
	
	//===============================================================================================================================================================
	
	//Macro's
	public void onStartup() throws FileNotFoundException
	{
		generateGrid();
		partyCreation();
		mapCreation();
		startupChecker = false;
	}
	
	public Color randColor() {
		return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//FRAMING
	static JTextField text = new JTextField();

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
				if(x<mapWidth && y<mapHeight)
					((Hero) party.get(getPartySelection())).setPosition(x/(mapWidth/gridWidth), y/(mapHeight/gridHeight));
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
			
			if (event.getKeyCode() == KeyEvent.VK_W) 
			{
				partySelectDown();

			}
			if (event.getKeyCode() == KeyEvent.VK_S) 
			{
				partySelectUp();

			}
				
			}
			
		@Override
		public void mouseMoved(MouseEvent event) {
			// TODO Auto-generated method stub
			int x = event.getX() - 5;
			int y = event.getY() - 32;
			setCursorPosition(x, y);
			
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