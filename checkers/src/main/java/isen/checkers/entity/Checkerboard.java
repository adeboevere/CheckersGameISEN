package isen.checkers.entity;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;


public class Checkerboard extends Applet {
	
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
        
        int row;
        int col;
        int x,y;
     
        for ( row = 0;  row < 10;  row++ ) {
        
           for ( col = 0;  col < 10;  col++) {
              x = col * 60;
              y = row * 60;
              if ( (row % 2) == (col % 2) )
                 g.setColor(Color.white);
              else
                 g.setColor(Color.black);
              g.fillRect(x, y, 20, 20);
           } 
        
        }
     
     }

  }
