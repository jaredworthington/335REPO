package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.Grid;

public class WumpusGUI extends JFrame{
	
	// instanced gui objects
	private JPanel movementControls = new JPanel(new FlowLayout());
	private JPanel shootingControls = new JPanel(new FlowLayout());
	private JPanel mapArea1 = new JPanel(new FlowLayout());
	private ImageView mapArea2;
	private JPanel hintPanel = new JPanel(new FlowLayout());
	private JTextArea mapTextArea = new JTextArea("placeholder");
	private JLabel hintLabel = new JLabel("placeholder");
	private JTabbedPane multiPane = new JTabbedPane();
	// instance variables
	private Grid map;
	
	// constructor
	public WumpusGUI(){
		
	   // Basics
	   setTitle("Hunt the Wumpus");
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setLocation(20, 20);
	   setSize(1000, 700);
	   setLayout(new FlowLayout());
	   setVisible(true);
	   
	   //
	   setUpMap();
	   //
	   setUpControls();
	   //
	   setUpHints();
	   //
	}
	
	// instantiate gui
	public static void main(String[] args){
		new WumpusGUI();
	}
	
	// helper method int randoms
	private int randInt(int min, int max) {
	    return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	// generate map and display
	private void setUpMap(){
		
		// generate new map 
		map = new Grid();
		map.setWumpus(randInt(0,9), randInt(0,9));
		int count = 0;
		while (count < randInt(4,5)){
			int k = randInt(0,9);
			int j = randInt(0,9);
			if (map.getUsedSpace(k,j) == false){
				map.setPit(randInt(0,9), randInt(0,9));
				count++;
			}
		}
		// place a hunter
		count = 0;
		while (count != 1){
			int k = randInt(0,9);
			int j = randInt(0,9);
			if (map.getUsedSpace(k,j) == false){
				map.setHunter(k, j);
				count++;
			}
		}
		map.setFog();
		
		// set up text area
		mapTextArea.setFont(new Font("monospaced", Font.PLAIN, 20));
		mapTextArea.setLocation(370, 30);
		mapTextArea.setEditable(false);
		
		
		// set up panel
		mapArea1.setSize(700, 500);
		mapArea1.setLocation(350, 10);
		mapArea1.setBackground(Color.WHITE);
		mapArea1.add(mapTextArea);
		
		mapArea2 = new ImageView(map);
		mapArea2.setOpaque(true);
		mapArea2.setBackground(Color.BLACK);
		
	

		multiPane.add(mapArea1, "Text View");
		multiPane.add(mapArea2, "Image View");
		
		multiPane.setPreferredSize(new Dimension(530,530));
		
		
		
		// Sets the initial board with fog. magically refresh jpanels to flowlayout.
		mapTextArea.setText("" + map.fogToString());
		
		
		// add to Frame
		this.add(multiPane);
	}
	
	// sets up buttons
	private void setUpControls(){
		
		// initialize buttons for movement
		JButton upMovementButton = new JButton("up");
		upMovementButton.addActionListener(new UpMovementListener());
		JButton rightMovementButton = new JButton("right");
		rightMovementButton.addActionListener(new RightMovementListener());
		JButton downMovementButton = new JButton("down");
		downMovementButton.addActionListener(new DownMovementListener());
		JButton leftMovementButton = new JButton("left");
		leftMovementButton.addActionListener(new LeftMovementListener());
		
		// Movement Label
		JLabel movementLabel = new JLabel("");

		// Movement Controls Panel
		movementControls.setSize(400, 250);
		movementControls.setBackground(Color.WHITE);
		movementControls.add(movementLabel);
		movementControls.add(upMovementButton);
		movementControls.add(rightMovementButton);
		movementControls.add(downMovementButton);
		movementControls.add(leftMovementButton);
		this.add(movementControls);
		
		// initialize buttons for arrow
		JButton upArrowButton = new JButton("up");
		upArrowButton.addActionListener(new UpArrowListener());
		JButton rightArrowButton = new JButton("right");
		rightArrowButton.addActionListener(new RightArrowListener());
		JButton downArrowButton = new JButton("down");
		downArrowButton.addActionListener(new DownArrowListener());
		JButton leftArrowButton = new JButton("left");
		leftArrowButton.addActionListener(new LeftArrowListener());
		
		// Controls Label
		JLabel shootingLabel = new JLabel("");

		// Shooting Controls Panel
		shootingControls.setSize(400, 250);
		shootingControls.setBackground(Color.WHITE);
		movementControls.add(shootingLabel);
		movementControls.add(upArrowButton);
		movementControls.add(rightArrowButton);
		movementControls.add(downArrowButton);
		movementControls.add(leftArrowButton);
		this.add(shootingControls);
		
		//magically refresh panels to flowlayout
		movementLabel.setText("Move Hunter");
		shootingLabel.setText("                                   Shoot Arrow");

	}
	
	// generate hintlabel panel.
	private void setUpHints(){
		
		// set up panel
		hintPanel.setSize(600, 100);
		hintPanel.add(hintLabel);
		
		// add to Frame
		this.add(hintPanel);
		
		// magically refresh
		hintLabel.setText("                    " + map.getHint() + "                    ");
	}
	
	// goes up
	private class UpMovementListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// move and refresh, check death
			String deathMessage = map.moveHunter("up");
			if (deathMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + deathMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.fogToString());
				hintLabel.setText("                    " + map.getHint() + "                    ");
			}
		
		}
		
		
	}
	// goes right
	private class RightMovementListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// move and refresh, check death
			String deathMessage = map.moveHunter("right");
			if (deathMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + deathMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.fogToString());
				hintLabel.setText("                    " + map.getHint() + "                    ");
			}
		}
	}
	// goes down
	private class DownMovementListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			// move and refresh, check death
			String deathMessage = map.moveHunter("down");
			if (deathMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + deathMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.fogToString());
				hintLabel.setText("                    " + map.getHint() + "                    ");
			}
		}
	}
	// goes left
	private class LeftMovementListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {		
			// move and refresh, check death
			String deathMessage = map.moveHunter("left");
			if (deathMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + deathMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.fogToString());
				hintLabel.setText("                    " + map.getHint() + "                    ");
			}
		}	
	}
	
	////// arrow listeners
	
	private class UpArrowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String winMessage = map.fireArrow("up");
			if (winMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + winMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + "You shot yourself with the arrow." + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}
		}
	}
	
	private class RightArrowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String winMessage = map.fireArrow("right");
			if (winMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + winMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + "You shot yourself with the arrow." + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}
		}
	}
	
	private class DownArrowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String winMessage = map.fireArrow("down");
			if (winMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + winMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + "You shot yourself with the arrow." + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}
		}
	}
	
	private class LeftArrowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String winMessage = map.fireArrow("left");
			if (winMessage.length() > 2){
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + winMessage + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}else{
				mapTextArea.setText("" + map.gridToString());
				hintLabel.setText("                    " + "You shot yourself with the arrow." + "                    ");
				movementControls.setVisible(false);
				shootingControls.setVisible(true);
			}
		}
	}


}
