package com.superChess.pieces;
import com.superChess.Tile;

import java.util.ArrayList;
import java.awt.Graphics2D;

abstract public class Piece
{
	String color = "temp";
	String symbol = "?";
	
	public String getSym()
	{
		return symbol;
	}
	
	public String getTeam()
	{
		return color;
	}
	
	abstract public ArrayList<Tile> getMoves(int x, int y);
	
	abstract public void drawPiece (Graphics2D g2, int xOffSet, int yOffSet);
}
