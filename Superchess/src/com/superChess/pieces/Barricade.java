package com.superChess.pieces;
import com.superChess.Draw;
import com.superChess.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Barricade extends Piece 
{

	public Barricade(String x)
	{
		color = x;
		if(x == "w")
		{
			symbol = "BAR";
		}
		else
		{
			symbol = "bar";
		}
	}
	
	public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
	{
		g2.fill(new Rectangle2D.Double(xOffSet+10, yOffSet+5, 80, 70));
		g2.fill(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		
		if(color == "w")
		{
			g2.setColor(Color.DARK_GRAY);
		}
		else
		{
			g2.setColor(Color.LIGHT_GRAY);
		}
		g2.setStroke(new BasicStroke(3));
		
		g2.draw(new Rectangle2D.Double(xOffSet+10, yOffSet+5, 80, 70));
		g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		
		g2.draw(new Line2D.Double(xOffSet+20, yOffSet+35, xOffSet+50, yOffSet+65));
		g2.draw(new Line2D.Double(xOffSet+25, yOffSet+25, xOffSet+55, yOffSet+55));
		g2.draw(new Line2D.Double(xOffSet+30, yOffSet+15, xOffSet+60, yOffSet+45));
	}
	
	public ArrayList<Tile> getMoves(int x, int y)
	{
		int xPos = x;
		int yPos = y;
		ArrayList<Tile> posMoves = new ArrayList<>();
		//Check upper tiles
		xPos = x + 1;
		while(xPos <= x+2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				System.out.println(xPos);
				System.out.println(yPos);
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos += 1;
			}
			else
			{
				break;
			}
			
			
		}
		//Lower Tiles
		xPos = x - 1;
		while(xPos >= x-2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos -= 1;
			}
			else 
			{
				break;
			}	
		}
		//reset xPos
		xPos = x;
		//right
		yPos = y + 1;
		while(yPos <= y+2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				yPos += 1;
			}
			else
			{
				break;
			}
			
		}
		//left
		yPos = y - 1;
		while(yPos >= y-2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				yPos -= 1;
			}
			else 
			{
				break;
			}
			
		}
		
		xPos = x + 1;
		yPos = y + 1;
		while(xPos <= x+2 && yPos <= y+2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos += 1;
				yPos += 1;
			}
			else 
			{
				break;
			}
			
			
		}
		//Lower Tiles
		xPos = x - 1;
		yPos = y + 1;
		while(xPos >= x-2 && yPos <= y+2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos -= 1;
				yPos += 1;
			}
			else 
			{
				break;
			}
			
		}
		//right
		xPos = x + 1;
		yPos = y - 1;
		while(xPos <= x+2 && yPos >= y-2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos += 1;
				yPos -= 1;
			}
			else
			{
				break;
			}
			
		}
		//left
		yPos = y - 1;
		xPos = x - 1;
		while(xPos >= x-2 && yPos >= y-2 && Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos -= 1;
				yPos -= 1;
			}
			else 
			{
				break;
			}
			
		}
		System.out.println(posMoves);
		return posMoves;
	}

}
