package randoStuff;

import java.awt.Color;

public class Enemy {
	

	int xPos, yPos;
	int health;
	int red, green, blue;
	String name;
//---------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Enemy()
	{
		xPos = 0;
		yPos = 0;
		name = "Enemy";
		
		red = 255;
		green = 0;
		blue = 0;
		
		health = 0;
	}
	
	public Enemy(int x, int y)
	{
		xPos = x;
		yPos = y;

		red = 255;
		green = 0;
		blue = 0;
		
		health = 0;
	}
	
	public Enemy(int x, int y, int h)
	{
		xPos = x;
		yPos = y;

		red = 255;
		green = 0;
		blue = 0;
		
		health = h;
	}
	
	public Enemy(String n, int x, int y)
	{
		xPos = x;
		yPos = y;
		name = n;
		
		red = 255;
		green = 0;
		blue = 0;
		
		health = 0;
	}
	//ULTIMATE ONE
	public Enemy(String n, int x, int y, int r, int g, int b, int h)
	{
		xPos = x;
		yPos = y;
		name = n;
		
		red = r;
		green = g;
		blue = b;
		
		health = h;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void setPosition(int x, int y)
	{
		xPos = x;
		yPos = y;
	}
	public void setColor(int r, int g, int b)
	{
		red = r;
		green = g;
		blue = b;
		
	}
	
	public void setHealth(int h)
	{
		health = h;
	}
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	public int getXPos()
	{
		return xPos;
	}
	public int getYPos()
	{
		return yPos;
	}
	
	public Color getColor()
	{
		return new Color(red, green, blue);
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public String getName()
	{
		return name;
	}
}
