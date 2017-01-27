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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EnemyMapping extends JPanel {
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//INSTANTIATION
	//Clicky Instantiation
	HandlerClass handler = new HandlerClass();
	
	
	static boolean lClickD = false, rClickD = false, mClickD = false;
	static int clicks = 0;
	
	
	//Clicking
	boolean clearEnemiesSafety = true;
	
	//Editing
	int editingSelection = -1;
	
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
	
	//number of obstacles
	int obstacles = 10;
	int[][] map = new int[gridWidth][gridHeight];
	
	ArrayList<Enemy> horde = new ArrayList<Enemy>();
	int hordeLength = 0;
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
		
		
			//Drawing the stuff
			drawBackground(g);
			drawMap(g);
			drawGrid(g);
			drawClearButton(g);
			drawHorde(g);
			drawSaveButton(g);
			drawPriority(g);
			

			
			
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
	
	public void drawHorde(Graphics g)
	{
		if(hordeLength>0)
		{
			int xSpot = 0;
			int ySpot = 0;
			int health = 0;
			for(int i=0; i<hordeLength; i++)
			{
				xSpot = ((Enemy) horde.get(i)).getXPos();
				ySpot = ((Enemy) horde.get(i)).getYPos();
				health = horde.get(i).getHealth();
				
				g.setColor(((Enemy) horde.get(i)).getColor());
				
				g.fillRect(xSpot*(mapWidth/gridWidth)+1, ySpot*(mapHeight/gridHeight)+1, (mapWidth/gridWidth)-1, (mapHeight/gridHeight)-1);
				g.setColor(Color.BLACK);
				g.drawString("" + health, (xSpot-1)*(mapWidth/gridWidth)+(mapWidth/gridWidth), ySpot*(mapHeight/gridHeight)+(mapHeight/gridHeight/2));
				
				
			}
		}
	}
	
	public void drawPriority(Graphics g)
	{
		if(hordeLength>0)
		{
		int xSpot;
		int ySpot;
		
		g.setColor(Color.YELLOW);
		
		xSpot = ((Enemy) horde.get(editingSelection)).getXPos();
		ySpot = ((Enemy) horde.get(editingSelection)).getYPos();
		g.drawRect(xSpot*(mapWidth/gridWidth), ySpot*(mapHeight/gridHeight), (mapWidth/gridWidth), (mapHeight/gridHeight));
		}
	}
	
	public void drawClearButton(Graphics g)
	{
		if(clearEnemiesSafety)
			g.setColor(new Color(255, 75, 75));
		else
			g.setColor(Color.RED);
		g.fillRect(mapWidth + (width-mapWidth)/8, mapHeight/8, ((mapWidth/gridWidth)*10)-1, ((mapHeight/gridHeight)*2)-1);

	}
	
	public void drawSaveButton(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.fillRect(mapWidth + (width-mapWidth)/8,  mapHeight - (mapHeight/8), ((mapWidth/gridWidth)*10)-1, ((mapHeight/gridHeight)*2)-1);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Creation
	
	public void createEnemy(int x, int y)
	{
		if(checkMapOccupation(x, y))
		{
		Enemy Eminem = new Enemy();
		Eminem.setPosition(x, y);
		Eminem.setColor(255,0,0);
		horde.add(Eminem);
		hordeLength++;
		editingSelection = hordeLength-1;
		}
	}
	
	public void setEnemyHealthUp()
	{
		if(hordeLength>0)
		{
			int current = horde.get(editingSelection).getHealth();
		
			horde.get(editingSelection).setHealth(current+1);
		}
	}
	public void setEnemyHealthDown()
	{
		if(hordeLength>0)
		{
			int health = horde.get(editingSelection).getHealth();
			
			if(health>0)
				horde.get(editingSelection).setHealth(health-1);
		}
	}
	public void deleteEnemy()
	{
		if(hordeLength>0)
		{
		horde.remove(editingSelection);
		if(editingSelection>0)
			editingSelection--;
		hordeLength--;
		}
	}
	
	public void clearEnemies()
	{
		if(clearEnemiesSafety)
			clearEnemiesSafety = false;
		else
		{
			horde.clear();
			clearEnemiesSafety = true;
			hordeLength = 0;
			editingSelection = -1;
		}
	}
	
	public void saveSetup() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("data/EnemyMap.txt", "UTF-8");
		
		int xSpot;
		int ySpot;
		int health;
		
		for(int i = 0; i<hordeLength; i++)
		{
			xSpot = ((Enemy) horde.get(i)).getXPos();
			ySpot = ((Enemy) horde.get(i)).getYPos();
			health = ((Enemy) horde.get(i)).getHealth();
			
			writer.println(xSpot);
			writer.println(ySpot);
			writer.println(health);
		}
		
		writer.close();
		
		System.out.println("SAVED");
		System.exit(0);
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
	
	public boolean checkMapOccupation(int x, int y)
	{
		//returns false for a grid-space being occupied
		//occupied = false
		
		for(int i = 0; i<hordeLength; i++)
		{
			if(x == ((Enemy) horde.get(i)).getXPos()  &&  y == ((Enemy) horde.get(i)).getYPos())
				return false;
		}
		return true;
	}


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//CLICKING MACRO
	public void selectPreviousEnemy()
	{
		if(editingSelection>0)
			editingSelection--;
	}
	public void selectNextEnemy()
	{
		if(editingSelection<hordeLength-1)
			editingSelection++;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	
	//===============================================================================================================================================================
	
	//Macro's
	public void onStartup() throws FileNotFoundException
	{
		mapCreation();
	}
	
	public Color randColor() {
		return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//FRAMING
	static JTextField text = new JTextField();

	static JFrame frame = new JFrame("Thane is pretty cool");

	public static void frame() throws InterruptedException {
		EnemyMapping game = new EnemyMapping();
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
				//if the click is in the map(grid)
				if(x<mapWidth && y<mapHeight)
					createEnemy(x/(mapWidth/gridWidth), y/(mapHeight/gridHeight));
				//if the click is on the button
				if(x>(mapWidth + (width-mapWidth)/8) && y>(mapHeight/8) && x<(mapWidth + (width-mapWidth)/8 + (mapWidth/gridWidth)*10)-1 && y<(((mapHeight/8)+(mapHeight/gridHeight)*2)-1))
					clearEnemies();
				if(x>(mapWidth + (width-mapWidth)/8) && y>(mapHeight - (mapHeight/8)) && x<(mapWidth + (width-mapWidth)/8 + (mapWidth/gridWidth)*10)-1 && y<(((mapHeight - (mapHeight/8))+(mapHeight/gridHeight)*2)-1))
				{
					try {
						saveSetup();
					} catch (FileNotFoundException | UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
			
			if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE)
				deleteEnemy();
			if (event.getKeyCode() == KeyEvent.VK_W)
				setEnemyHealthUp();
			if (event.getKeyCode() == KeyEvent.VK_S)
				setEnemyHealthDown();
			if (event.getKeyCode() == KeyEvent.VK_A)
				selectPreviousEnemy();
			if (event.getKeyCode() == KeyEvent.VK_D)
				selectNextEnemy();
			if (event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				for(int i = 0; i<hordeLength; i++)
					System.out.println(horde.get(i));
				System.out.println("------------------");
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