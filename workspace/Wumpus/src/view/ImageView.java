package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Grid;


public class ImageView extends JPanel implements Observer{

	private Grid grid;
	private Image hunter, wumpus, blood, slime, goop, ground, slimePit;
	private String direction;
	Timer timer;
	private int tic;
	int n;
	
	
	public ImageView(Grid grid){
		this.grid = grid;
		try {
			hunter = ImageIO.read(new File("./images/TheHunter.png"));
		   	wumpus = ImageIO.read(new File("./images/Wumpus.png"));
		   	blood = ImageIO.read(new File("./images/Blood.png"));
		   	slime = ImageIO.read(new File("./images/Slime.png"));
		   	goop = ImageIO.read(new File("./images/Goop.png"));
		   	ground = ImageIO.read(new File("./images/Ground.png"));
		   	slimePit = ImageIO.read(new File("./images/SlimePit.png"));
		}
		
		catch (IOException e) {
		      e.printStackTrace();
		    }
		
		this.setSize(500,700);
	}
	
	@Override
	public void update(Observable observable, Object dir) {
		grid = (Grid) observable;
		direction = (String) dir;
		
		repaint();
	}
	
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		int mapLength = grid.getMapLength();
		
		Image[][] imageGrid = new Image[mapLength][mapLength];
		String[][] mapFog = grid.getMapFog();
		int tempi=0, tempj=0;
		//paint showing all images
		for(int i = 0; i< mapLength; i=i+1){
			for( int j=0; j< mapLength; j=j+1){
				
				if(grid.getTileAt(i, j) == "W"){
					imageGrid[i][j] = wumpus;
				}
				else if(grid.getTileAt(i, j) == "S"){
					imageGrid[i][j] = slime;
				}
				else if(grid.getTileAt(i, j) == "G"){
					imageGrid[i][j] = goop;
				}
				else if(grid.getTileAt(i, j) == "B"){
					imageGrid[i][j] = blood;
				}
				else if(grid.getTileAt(i, j) == "P"){
					imageGrid[i][j] = slimePit;
				}	
			}

		}
		
		if(direction == "up"){
			imageGrid[tempi][tempj] = null;
			imageGrid[tempi-1][tempj] = hunter;
		}
		if(direction == "down"){
			imageGrid[tempi][tempj] = null;
			imageGrid[tempi+1][tempj] = hunter;
		}
		if(direction == "left"){
			imageGrid[tempi][tempj] = null;
			imageGrid[tempi][tempj-1] = hunter;
		}
		if(direction == "right"){
			imageGrid[tempi][tempj] = null;
			imageGrid[tempi][tempj] = hunter;}
		
		
		
		for (int r = 0; r < 500; r += 50){
		      for (int c = 0; c < 500; c += 50){
		    	  if(mapFog[c/50][r/50]!= "X"){
		    		  g2d.drawImage(ground, r, c, null);
		    		  g2d.drawImage(imageGrid[c/50][r/50], r, c, null);
		    		  if(mapFog[c/50][r/50] == "O"){
		    			  tempi = c/50;
		    			  tempj = r/50;

		    		  }
		    	  }
			}		
		}
		
		g2d.drawImage(hunter, tempj*50, tempi*50, null);
		
		
		
		
		if(grid.getTileAt(tempi, tempj)=="P" || grid.getTileAt(tempi, tempj)=="W" || grid.getFiredArrow()) {
			for (int r = 0; r < 500; r += 50){
			      for (int c = 0; c < 500; c += 50){
		    		  g2d.drawImage(ground, r, c, null);
		    		  g2d.drawImage(imageGrid[c/50][r/50], r, c, null);
		    		  if(mapFog[c/50][r/50] == "O"){
		    			  tempi = c/50;
		    			  tempj = r/50;
		    		  }
		    	  
				}			
			}
			
			g2d.drawImage(hunter, tempj*50, tempi*50, null);
		}
		
		
		repaint();
	}
	
}
