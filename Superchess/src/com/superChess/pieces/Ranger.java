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

public class Ranger extends Piece
{
    public Ranger(String x)
    {
        color = x;
        if(x == "w")
        {
            symbol = "RAN";
        }
        else
        {
            symbol = "ran";
        }
    }

    public void drawPiece(Graphics2D g2, int xOffSet, int yOffSet)
    {
        g2.fill(new Ellipse2D.Double(xOffSet+30, yOffSet+25, 40, 25));
        g2.fill(new Ellipse2D.Double(xOffSet+40, yOffSet+30, 10, 10));
        g2.fill(new Ellipse2D.Double(xOffSet+60, yOffSet+30, 10, 10));
        if(color == "w")
        {
            g2.setColor(Color.DARK_GRAY);
        }
        else
        {
            g2.setColor(Color.LIGHT_GRAY);
        }

        g2.setStroke(new BasicStroke(3));
        g2.draw(new Ellipse2D.Double(xOffSet+30, yOffSet+25, 40, 25));
        g2.draw(new Ellipse2D.Double(xOffSet+40, yOffSet+30, 10, 10));
        g2.draw(new Ellipse2D.Double(xOffSet+60, yOffSet+30, 10, 10));

        if(color == "w")
        {
            g2.setColor(new Color(255,253,208));
        }
        else
        {
            g2.setColor(Color.BLACK);
        }


        g2.fill(new Rectangle2D.Double(xOffSet+40, yOffSet+50, 20, 25));
        g2.fill(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
        int x[]={xOffSet+20,xOffSet+80,xOffSet+50};
        int y[]={yOffSet+35,yOffSet+35,yOffSet+5};
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


        g2.draw(new Rectangle2D.Double(xOffSet+40, yOffSet+50, 20, 25));
        g2.draw(new Rectangle2D.Double(xOffSet+5, yOffSet+75, 90, 20));
        g2.drawPolygon(x, y, 3);

        g2.draw(new Line2D.Double(xOffSet+35, yOffSet+50, xOffSet+65, yOffSet+50));
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

    private boolean canPass(int x, int y, int xPos, int yPos)
    {
        if(Draw.getTile(xPos, yPos) != null) {
            Piece piece = Draw.getTile(xPos, yPos).getOcc();
            if (piece == null)
            {
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
        if (canPass(x, y, xPos, yPos))
        {
            xPos = x + 2;
            if (canPass(x, y, xPos, yPos))
            {
                xPos = x + 3;
                if (isMoveValid(x, y, xPos, yPos))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }

        xPos = x - 1;
        if (canPass(x, y, xPos, yPos))
        {
            xPos = x - 2;
            if (canPass(x, y, xPos, yPos))
            {
                xPos = x - 3;
                if (isMoveValid(x, y, xPos, yPos))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }

        //reset x
        xPos = x;

        yPos = y + 1;
        if (canPass(x, y, xPos, yPos))
        {
            yPos = y + 2;
            if (canPass(x, y, xPos, yPos))
            {
                yPos = y + 3;
                if (isMoveValid(x, y, xPos, yPos))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }

        yPos = y - 1;
        if (canPass(x, y, xPos, yPos))
        {
            yPos = y - 2;
            if (canPass(x, y, xPos, yPos))
            {
                yPos = y - 3;
                if (isMoveValid(x, y, xPos, yPos))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }

        xPos = x + 1;
        yPos = y + 1;
        if (canPass(x, y, xPos, yPos))
        {
            xPos = x + 2;
            yPos = y + 2;
            if (isMoveValid(x, y, xPos, yPos))
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            xPos = x + 1;
            yPos = y + 2;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos-1))
            {
                xPos = x + 1;
                yPos = y + 3;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos-1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
            xPos = x + 2;
            yPos = y + 1;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos-1))
            {
                xPos = x + 3;
                yPos = y + 1;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos-1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }
        xPos = x - 1;
        yPos = y + 1;
        if (canPass(x, y, xPos, yPos))
        {
            xPos = x - 2;
            yPos = y + 2;
            if (isMoveValid(x, y, xPos, yPos))
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            xPos = x - 1;
            yPos = y + 2;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos-1))
            {
                xPos = x - 1;
                yPos = y + 3;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos-1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
            xPos = x - 2;
            yPos = y + 1;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos-1))
            {
                xPos = x - 3;
                yPos = y + 1;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos-1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }
        xPos = x + 1;
        yPos = y - 1;
        if (canPass(x, y, xPos, yPos))
        {
            xPos = x + 2;
            yPos = y - 2;
            if (isMoveValid(x, y, xPos, yPos))
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            xPos = x + 1;
            yPos = y - 2;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos+1))
            {
                xPos = x + 1;
                yPos = y - 3;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos+1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
            xPos = x + 2;
            yPos = y - 1;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos+1))
            {
                xPos = x + 3;
                yPos = y - 1;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos-1, yPos+1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }
        xPos = x - 1;
        yPos = y - 1;
        if (canPass(x, y, xPos, yPos))
        {
            xPos = x - 2;
            yPos = y - 2;
            if (isMoveValid(x, y, xPos, yPos))
            {
                posMoves.add(Draw.getTile(xPos, yPos));
            }
            xPos = x - 1;
            yPos = y - 2;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos+1))
            {
                xPos = x - 1;
                yPos = y - 3;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos+1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
            xPos = x - 2;
            yPos = y - 1;
            if (canPass(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos+1))
            {
                xPos = x - 3;
                yPos = y - 1;
                if (isMoveValid(x, y, xPos, yPos) && canPass(x, y, xPos+1, yPos+1))
                {
                    posMoves.add(Draw.getTile(xPos, yPos));
                }
            }
        }
        System.out.println(posMoves);
        return posMoves;
    }


}
