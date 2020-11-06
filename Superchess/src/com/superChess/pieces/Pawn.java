package com.superChess.pieces;
import com.superChess.Draw;
import com.superChess.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Pawn extends Piece 
{

	public Pawn(String x)
	{
		color = x;
		if(x == "w")
		{
			symbol = "P";
		}
		else
		{
			symbol = "p";
		}
	}
	
	public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
	{
		g2.fill(new Ellipse2D.Double(xOffSet+30, yOffSet+5, 40, 40));
		g2.fill(new Rectangle2D.Double(xOffSet+40, yOffSet+45, 20, 30));
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
		
		g2.draw(new Ellipse2D.Double(xOffSet+30, yOffSet+5, 40, 40));
		g2.draw(new Rectangle2D.Double(xOffSet+40, yOffSet+45, 20, 30));
		g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		g2.draw(new Line2D.Double(xOffSet+35, yOffSet+45, xOffSet+65, yOffSet+45));
		
	}
	
	
	public ArrayList<Tile> getMoves(int x, int y)
	{
		int xPos = x;
		int yPos = y;
		ArrayList<Tile> posMoves = new ArrayList<>();
		
		if(color == "w")
		{
			//Check upper tiles
			xPos = x - 1;
			if(Draw.getTile(xPos, yPos) != null)
			{
				if(Draw.getTile(xPos, yPos).getOcc() == null)
				{
					posMoves.add(Draw.getTile(xPos, yPos));
					if(xPos + 1 == 6 && Draw.getTile(xPos - 1, yPos).getOcc() == null)
					{
						posMoves.add(Draw.getTile(xPos - 1, yPos));
					}
				}
				
			}
			//diagonal tiles
			xPos = x - 1;
			yPos = y + 1;
			if(Draw.getTile(xPos, yPos) != null)
			{
				if(Draw.getTile(xPos, yPos).getOcc() != null && Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
				{
					posMoves.add(Draw.getTile(xPos, yPos));
				}
				
			}
			
			xPos = x - 1;
			yPos = y - 1;
			if(Draw.getTile(xPos, yPos) != null)
			{
				if(Draw.getTile(xPos, yPos).getOcc() != null && Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
				{
					posMoves.add(Draw.getTile(xPos, yPos));
				}
				
			}
		}
		
		else if(color == "b")
		{
			//Check upper tiles
			xPos = x + 1;
			if(Draw.getTile(xPos, yPos) != null)
			{
				if(Draw.getTile(xPos, yPos).getOcc() == null)
				{
					posMoves.add(Draw.getTile(xPos, yPos));
					if(xPos - 1 == 1 && Draw.getTile(xPos + 1, yPos).getOcc() == null)
					{
						posMoves.add(Draw.getTile(xPos + 1, yPos));
					}
				}
				
			}
			//diagonal tiles
			xPos = x + 1;
			yPos = y + 1;
			if(Draw.getTile(xPos, yPos) != null)
			{
				if(Draw.getTile(xPos, yPos).getOcc() != null && Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
				{
					posMoves.add(Draw.getTile(xPos, yPos));
				}
				
			}
			
			xPos = x + 1;
			yPos = y - 1;
			if(Draw.getTile(xPos, yPos) != null)
			{
				if(Draw.getTile(xPos, yPos).getOcc() != null && Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
				{
					posMoves.add(Draw.getTile(xPos, yPos));
				}
				
			}
		}
		return posMoves;
	}
}
