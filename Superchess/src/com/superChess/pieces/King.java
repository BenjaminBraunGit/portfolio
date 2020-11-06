package com.superChess.pieces;
import com.superChess.Draw;
import com.superChess.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class King extends Piece
{
	public King(String x)
	{
		color = x;
		if(x == "w")
		{
			symbol = "K";
		}
		else
		{
			symbol = "k";
		}
	}

	public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
	{
		g2.fill(new Rectangle2D.Double(xOffSet+20, yOffSet+30, 60, 10));
		g2.fill(new Rectangle2D.Double(xOffSet+30, yOffSet+40, 40, 10));
		g2.fill(new Rectangle2D.Double(xOffSet+40, yOffSet+50, 20, 25));
		g2.fill(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		g2.fill(new Rectangle2D.Double(xOffSet+46, yOffSet+2, 8, 28));
		g2.fill(new Rectangle2D.Double(xOffSet+36, yOffSet+12, 28, 8));
		if(color == "w")
		{
			g2.setColor(Color.DARK_GRAY);
		}
		else
		{
			g2.setColor(Color.LIGHT_GRAY);
		}
		g2.setStroke(new BasicStroke(3));

		g2.draw(new Rectangle2D.Double(xOffSet+20, yOffSet+30, 60, 10));
		g2.draw(new Rectangle2D.Double(xOffSet+30, yOffSet+40, 40, 10));
		g2.draw(new Rectangle2D.Double(xOffSet+40, yOffSet+50, 20, 25));
		g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		g2.draw(new Rectangle2D.Double(xOffSet+46, yOffSet+2, 8, 28));
		g2.draw(new Rectangle2D.Double(xOffSet+36, yOffSet+12, 28, 8));

		g2.draw(new Line2D.Double(xOffSet+25, yOffSet+50, xOffSet+75, yOffSet+50));
	}

	private boolean isMoveValid(int x, int y, int xPos, int yPos)
	{
		if(Draw.getTile(xPos, yPos) != null) {
			Piece piece = Draw.getTile(xPos, yPos).getOcc();
			if (piece == null || piece.getTeam() != getTeam()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Tile> getMoves(int x, int y)
	{
		int xPos = x;
		int yPos = y;
		ArrayList<Tile> posMoves = new ArrayList<>();
		//Check upper tiles

		xPos = x + 1;

		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}

//		if(Draw.getTile(xPos, yPos) != null)
//		{
//			if(Draw.getTile(xPos, yPos).getOcc() == null || Draw.getTile(xPos, yPos).getOcc().getTeam() != Draw.getTile(x, y).getOcc().getTeam())
//			{
//				posMoves.add(Draw.getTile(xPos, yPos));
//			}
//
//		}
		xPos = x - 1;
		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}
		//reset x
		xPos = x;

		yPos = y + 1;
		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}
		yPos = y - 1;
		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}

		xPos = x + 1;
		yPos = y + 1;
		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}
		xPos = x - 1;
		yPos = y + 1;
		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}
		xPos = x + 1;
		yPos = y - 1;
		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}
		xPos = x - 1;
		yPos = y - 1;
		if (isMoveValid(x, y, xPos, yPos))
		{
			posMoves.add(Draw.getTile(xPos, yPos));
		}
		System.out.println(posMoves);
		return posMoves;
	}


}