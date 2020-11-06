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

public class Minion extends Piece
{

    public Minion(String x)
    {
        color = x;
        if(x == "w")
        {
            symbol = "M";
        }
        else
        {
            symbol = "m";
        }
    }

    public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
    {
        g2.fill(new Rectangle2D.Double(xOffSet+30, yOffSet+15, 45, 30));
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

        g2.draw(new Rectangle2D.Double(xOffSet+30, yOffSet+15, 45, 30));
        g2.draw(new Rectangle2D.Double(xOffSet+40, yOffSet+45, 20, 30));
        g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
        g2.draw(new Line2D.Double(xOffSet+35, yOffSet+45, xOffSet+65, yOffSet+45));

        g2.draw(new Ellipse2D.Double(xOffSet+35, yOffSet+15, 5, 5));
        g2.draw(new Ellipse2D.Double(xOffSet+65, yOffSet+15, 5, 5));
        g2.draw(new Line2D.Double(xOffSet+40, yOffSet+20, xOffSet+60, yOffSet+20));

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
            yPos = y + 1;
            if(Draw.getTile(xPos, yPos) != null)
            {
                if(Draw.getTile(xPos, yPos).getOcc() == null)
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                    if(Draw.getTile(xPos - 1, yPos + 1) != null && xPos + 1 == 6 && Draw.getTile(xPos - 1, yPos + 1).getOcc() == null)
                    {
                        posMoves.add(Draw.getTile(xPos - 1, yPos + 1));
                    }
                }

            }

            xPos = x - 1;
            yPos = y - 1;
            if(Draw.getTile(xPos, yPos) != null)
            {
                if(Draw.getTile(xPos, yPos).getOcc() == null)
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                    if(Draw.getTile(xPos - 1, yPos - 1) != null && xPos + 1 == 6 && Draw.getTile(xPos - 1, yPos - 1).getOcc() == null)
                    {
                        posMoves.add(Draw.getTile(xPos - 1, yPos - 1));
                    }
                }

            }
            yPos = y;
            xPos = x + 1;
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
            xPos = x + 1;
            yPos = y + 1;
            if(Draw.getTile(xPos, yPos) != null)
            {
                if(Draw.getTile(xPos, yPos).getOcc() == null)
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                    if(Draw.getTile(xPos + 1, yPos + 1) != null && xPos - 1 == 1 && Draw.getTile(xPos + 1, yPos + 1).getOcc() == null)
                    {
                        posMoves.add(Draw.getTile(xPos + 1, yPos + 1));
                    }
                }

            }

            xPos = x + 1;
            yPos = y - 1;
            if(Draw.getTile(xPos, yPos) != null)
            {
                if(Draw.getTile(xPos, yPos).getOcc() == null)
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                    if(Draw.getTile(xPos + 1, yPos - 1) != null && xPos - 1 == 1 && Draw.getTile(xPos + 1, yPos - 1).getOcc() == null)
                    {
                        posMoves.add(Draw.getTile(xPos + 1, yPos - 1));
                    }
                }

            }
            yPos = y;
            xPos = x - 1;
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
