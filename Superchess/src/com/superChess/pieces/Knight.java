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

public class Knight extends Piece 
{

	public Knight(String x)
	{
		color = x;
		if(x == "w")
		{
			symbol = "N";
		}
		else
		{
			symbol = "n";
		}
	}
	
	public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
	{
		g2.fill(new Ellipse2D.Double(xOffSet+20, yOffSet+15, 30, 70));
		
		if(color == "w")
		{
			g2.setColor(Color.DARK_GRAY);
		}
		else
		{
			g2.setColor(Color.LIGHT_GRAY);
		}
		
		g2.setStroke(new BasicStroke(3));
		g2.draw(new Ellipse2D.Double(xOffSet+20, yOffSet+15, 30, 70));
		
		if(color == "w")
		{
			g2.setColor(new Color(255,253,208));
		}
		else
		{
			g2.setColor(Color.BLACK);
		}
		
		g2.fill(new Ellipse2D.Double(xOffSet+20, yOffSet+5, 65, 30));
		g2.fill(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		if(color == "w")
		{
			g2.setColor(Color.DARK_GRAY);
		}
		else
		{
			g2.setColor(Color.LIGHT_GRAY);
		}
		g2.draw(new Ellipse2D.Double(xOffSet+20, yOffSet+5, 65, 30));
		g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		g2.draw(new Ellipse2D.Double(xOffSet+65, yOffSet+15, 5, 5));
		g2.draw(new Line2D.Double(xOffSet+55, yOffSet+25, xOffSet+80, yOffSet+25));
	}
	
	public ArrayList<Tile> getMoves(int x, int y)
	{
		int xPos = x;
		int yPos = y;
		ArrayList<Tile> posMoves = new ArrayList<>();
		//Check upper tiles
		xPos = x + 2;
		yPos = y + 1;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		xPos = x + 2;
		yPos = y - 1;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		xPos = x - 2;
		yPos = y + 1;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		xPos = x - 2;
		yPos = y - 1;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		xPos = x + 1;
		yPos = y + 2;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		xPos = x - 1;
		yPos = y + 2;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		xPos = x + 1;
		yPos = y - 2;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		xPos = x - 1;
		yPos = y - 2;
		if(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			
		}
		System.out.println(posMoves);
		return posMoves;
	}
}
