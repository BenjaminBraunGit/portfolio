package com.superChess;
import com.superChess.pieces.*;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class Draw extends JComponent
{
	
	

	static Tile[][] board = new Tile[8][8];
	static JFrame frame = new JFrame();
	static ArrayList<Tile> possMoves = new ArrayList<>();
	//static JPanel pane = new JPanel();
	
	static int selectorX = 0;
	static int selectorY = 0;
	static int opt = 0;
	
	static Piece weakW = new Pawn("w");
	static Piece medW1 = new Rook("w");
	static Piece medW2 = new Knight("w");
	static Piece medW3 = new Bishop("w");
	static Piece strongW = new Queen("w");
	
	
	static Piece weakB = new Pawn("b");
	static Piece medB1 = new Rook("b");
	static Piece medB2 = new Knight("b");
	static Piece medB3 = new Bishop("b");
	static Piece strongB = new Queen("b");
	
	static boolean eligibleCastleWL = true;
	static boolean eligibleCastleWR = true;
	
	static boolean eligibleCastleBL = true;
	static boolean eligibleCastleBR = true;

    static boolean mimicWizard = false;
	
	static boolean click = false;
	static boolean move = false;
	
	static String currentTurn = "w"; 
	


	
	public static void main(String[] args)
	{
		
		//frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setTitle("Braun Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Draw component = new Draw();
		
		frame.addKeyListener(new KeyListener() 
		{
        @Override
        public void keyTyped(KeyEvent e) 
        {
        	
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            //System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            if(e.getKeyChar() == 'w')
            {
        		if(selectorY > 0)
        		{
        			selectorY --;
        			move = true;
        			//System.out.println("seleY = " + selectorY);
        		}
            	
            	 //System.out.println("works");
            }
            if(e.getKeyChar() == 's')
            {
            	if(selectorY < 7)
        		{
        			selectorY ++;
        			move = true;
        			//System.out.println("seleY = " + selectorY);
        		}
            }
            if(e.getKeyChar() == 'a')
            {
            	if(selectorX > 0)
        		{
        			selectorX --;
        			move = true;
        			//System.out.println("seleX = " + selectorX);
        		}
            }
            if(e.getKeyChar() == 'd')
            {
            	if(selectorX < 7)
        		{
        			selectorX ++;
        			move = true;
        			//System.out.println("seleX = " + selectorX);
        		}
            }
            if(e.getKeyChar() == 'q')
            {
            	if(opt > 0)
        		{
        			opt --;
        			move = true;
        			//System.out.println("seleX = " + selectorX);
        		}
            }
            if(e.getKeyChar() == 'e')
            {
            	if(opt < 3)
        		{
        			opt ++;
        			move = true;
        			//System.out.println("seleX = " + selectorX);
        		}
            }
            if(e.getKeyChar() == ' ')
            {
            	if(!click)
        		{
        			click = true;
        			//System.out.println("Clicked!");
        		}
            }
        }

        @Override
        public void keyReleased(KeyEvent e) 
        {
        	
        }
    });
		
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				if((i+j)%2 == 0)
				{
					board[i][j] = new Tile(null, "w");
				}
				else
				{
					board[i][j] = new Tile(null, "b");
				}
			}
		}
		
		opt = 0;
		move = true;
		board[2][1].setOcc(weakW);
		board[2][3].setOcc(medW1);
		board[2][4].setOcc(medW2);
		board[2][5].setOcc(medW3);
		board[2][6].setOcc(strongW);
		
		board[5][1].setOcc(weakB);
		board[5][3].setOcc(medB1);
		board[5][4].setOcc(medB2);
		board[5][5].setOcc(medB3);
		board[5][6].setOcc(strongB);
		
		selectorY = 2;
		selectorX = 1;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					board[2][1].setOcc(new Pawn(currentTurn));
				}
				else if(opt == 1)
				{
					board[2][1].setOcc(new Barricade(currentTurn));
				}
				else if(opt == 2)
				{
                    board[2][1].setOcc(new Minion(currentTurn));
				}
				else if(opt == 3)
				{
					opt = 2;
				}
				
				selectorY = 2;
				selectorX = 1;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "b";
		
		selectorY = 5;
		selectorX = 1;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 0;
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					board[5][1].setOcc(new Pawn(currentTurn));
				}
				else if(opt == 1)
				{
					board[5][1].setOcc(new Barricade(currentTurn));
				}
				else if(opt == 2)
				{
                    board[5][1].setOcc(new Minion(currentTurn));
				}
				else if(opt == 3)
				{
					opt = 2;
				}
				
				selectorY = 5;
				selectorX = 1;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "w";
		
		selectorY = 2;
		selectorX = 3;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 0;
		
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					board[2][3].setOcc(new Rook(currentTurn));
				}
				else if(opt == 1)
				{
					board[2][3].setOcc(new Ranger(currentTurn));
				}
				else if(opt == 2)
				{
					opt = 1;
				}
				else if(opt == 3)
				{
					opt = 1;
				}
				
				selectorY = 2;
				selectorX = 3;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "b";
		
		selectorY = 5;
		selectorX = 3;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 0;
		
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					board[5][3].setOcc(new Rook(currentTurn));
				}
				else if(opt == 1)
				{
					board[5][3].setOcc(new Ranger(currentTurn));
				}
				else if(opt == 2)
				{
					opt = 1;
				}
				else if(opt == 3)
				{
					opt = 1;
				}
				
				selectorY = 5;
				selectorX = 3;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "w";
		
		selectorY = 2;
		selectorX = 4;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 1;
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					opt = 1;
				}
				else if(opt == 1)
				{
					board[2][4].setOcc(new Knight(currentTurn));
				}
				else if(opt == 2)
				{
					board[2][4].setOcc(new Bishop(currentTurn));
				}
				else if(opt == 3)
				{
					board[2][4].setOcc(new Squire(currentTurn));		
				}
				
				selectorY = 2;
				selectorX = 4;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "b";
		
		selectorY = 5;
		selectorX = 4;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 1;
		
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					opt = 1;
				}
				else if(opt == 1)
				{
					board[5][4].setOcc(new Knight(currentTurn));
				}
				else if(opt == 2)
				{
					board[5][4].setOcc(new Bishop(currentTurn));
				}
				else if(opt == 3)
				{
					board[5][4].setOcc(new Squire(currentTurn));		
				}
				
				selectorY = 5;
				selectorX = 4;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "w";
		
		selectorY = 2;
		selectorX = 5;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 2;
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					opt = 1;
				}
				else if(opt == 1)
				{
					board[2][5].setOcc(new Knight(currentTurn));
				}
				else if(opt == 2)
				{
					board[2][5].setOcc(new Bishop(currentTurn));
				}
				else if(opt == 3)
				{
					board[2][5].setOcc(new Squire(currentTurn));		
				}
				
				selectorY = 2;
				selectorX = 5;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "b";
		
		selectorY = 5;
		selectorX = 5;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 2;
		
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					opt = 1;
				}
				else if(opt == 1)
				{
					board[5][5].setOcc(new Knight(currentTurn));
				}
				else if(opt == 2)
				{
					board[5][5].setOcc(new Bishop(currentTurn));
				}
				else if(opt == 3)
				{
					board[5][5].setOcc(new Squire(currentTurn));		
				}
				
				selectorY = 5;
				selectorX = 5;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "w";
		
		selectorY = 2;
		selectorX = 6;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 0;
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					board[2][6].setOcc(new Queen(currentTurn));
				}
				else if(opt == 1)
				{
                    board[2][6].setOcc(new Wizard(currentTurn));
				}
				else if(opt == 2)
				{
					opt = 1;
				}
				else if(opt == 3)
				{
					opt = 1;
				}
				
				selectorY = 2;
				selectorX = 6;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "b";
		
		selectorY = 5;
		selectorX = 6;
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		opt = 0;
		
		
		while(!click)
		{
			System.out.print("");
			if(move)
			{
				
				if(opt == 0)
				{
					board[5][6].setOcc(new Queen(currentTurn));
				}
				else if(opt == 1)
				{
                    board[5][6].setOcc(new Wizard(currentTurn));
				}
				else if(opt == 2)
				{
					opt = 1;
				}
				else if(opt == 3)
				{
					opt = 1;
				}
				
				selectorY = 5;
				selectorX = 6;
				frame.remove(component);
				component = new Draw();
				frame.add(component);
				frame.setVisible(true);
				move = false;
			}
		}
		
		click = false;
		
		currentTurn = "w";
		
		weakW = board[2][1].getOcc();
		medW1 = board[2][3].getOcc();
		medW2 = board[2][4].getOcc();
		medW3 = board[2][5].getOcc();
		strongW = board[2][6].getOcc();
		
		weakB = board[5][1].getOcc();
		medB1 = board[5][3].getOcc();
		medB2 = board[5][4].getOcc();
		medB3 = board[5][5].getOcc();
		strongB = board[5][6].getOcc();
		
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				if((i+j)%2 == 0)
				{
					board[i][j] = new Tile(null, "w");
				}
				else
				{
					board[i][j] = new Tile(null, "b");
				}
				//initialize white pieces
				if(i == 7 && j == 0 || i == 7 && j == 7)
				{
					board[i][j].setOcc(medW1);
				}
				if(i == 7 && j == 1 || i == 7 && j == 6)
				{
					board[i][j].setOcc(medW2);
				}
				if(i == 7 && j == 2 || i == 7 && j == 5)
				{
					board[i][j].setOcc(medW3);
				}
				if(i == 7 && j == 3)
				{
					board[i][j].setOcc(strongW);
				}
				if(i == 7 && j == 4)
				{
					board[i][j].setOcc(new King("w"));
				}
				if(i == 6)
				{
					board[i][j].setOcc(weakW);
				}
				//initialize black pieces
				if(i == 1)
				{
					board[i][j].setOcc(weakB);
				}
				if(i == 0 && j == 0 || i == 0 && j == 7)
				{
					board[i][j].setOcc(medB1);
				}
				if(i == 0 && j == 1 || i == 0 && j == 6)
				{
					board[i][j].setOcc(medB2);
				}
				if(i == 0 && j == 2 || i == 0 && j == 5)
				{
					board[i][j].setOcc(medB3);
				}
				if(i == 0 && j == 3)
				{
					board[i][j].setOcc(strongB);
				}
				if(i == 0 && j == 4)
				{
					board[i][j].setOcc(new King("b"));
				}
			}
		}
		
		
		
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		
		
		//while(userInput != "quit")
		while(true)
		{
			int initialXPos = 0; 
			int initialYPos = 0;
			int destinationXPos = 0;
			int destinationYPos=0;
			possMoves = new ArrayList<Tile>();
			drawBoard();
			frame.remove(component);
			component = new Draw();
			frame.add(component);
			frame.setVisible(true);
			while(true)
			{
				
				/*userInput = reader.nextLine();
				if(userInput == "quit")
				{
					break;
				}
				
				initialXPos = Integer.parseInt(userInput.substring(0,1)); 
				initialYPos = Integer.parseInt(userInput.substring(1,2));
				*/
				
				while(!click)
				{
					System.out.print("");
					if(move)
					{
						//System.out.println("board is redrawing!");
						frame.remove(component);
						component = new Draw();
						frame.add(component);
						frame.setVisible(true);
						move = false;
					}
				}
				
				initialXPos = selectorY;
				initialYPos = selectorX;
				click = false;
				
				System.out.println(currentTurn);
				//int destinationXPos = Integer.parseInt(userInput.substring(3,4));
				//int destinationYPos = Integer.parseInt(userInput.substring(4,5));
				if(board[initialXPos][initialYPos].getOcc() != null && board[initialXPos][initialYPos].getOcc().getTeam() == currentTurn)
				{
					
						
					possMoves = board[initialXPos][initialYPos].getOcc().getMoves(initialXPos, initialYPos);
					
					if(currentTurn == "w")
					{
						if(eligibleCastleWL && !inCheck(currentTurn) && board[7][4].getOcc() != null && board[7][0].getOcc() != null && board[initialXPos][initialYPos].getOcc().getSym() == "K" &&  board[7][4].getOcc().getSym() == "K" && board[7][0].getOcc().getSym() == "R" && board[7][1].getOcc() == null && board[7][2].getOcc() == null&& board[7][3].getOcc() == null)
						{
							possMoves.add(board[7][2]);
							//Draw.movePiece(7, 0, 7, 3);
						}
						if(eligibleCastleWR && !inCheck(currentTurn) && board[7][4].getOcc() != null && board[7][7].getOcc() != null && board[initialXPos][initialYPos].getOcc().getSym() == "K" &&  board[7][4].getOcc().getSym() == "K" && board[7][7].getOcc().getSym() == "R" && board[7][6].getOcc() == null && board[7][5].getOcc() == null)
						{
							possMoves.add(board[7][6]);
							//Draw.movePiece(7, 0, 7, 3);
						}
					}
					
					else
					{
						if(eligibleCastleBL && !inCheck(currentTurn) && board[0][4].getOcc() != null && board[0][0].getOcc() != null && board[initialXPos][initialYPos].getOcc().getSym() == "k" &&  board[0][4].getOcc().getSym() == "k" && board[0][0].getOcc().getSym() == "r" && board[0][1].getOcc() == null && board[0][2].getOcc() == null&& board[0][3].getOcc() == null)
						{
							possMoves.add(board[0][2]);
							//Draw.movePiece(7, 0, 7, 3);
						}
						if(eligibleCastleBR && !inCheck(currentTurn) && board[0][4].getOcc() != null && board[0][7].getOcc() != null && board[initialXPos][initialYPos].getOcc().getSym() == "k" &&  board[0][4].getOcc().getSym() == "k" && board[0][7].getOcc().getSym() == "r" && board[0][6].getOcc() == null && board[0][5].getOcc() == null)
						{
							possMoves.add(board[0][6]);
							//Draw.movePiece(7, 0, 7, 3);
						}
					}
					
					if(board[initialXPos][initialYPos].getOcc() instanceof Wizard)
					{
						removeSwapsToCheck(possMoves, initialXPos, initialYPos);
					}
					else
					{
						removeMovesToCheck(possMoves, initialXPos, initialYPos);
					}
					
					if(currentTurn == "w")
					{
						if(!possMoves.contains(board[7][3]) && possMoves.contains(board[7][2]) && !inCheck(currentTurn) && board[initialXPos][initialYPos].getOcc().getSym() == "K" && board[7][4].getOcc() != null && board[7][4].getOcc().getSym() == "K")
						{
							possMoves.remove(board[7][2]);
							//Draw.movePiece(7, 0, 7, 3);
						}
						
						if(!possMoves.contains(board[7][5]) && possMoves.contains(board[7][6]) && !inCheck(currentTurn) && board[initialXPos][initialYPos].getOcc().getSym() == "K" && board[7][4].getOcc() != null && board[7][4].getOcc().getSym() == "K")
						{
							possMoves.remove(board[7][6]);
							//Draw.movePiece(7, 0, 7, 3);
						}
					}
					else
					{
						if(!possMoves.contains(board[0][3]) && possMoves.contains(board[0][2]) && !inCheck(currentTurn) && board[initialXPos][initialYPos].getOcc().getSym() == "k" && board[0][4].getOcc() != null && board[0][4].getOcc().getSym() == "k")
						{
							possMoves.remove(board[0][2]);
							//Draw.movePiece(7, 0, 7, 3);
						}
						
						if(!possMoves.contains(board[0][5]) && possMoves.contains(board[0][6]) && !inCheck(currentTurn) && board[initialXPos][initialYPos].getOcc().getSym() == "k" && board[0][4].getOcc() != null && board[0][4].getOcc().getSym() == "k")
						{
							possMoves.remove(board[0][6]);
							//Draw.movePiece(7, 0, 7, 3);
						}
					}
					
					
					drawBoard(possMoves);
					frame.remove(component);
					component = new Draw();
					frame.add(component);
					frame.setVisible(true);
					
					/*
					userInput = reader.nextLine();
					
					destinationXPos = Integer.parseInt(userInput.substring(0,1));
					destinationYPos = Integer.parseInt(userInput.substring(1,2));
					*/
					
					while(!click)
					{
						System.out.print("");
						if(move)
						{
							frame.remove(component);
							component = new Draw();
							frame.add(component);
							frame.setVisible(true);
							move = false;
						}
					}
					
					
					click = false;
					destinationXPos = selectorY;
					destinationYPos = selectorX;
					mimicWizard = false;
					
					if((board[initialXPos][initialYPos].getOcc().getSym() == "SQ" || board[initialXPos][initialYPos].getOcc().getSym() == "sq") && board[destinationXPos][destinationYPos].getOcc() != null && board[destinationXPos][destinationYPos].getOcc().getTeam() == currentTurn && possMoves.contains(board[destinationXPos][destinationYPos]))
					{		
						//System.out.println(currentTurn);
						//int destinationXPos = Integer.parseInt(userInput.substring(3,4));
						//int destinationYPos = Integer.parseInt(userInput.substring(4,5));
                        while(board[destinationXPos][destinationYPos].getOcc() instanceof Squire && board[destinationXPos][destinationYPos].getOcc().getTeam() == currentTurn && possMoves.contains(board[destinationXPos][destinationYPos]))
						{
							possMoves = board[destinationXPos][destinationYPos].getOcc().getMoves(destinationXPos, destinationYPos);

                            removeMovesToCheck(possMoves, initialXPos, initialYPos);


							drawBoard(possMoves);
							frame.remove(component);
							component = new Draw();
							frame.add(component);
							frame.setVisible(true);

							while(!click)
							{
								System.out.print("");
								if(move)
								{
									frame.remove(component);
									component = new Draw();
									frame.add(component);
									frame.setVisible(true);
									move = false;
								}
							}
							destinationXPos = selectorY;
							destinationYPos = selectorX;
							click = false;
						}
//BUG PLS FIX
						possMoves = board[destinationXPos][destinationYPos].getOcc().getMoves(destinationXPos, destinationYPos);

                        if(board[destinationXPos][destinationYPos].getOcc() instanceof Wizard)
                        {
                            mimicWizard = true;
                            removeSwapsToCheck(possMoves, initialXPos, initialYPos);
                        }
                        else
                        {
                            removeMovesToCheck(possMoves, initialXPos, initialYPos);
                        }

						
						drawBoard(possMoves);
						frame.remove(component);
						component = new Draw();
						frame.add(component);
						frame.setVisible(true);
						
						while(!click)
						{
							System.out.print("");
							if(move)
							{
								frame.remove(component);
								component = new Draw();
								frame.add(component);
								frame.setVisible(true);
								move = false;
							}
						}
						destinationXPos = selectorY;
						destinationYPos = selectorX;
						click = false;
					}


                    if(possMoves.contains(board[destinationXPos][destinationYPos]))
                    {
                        if(board[initialXPos][initialYPos].getOcc() instanceof Wizard || mimicWizard)
                        {
                            Draw.swapPiece(initialXPos, initialYPos, destinationXPos, destinationYPos);
                            mimicWizard = false;
                        }
                        else
                        {
                            Draw.movePiece(initialXPos, initialYPos, destinationXPos, destinationYPos);
                        }
                        System.out.println("Movement executed succsessfuly");
                        possMoves = new ArrayList<Tile>();

                        break;
                    }

					else if(board[destinationXPos][destinationYPos].getOcc() != null && board[destinationXPos][destinationYPos].getOcc().getTeam() == currentTurn)
					{
						possMoves = new ArrayList<Tile>();
						frame.remove(component);
						component = new Draw();
						frame.add(component);
						frame.setVisible(true);
						click = true;
					}
					else
					{
						System.out.println("Invalid Destination!");
						possMoves = new ArrayList<Tile>();
						frame.remove(component);
						component = new Draw();
						frame.add(component);
						frame.setVisible(true);
					}
				}
				else
				{
					System.out.println("Invalid Tile!");
				}
			}

			System.out.println(destinationXPos);
            System.out.println(destinationYPos);
			if((destinationXPos == 0 && board[destinationXPos][destinationYPos].getOcc().getSym() == "P") || (destinationXPos == 7 && board[destinationXPos][destinationYPos].getOcc().getSym() == "p"))
			{
				opt = 0;
				move = true;
				
				while(!click)
				{
					System.out.print("");
					if(move)
					{
						
						if(opt == 0)
						{
							board[destinationXPos][destinationYPos].setOcc(new Queen(currentTurn));
						}
						else if(opt == 1)
						{
							board[destinationXPos][destinationYPos].setOcc(new Knight(currentTurn));
						}
						else if(opt == 2)
						{
							board[destinationXPos][destinationYPos].setOcc(new Rook(currentTurn));
						}
						else if(opt == 3)
						{
							board[destinationXPos][destinationYPos].setOcc(new Bishop(currentTurn));	
						}
						
						selectorY = destinationXPos;
						selectorX = destinationYPos;
						frame.remove(component);
						component = new Draw();
						frame.add(component);
						frame.setVisible(true);
						move = false;
					}
				}
				
			}
			
			
			//switch whos turn it is
			if(currentTurn == "w")
			{	
				if(board[destinationXPos][destinationYPos].getOcc().getSym() == "K" && eligibleCastleWL && destinationXPos == 7 && destinationYPos == 2 && initialXPos == 7 && initialYPos == 4)
				{
					Draw.movePiece(7, 0, 7, 3);
				}
				
				else if(board[destinationXPos][destinationYPos].getOcc().getSym() == "K" && eligibleCastleWR && destinationXPos == 7 && destinationYPos == 6 && initialXPos == 7 && initialYPos == 4)
				{
					Draw.movePiece(7, 7, 7, 5);
				}
				
				if(board[7][4].getOcc() == null)
				{
					eligibleCastleWL = false;
					eligibleCastleWR = false;
				}
				
				else if(board[7][0].getOcc() == null || board[7][0].getOcc().getSym() != "R")
				{
					eligibleCastleWL = false;
				}
				
				else if(board[7][7].getOcc() == null || board[7][7].getOcc().getSym() != "R")
				{
					eligibleCastleWR = false;
				}
				currentTurn = "b";
				//System.out.println("It is now Black's turn!");
			}
			else
			{
				if(board[destinationXPos][destinationYPos].getOcc().getSym() == "k" && eligibleCastleBL && destinationXPos == 0 && destinationYPos == 2 && initialXPos == 0 && initialYPos == 4)
				{
					Draw.movePiece(0, 0, 0, 3);
				}
				
				else if(board[destinationXPos][destinationYPos].getOcc().getSym() == "k" && eligibleCastleBR && destinationXPos == 0 && destinationYPos == 6 && initialXPos == 0 && initialYPos == 4)
				{
					Draw.movePiece(0, 7, 0, 5);
				}
				
				if(board[7][4].getOcc() == null)
				{
					eligibleCastleBL = false;
					eligibleCastleBR = false;
				}
				
				else if(board[0][0].getOcc() == null || board[0][0].getOcc().getSym() != "r")
				{
					eligibleCastleBL = false;
				}
				
				else if(board[0][7].getOcc() == null || board[0][7].getOcc().getSym() != "r")
				{
					eligibleCastleBR = false;
				}
				currentTurn = "w";
				//System.out.println("It is now White's turn!");
			}
			
			if(checkMate())
			{
				break;
			}
			
		}
		
		//reader.close();
		frame.remove(component);
		component = new Draw();
		frame.add(component);
		frame.setVisible(true);
		
		if(currentTurn == "w")
		{
			System.out.println("White is in Checkmate!");	
			System.out.println("Black wins the game!");
		}
		else
		{
			System.out.println("Black is in Checkmate!");
			System.out.println("White wins the game!");
		}
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Line2D.Double line1 = new Line2D.Double(99, 99, 900, 99);
		Line2D.Double line2 = new Line2D.Double(99, 99, 99, 900);
		Line2D.Double line3 = new Line2D.Double(900, 99, 900, 900);
		Line2D.Double line4 = new Line2D.Double(99, 900, 900, 900);
		
		g2.draw(line1);
		g2.draw(line2);
		g2.draw(line3);
		g2.draw(line4);
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
//				if(posMoves.contains(y))
//				{
//					leftEdge = "*";
//					rightEdge = "*";
//				}
				
				if(board[i][j].getColor() == "w")
				{
					g2.setColor(Color.WHITE);
				}
				else
				{
					//g2.setColor(new Color(210,105,30));
					g2.setColor(Color.GRAY);
				}
				g2.fill(new Rectangle2D.Double((j)*100, (i)*100,100,100));
				if(board[i][j].getOcc() != null)
				{
					if(board[i][j].getOcc().getTeam() == "w")
					{
						g2.setColor(new Color(255,253,208));
					}
					else
					{
						g2.setColor(Color.BLACK);
					}
					board[i][j].getOcc().drawPiece(g2, (j)*100, (i)*100);
				}
				if(possMoves.contains(board[i][j]))
				{
					if(currentTurn == "w")
					{
						g2.setColor(new Color (0, 191, 255, 150));
					}
					else
					{
						g2.setColor(new Color (255, 64, 0, 150));
					}

					g2.fill(new Rectangle2D.Double(((j)*100), ((i)*100),100,100));
				}
				
				if(i == selectorY && j == selectorX)
				{

					g2.setColor(Color.RED);
					g2.setStroke(new BasicStroke(4));
					g2.draw(new Rectangle2D.Double(((j)*100)+2, ((i)*100)+2,96,96));
					g2.setStroke(new BasicStroke(1));
				}
			}
		}
		
	}
	
	
	public static void drawBoard()
	{
		int line = 0;
		System.out.print("   0  1  2  3  4  5  6  7 ");
		for(Tile[] x : board)
		{
			System.out.println();
			System.out.print(line + " ");
			line++;
			for(Tile y : x)
			{
				if(y.getColor() == "w")
				{
					if(y.getOcc() != null)
					{
						System.out.print("[" + y.getOcc().getSym() + "]");
					}
					else
					{
						System.out.print("[ ]");
					}
				}
				else
				{
					if(y.getOcc() != null)
					{
						System.out.print("(" + y.getOcc().getSym() + ")");
					}
					else
					{
						System.out.print("( )");
					}
				}
			}
		}
	}
	
	public static void drawBoard(ArrayList<Tile> posMoves)
	{
		String leftEdge = "";
		String rightEdge = "";
		int line = 0;
        System.out.println();
		System.out.print("   0  1  2  3  4  5  6  7 ");
		for(Tile[] x : board)
		{
			System.out.println();
			System.out.print(line + " ");
			line++;
			for(Tile y : x)
			{
				if(posMoves.contains(y))
				{
					leftEdge = "*";
					rightEdge = "*";
				}
				else if(y.getColor() == "w")
				{
					leftEdge = "[";
					rightEdge = "]";
				}
				else if(y.getColor() == "b")
				{
					leftEdge = "(";
					rightEdge = ")";
				}
				
				if(y.getOcc() != null)
				{
					System.out.print(leftEdge + y.getOcc().getSym() + rightEdge);
				}
				else
				{
					System.out.print(leftEdge + " " + rightEdge);
				}
				
			}
		}
	}
	
	static public Tile getTile(int x, int y)
	{
		if(x > 7 || x < 0 || y > 7 || y < 0)
		{
			return null;
		}
		else
		{
			return board[x][y];
		}
	}
	
	static public void movePiece(int iniPosX, int iniPosY, int desPosX, int desPosY)
	{
		if(board[desPosX][desPosY].getOcc() == null)
		{
			board[desPosX][desPosY].setOcc(board[iniPosX][iniPosY].getOcc());
			board[iniPosX][iniPosY].setOcc(null);
			System.out.println("Piece moved!");
		}
		
		else if(board[desPosX][desPosY].getOcc() != null)
		{
			System.out.println("Piece taken!");
			board[desPosX][desPosY].setOcc(board[iniPosX][iniPosY].getOcc());
			board[iniPosX][iniPosY].setOcc(null);
		}
		
	}

	static public void swapPiece(int iniPosX, int iniPosY, int desPosX, int desPosY)
	{
		Piece iniTileOcc = board[iniPosX][iniPosY].getOcc();
		Piece desTileOcc = board[desPosX][desPosY].getOcc();

		if(board[desPosX][desPosY].getOcc() != null)
		{
			board[desPosX][desPosY].setOcc(iniTileOcc);
			board[iniPosX][iniPosY].setOcc(desTileOcc);
		}

	}

	static void removeMovesToCheck( ArrayList<Tile> possMoves, int initialXPos, int initialYPos)
	{
		Piece iniTileOcc;
		Piece desTileOcc;

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(possMoves.contains(board[i][j]))
				{
					iniTileOcc = board[initialXPos][initialYPos].getOcc();
					desTileOcc = board[i][j].getOcc();
					Draw.movePiece(initialXPos, initialYPos, i, j);
					//System.out.println("Movement executed succsessfuly");

					if(inCheck(currentTurn))
					{
						possMoves.remove(board[i][j]);
					}
					board[initialXPos][initialYPos].setOcc(iniTileOcc);
					board[i][j].setOcc(desTileOcc);
				}
			}
		}
	}

	static void removeSwapsToCheck( ArrayList<Tile> possMoves, int initialXPos, int initialYPos)
	{
		Piece iniTileOcc;
		Piece desTileOcc;

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(possMoves.contains(board[i][j]))
				{
					iniTileOcc = board[initialXPos][initialYPos].getOcc();
					desTileOcc = board[i][j].getOcc();
					Draw.swapPiece(initialXPos, initialYPos, i, j);
					//System.out.println("Movement executed succsessfuly");

					if(inCheck(currentTurn))
					{
						possMoves.remove(board[i][j]);
					}
					board[initialXPos][initialYPos].setOcc(iniTileOcc);
					board[i][j].setOcc(desTileOcc);
				}
			}
		}
	}
	
	static public boolean inCheck(String team)
	{
		int KingI = 0;
		int KingJ = 0;
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(board[i][j].getOcc() != null && board[i][j].getOcc().getTeam() == team)
				{
					if(board[i][j].getOcc().getSym() == "K" || board[i][j].getOcc().getSym() == "k")
					{
						KingI = i;
						KingJ = j;
					}
				}
			}
		}
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(board[i][j].getOcc() != null && !((board[i][j].getOcc() instanceof Wizard) || (board[i][j].getOcc() instanceof Squire && mimicWizard)) && board[i][j].getOcc().getTeam() != team && board[i][j].getOcc().getMoves(i, j).contains(board[KingI][KingJ]))
				{
					System.out.println(team + " IN CHECK!");
					return true;
				}
			
			}
		}
		return false;
	}
	
	static public boolean checkMate()
	{
		for(int a = 0; a < 8; a++)
		{
			for(int b = 0; b < 8; b++)
			{
				if(board[a][b].getOcc() != null && board[a][b].getOcc().getTeam() == currentTurn)
				{
					possMoves = board[a][b].getOcc().getMoves(a, b);
					
					Piece iniTileOcc = null;
					Piece desTileOcc = null;
					
					for(int i = 0; i < 8; i++)
					{
						for(int j = 0; j < 8; j++)
						{	
							if(possMoves.contains(board[i][j]))
							{
								iniTileOcc = board[a][b].getOcc();
								desTileOcc = board[i][j].getOcc();
								Draw.movePiece(a, b, i, j);
								//System.out.println("Movement executed succsessfuly");
								
								if(inCheck(currentTurn))
								{
									possMoves.remove(board[i][j]);
								}
								else
								{
									board[a][b].setOcc(iniTileOcc);
									board[i][j].setOcc(desTileOcc);
									return false;
								}
								board[a][b].setOcc(iniTileOcc);
								board[i][j].setOcc(desTileOcc);
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	static public JFrame getFrame()
	{
		return frame;
	}
}
