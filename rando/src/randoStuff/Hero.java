package randoStuff;

import java.awt.Color;

public class Hero {
	

	int xPos, yPos;
	int health;
	int red, green, blue;
	String name;

	
	public Hero(String n, int x, int y, int r, int g, int b)
	{
		xPos = x;
		yPos = y;
		name = n;
		
		red = r;
		green = g;
		blue = b;
	}
	public Hero(String n, int x, int y, int h, int r, int g, int b)
	{
		xPos = x;
		yPos = y;
		name = n;
		
		health = h;
		
		red = r;
		green = g;
		blue = b;
	}
	
	public void setPosition(int x, int y)
	{
		xPos = x;
		yPos = y;
	}
	
	public void setHealth(int h)
	{
		health = h;
	}
	
	public int getXPos()
	{
		return xPos;
	}
	
	public int getYPos()
	{
		return yPos;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public Color getColor()
	{
		return new Color(red, green, blue);
	}
	
	public String getName()
	{
		return name;
	}
}