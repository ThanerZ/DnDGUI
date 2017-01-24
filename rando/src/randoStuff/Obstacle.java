package randoStuff;

import java.awt.Color;

public class Obstacle {
	
	int xPos, yPos;
	int width, height;
	int red, green, blue;
	String name;
	
	public Obstacle()
	{
		xPos = 0;
		yPos = 0;
		width = 0;
		height = 0;
	}
	
	public Obstacle(int x, int y, int w, int h, int r, int g, int b)
	{
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		red = r;
		green = g;
		blue = b;
	}
	
	public int getXPos()
	{
		return xPos;
	}
	
	public int getYPos()
	{
		return yPos;
	}

	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Color getColor()
	{
		return new Color(red, green, blue);
	}
}
