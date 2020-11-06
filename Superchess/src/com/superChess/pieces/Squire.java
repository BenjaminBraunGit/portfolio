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

public class Squire extends Piece 
{

	public Squire(String x)
	{
		color = x;
		if(x == "w")
		{
			symbol = "SQ";
		}
		else
		{
			symbol = "sq";
		}
	}
	
	public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
	{
		g2.fill(new Ellipse2D.Double(xOffSet+30, yOffSet+15, 45, 30));
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
		
		g2.draw(new Ellipse2D.Double(xOffSet+30, yOffSet+15, 45, 30));
		g2.draw(new Rectangle2D.Double(xOffSet+40, yOffSet+45, 20, 30));
		g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
		g2.draw(new Line2D.Double(xOffSet+35, yOffSet+45, xOffSet+65, yOffSet+45));
		
		g2.draw(new Ellipse2D.Double(xOffSet+35, yOffSet+15, 5, 5));
		g2.draw(new Ellipse2D.Double(xOffSet+65, yOffSet+15, 5, 5));
		g2.draw(new Line2D.Double(xOffSet+40, yOffSet+20, xOffSet+60, yOffSet+20));
	}

	private void addMoveIfValid(ArrayList<Tile> posMoves, int xPos, int yPos, ArrayList<Tile> checkedMoves)
	{
		Tile tile = Draw.getTile(xPos, yPos);
		if(tile != null && !checkedMoves.contains(tile))
		{
			checkedMoves.add(tile);
			Piece piece = tile.getOcc();
			if (piece == null)
			{
				// no piece, we can move her.
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			else if (piece.getTeam() != getTeam())
			{
				// enemy piece, we can move here.
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			else if (piece instanceof Squire && !((Squire) piece).getMovesInternal(xPos, yPos, checkedMoves).isEmpty())
			{
				// piece is a squire, use special method to determine if it has moves.
				posMoves.add(Draw.getTile(xPos, yPos));
			}
			else if (!piece.getMoves(xPos, yPos).isEmpty())
			{
				// piece is not a squire, use normal method to see if it has moves.
				posMoves.add(Draw.getTile(xPos, yPos));
			}
		}
	}

	private ArrayList<Tile> getMovesInternal(int x, int y, ArrayList<Tile> checkedMoves)
	{
		ArrayList<Tile> posMoves = new ArrayList<>();
		checkedMoves.add(Draw.getTile(x, y));
		addMoveIfValid(posMoves, x + 1, y, checkedMoves);
		addMoveIfValid(posMoves, x - 1, y, checkedMoves);
		addMoveIfValid(posMoves, x, y + 1, checkedMoves);
		addMoveIfValid(posMoves, x , y - 1, checkedMoves);
		//System.out.println(posMoves);
		return posMoves;
	}

	public ArrayList<Tile> getMoves(int x, int y)
	{
		ArrayList<Tile> checkedMoves = new ArrayList<>();
		return getMovesInternal(x, y, checkedMoves);

	}
	
}
