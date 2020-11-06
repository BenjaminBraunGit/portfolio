package com.superChess;

import com.superChess.pieces.Piece;

public class Tile
{
	Piece occ = null;
	String color = null;
	
	public Tile(Piece x, String y)
	{
		occ = x;
		color = y;
	}
	
	public Piece getOcc()
	{
		return occ;
	}
	
	public void setOcc(Piece x)
	{
		occ = x;
	}
	
	public String getColor()
	{
		return color;
	}
}
