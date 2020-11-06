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

public class Wizard extends Piece
{

    public Wizard(String x)
    {
        color = x;
        if(x == "w")
        {
            symbol = "Wiz";
        }
        else
        {
            symbol = "wiz";
        }
    }

    public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
    {
        g2.fill(new Ellipse2D.Double(xOffSet+30, yOffSet+40, 40, 50));
        g2.fill(new Ellipse2D.Double(xOffSet+35, yOffSet+60, 5, 5));
        g2.fill(new Ellipse2D.Double(xOffSet+60, yOffSet+60, 5, 5));
        if(color == "w")
        {
            g2.setColor(Color.DARK_GRAY);
        }
        else
        {
            g2.setColor(Color.LIGHT_GRAY);
        }

        g2.setStroke(new BasicStroke(3));
        g2.draw(new Ellipse2D.Double(xOffSet+30, yOffSet+40, 40, 50));
        g2.draw(new Ellipse2D.Double(xOffSet+35, yOffSet+60, 5, 5));
        g2.draw(new Ellipse2D.Double(xOffSet+60, yOffSet+60, 5, 5));

        if(color == "w")
        {
            g2.setColor(new Color(255,253,208));
        }
        else
        {
            g2.setColor(Color.BLACK);
        }

        g2.fill(new Ellipse2D.Double(xOffSet+48, yOffSet+20, 3, 3));
        g2.fill(new Ellipse2D.Double(xOffSet+55, yOffSet+30, 3, 3));
        g2.fill(new Ellipse2D.Double(xOffSet+40, yOffSet+40, 3, 3));

        g2.fill(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
        int x[]={xOffSet+30,xOffSet+70,xOffSet+50};
        int y[]={yOffSet+50,yOffSet+50,yOffSet+5};
        g2.fillPolygon(x, y, 3);
        if(color == "w")
        {
            g2.setColor(Color.DARK_GRAY);
        }
        else
        {
            g2.setColor(Color.LIGHT_GRAY);
        }
        g2.setStroke(new BasicStroke(3));


        g2.draw(new Ellipse2D.Double(xOffSet+48, yOffSet+20, 3, 3));
        g2.draw(new Ellipse2D.Double(xOffSet+55, yOffSet+30, 3, 3));
        g2.draw(new Ellipse2D.Double(xOffSet+40, yOffSet+40, 3, 3));
        g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
        g2.drawPolygon(x, y, 3);

        g2.draw(new Line2D.Double(xOffSet+35, yOffSet+50, xOffSet+65, yOffSet+50));
    }

    public ArrayList<Tile> getMoves(int x, int y)
    {
        int xPos = x;
        int yPos = y;
        ArrayList<Tile> posMoves = new ArrayList<>();
        //Check upper tiles
        xPos = x + 1;
        while(xPos <= x+3 && Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            xPos += 1;
        }
        //Lower Tiles
        xPos = x - 1;
        while(xPos >= x-3 && Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            xPos -= 1;
        }
        //reset xPos
        xPos = x;
        //right
        yPos = y + 1;
        while(yPos <= y+3 && Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            yPos += 1;
        }
        //left
        yPos = y - 1;
        while(yPos >= y-3 && Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            yPos -= 1;
        }

        xPos = x + 1;
        yPos = y + 1;

        if(Draw.getTile(xPos, yPos) != null && Draw.getTile(xPos, yPos).getOcc() != null)
        {
            posMoves.add(Draw.getTile(xPos, yPos));
        }

        //Lower Tiles
        xPos = x - 1;
        yPos = y + 1;

        if(Draw.getTile(xPos, yPos) != null && Draw.getTile(xPos, yPos).getOcc() != null)
        {
            posMoves.add(Draw.getTile(xPos, yPos));
        }


        //right
        xPos = x + 1;
        yPos = y - 1;

        if(Draw.getTile(xPos, yPos) != null && Draw.getTile(xPos, yPos).getOcc() != null)
        {
            posMoves.add(Draw.getTile(xPos, yPos));
        }

        //Lower Tiles
        xPos = x - 1;
        yPos = y - 1;

        if(Draw.getTile(xPos, yPos) != null && Draw.getTile(xPos, yPos).getOcc() != null)
        {
            posMoves.add(Draw.getTile(xPos, yPos));
        }

        xPos = x + 2;
        yPos = y + 1;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }
        xPos = x + 2;
        yPos = y - 1;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }
        xPos = x - 2;
        yPos = y + 1;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }
        xPos = x - 2;
        yPos = y - 1;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }
        xPos = x + 1;
        yPos = y + 2;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }
        xPos = x - 1;
        yPos = y + 2;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }
        xPos = x + 1;
        yPos = y - 2;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }
        xPos = x - 1;
        yPos = y - 2;
        if(Draw.getTile(xPos, yPos) != null)
        {
            if(Draw.getTile(xPos, yPos).getOcc() != null)
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }

        }

        System.out.println(posMoves);
        return posMoves;
    }

}
