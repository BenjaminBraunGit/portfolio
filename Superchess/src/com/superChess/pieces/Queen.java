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

public class Queen extends Piece 
{

	public Queen(String x)
	{
		color = x;
		if(x == "w")
		{
			symbol = "Q";
		}
		else
		{
			symbol = "q";
		}
	}
	
	public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
	{
g2.fill(new Ellipse2D.Double(xOffSet+30, yOffSet+12, 40, 15));
		
		if(color == "w")
		{
			g2.setColor(Color.DARK_GRAY);
		}
		else
		{
			g2.setColor(Color.LIGHT_GRAY);
		}
		
		g2.setStroke(new BasicStroke(3));
		g2.draw(new Ellipse2D.Double(xOffSet+30, yOffSet+12, 40, 15));
		
		if(color == "w")
		{
			g2.setColor(new Color(255,253,208));
		}
		else
		{
			g2.setColor(Color.BLACK);
		}
		
		g2.fill(new Rectangle2D.Double(xOffSet+20, yOffSet+20, 60, 20));
		g2.fill(new Rectangle2D.Double(xOffSet+30, yOffSet+40, 40, 10));
		g2.fill(new Rectangle2D.Double(xOffSet+40, yOffSet+50, 20, 25));
		g2.fill(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		g2.fill(new Ellipse2D.Double(xOffSet+45, yOffSet+2, 10, 10));
		if(color == "w")
		{
			g2.setColor(Color.DARK_GRAY);
		}
		else
		{
			g2.setColor(Color.LIGHT_GRAY);
		}
		g2.setStroke(new BasicStroke(3));
		
		g2.draw(new Rectangle2D.Double(xOffSet+20, yOffSet+20, 60, 20));
		g2.draw(new Rectangle2D.Double(xOffSet+30, yOffSet+40, 40, 10));
		g2.draw(new Rectangle2D.Double(xOffSet+40, yOffSet+50, 20, 25));
		g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		g2.draw(new Ellipse2D.Double(xOffSet+45, yOffSet+2, 10, 10));
		
		g2.setStroke(new BasicStroke(4));
		
		g2.draw(new Line2D.Double(xOffSet+25, yOffSet+22, xOffSet+30, yOffSet+30));
		g2.draw(new Line2D.Double(xOffSet+37, yOffSet+22, xOffSet+40, yOffSet+30));
		g2.draw(new Line2D.Double(xOffSet+50, yOffSet+22, xOffSet+50, yOffSet+30));
		g2.draw(new Line2D.Double(xOffSet+63, yOffSet+22, xOffSet+60, yOffSet+30));
		g2.draw(new Line2D.Double(xOffSet+75, yOffSet+22, xOffSet+70, yOffSet+30));
	}
	
	public ArrayList<Tile> getMoves(int x, int y)
	{
		System.out.println("Method Ran");
		int xPos = x;
		int yPos = y;
		ArrayList<Tile> posMoves = new ArrayList<>();
		//Check upper tiles
		xPos = x + 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				System.out.println(xPos);
				System.out.println(yPos);
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos += 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		//Lower Tiles
		xPos = x - 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos -= 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		//reset xPos
		xPos = x;
		//right
		yPos = y + 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				yPos += 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		//left
		yPos = y - 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				yPos -= 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		
		xPos = x + 1;
		yPos = y + 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos += 1;
				yPos += 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		//Lower Tiles
		xPos = x - 1;
		yPos = y + 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos -= 1;
				yPos += 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		//right
		xPos = x + 1;
		yPos = y - 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos += 1;
				yPos -= 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		//left
		yPos = y - 1;
		xPos = x - 1;
		while(Draw.getTile(xPos, yPos) != null)
		{
			if(Draw.getTile(xPos, yPos).getOcc() == null)
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				xPos -= 1;
				yPos -= 1;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() == Draw.getTile(x, y).getOcc().getTeam())
			{
				break;
			}
			else if(Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
			{
				posMoves.add(Draw.getTile(xPos, yPos));
				break;
			}
			
		}
		System.out.println(posMoves);
		return posMoves;
	}

	
}
