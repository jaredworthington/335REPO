package model;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class Grid extends Observable{
	
	/***
	 * Instantiation
	 */
	
	//// note: making map into Object or Character causes null pointers like a mf
	private String[][] map;
	private String[][] mapFog;
	private boolean[][] usedSpaces;
	private boolean firedArrow;
	
	// overload: instantiates an empty map
	public Grid(){
		map = new String[10][10]; // background
		mapFog = new String[10][10]; // foreground
		usedSpaces = new boolean[10][10];
		cleanNulls();
	}
	
	// sets a wumpus
	public void setWumpus(int k, int j){
		map[k][j] = "W";
		usedSpaces[k][j] = true;
		
		///////// place the blood (N,E,S,W)
		// k+1
		if (k+1 < map.length){
			map[k+1][j] = "B";
			usedSpaces[k+1][j] = true;
		}else{
			map[0][j] = "B";
			usedSpaces[0][j] = true;
		}
		// j+1
		if (j+1 < map.length){ 
			map[k][j+1] = "B";
			usedSpaces[k][j+1] = true;
		}else{
			map[k][0] = "B";
			usedSpaces[k][0] = true;
		}
		// k-1
		if (k-1 > -1){
			map[k-1][j] = "B";
			usedSpaces[k-1][j] = true;
		}else{
			map[map.length-1][j] = "B";
			usedSpaces[map.length-1][j] = true;
		}
		// j-1
		if (j-1 > -1){
			map[k][j-1] = "B";
			usedSpaces[k][j-1] = true;
		}else{
			map[k][map.length-1] = "B";
			usedSpaces[k][map.length-1] = true;
		}
		
		
		/////// Place diagonally
		// k+1, j+1
		if (k+1 < map.length){
			if (j+1 < map.length){
				map[k+1][j+1] = "B";
				usedSpaces[k+1][j+1] = true;
			}else{
				map[k+1][0] = "B";
				usedSpaces[k+1][0] = true;
			}
		}else{
			if (!(j+1 < map.length)){
				map[0][0] = "B";
				usedSpaces[0][0] = true;
			}else{
				map[0][j+1] = "B";
				usedSpaces[0][j+1] = true;
			}
		}
		// k-1, j+1
		if (k-1 > -1){
			if (j+1 < map.length){
				map[k-1][j+1] = "B";
				usedSpaces[k-1][j+1] = true;
			}else{
				map[k-1][0] = "B";
				usedSpaces[k-1][0] = true;
			}
		}else{
			if (!(j+1 < map.length)){
				map[map.length-1][0] = "B";
				usedSpaces[map.length-1][0] = true;
			}else{
				map[map.length-1][j+1] = "B";
				usedSpaces[map.length-1][j+1] = true;
			}
		}
		// k+1, j-1
		if (k+1 < map.length){
			if (j-1 > -1){
				map[k+1][j-1] = "B";
				usedSpaces[k+1][j-1] = true;
			}else{
				map[k+1][map.length-1] = "B";
				usedSpaces[k+1][map.length-1] = true;
			}
		}else{
			if (!(j-1 > -1)){
				map[0][map.length-1] = "B";
				usedSpaces[0][map.length-1] = true;
			}else{
				map[0][j-1] = "B";
				usedSpaces[0][j-1] = true;
			}
		}
		// k-1, j-1
		if (k-1 > -1){
			if (j-1 > -1){
				map[k-1][j-1] = "B";
				usedSpaces[k-1][j-1] = true;
			}else{
				map[k-1][map.length-1] = "B";
				usedSpaces[k-1][map.length-1] = true;
			}
		}else{
			if (!(j-1 > -1)){
				map[map.length-1][map.length-1] = "B";
				usedSpaces[map.length-1][map.length-1] = true;
			}else{
				map[map.length-1][j-1] = "B";
				usedSpaces[map.length-1][j-1] = true;
			}
		}
		
		
		//////// place two blocks away
		// k+2
		if (k+2 < map.length){
			map[k+2][j] = "B";
			usedSpaces[k+2][j] = true;
		}else{
			if (k+2 == map.length + 1){
				map[1][j] = "B";
				usedSpaces[1][j] = true;
			}else{
				map[0][j] = "B";
				usedSpaces[0][j] = true;
			}
		}
		// j+2
		if (j+2 < map.length){ 
			map[k][j+2] = "B";
			usedSpaces[k][j+2] = true;
		}else{
			if (j+2 == map.length + 1){
				map[k][1] = "B";
				usedSpaces[k][1] = true;
			}else{
				map[k][0] = "B";
				usedSpaces[k][0] = true;
			}
		}
		// k-2
		if (k-2 > -1){
			map[k-2][j] = "B";
			usedSpaces[k-2][j] = true;
		}else{
			if (k-2 == -2){
				map[map.length - 2][j] = "B";
				usedSpaces[map.length - 2][j] = true;
			}else{
				map[map.length - 1][j] = "B";
				usedSpaces[map.length - 1][j] = true;
			}
		}
		// j-2
		if (j-2 > -1){
			map[k][j-2] = "B";
			usedSpaces[k][j-2] = true;
		}else{
			if (j-2 == -2){
				map[k][map.length - 2] = "B";
				usedSpaces[k][map.length - 2] = true;
			}else{
				map[k][map.length - 1] = "B";
				usedSpaces[k][map.length - 1] = true;
			}
		}
		
		// end static wumpus/blood generation
	}
	
	// sets a bottomless pit
	public void setPit(int k, int j){
		map[k][j] = "P";
		usedSpaces[k][j] = true;
		
		///// make the slime
		// k+1
		if (k+1 < map.length){
			if (map[k+1][j] == "B"){
				map[k+1][j] = "G";
			}else{
				map[k+1][j] = "S";
			}
			usedSpaces[k+1][j] = true;
		}else{
			if (map[0][j] == "B"){
				map[0][j] = "G";
			}else{
				map[0][j] = "S";
			}
			usedSpaces[0][j] = true;
		}
		
		// j+1
		if (j+1 < map.length){
			if (map[k][j+1] == "B"){
				map[k][j+1] = "G";
			}else{
				map[k][j+1] = "S";
			}
			usedSpaces[k][j+1] = true;
		}else{
			if (map[k][0] == "B"){
				map[k][0] = "G";
			}else{
				map[k][0] = "S";
			}
			usedSpaces[k][0] = true;
		}
		
		// k-1
		if (k-1 > -1){
			if (map[k-1][j] == "B"){
				map[k-1][j] = "G";
			}else{
				map[k-1][j] = "S";
			}
			usedSpaces[k-1][j] = true;
		}else{
			if (map[map.length-1][j] == "B"){
				map[map.length-1][j] = "G";
			}else{
				map[map.length-1][j] = "S";
			}
			usedSpaces[map.length-1][j] = true;
		}
		
		// j-1
		if (j-1 > -1){
			if (map[k][j-1] == "B"){
				map[k][j-1] = "G";
			}else{
				map[k][j-1] = "S";
			}
			usedSpaces[k][j-1] = true;
		}else{
			if (map[k][map.length-1] == "B"){
				map[k][map.length-1] = "G";
			}else{
				map[k][map.length-1] = "S";
			}
			usedSpaces[k][map.length-1] = true;
		}
		
	}
	
	// sets a hunter
	public void setHunter(int k, int j){
		mapFog[k][j] = "O";
	}
	
	public void setFog(){
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				if (mapFog[i][j] != "O"){
					mapFog[i][j] = "X";
				}
			}
		}
	}
	
	/***
	 * map getters
	 */
	
	// gets the Map Element at location
	public String getTileAt(int k, int j){
		return map[k][j];
	}
	
	
	
	// tests if map space is used by something
	public boolean getUsedSpace(int k, int j){
		return usedSpaces[k][j];
	}
	
	/***
	 * helper methods
	 */
	
	// helper method int randoms
	private int randInt(int min, int max) {
	    return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	// clean nulls, replace with empty Strings
	private void cleanNulls(){
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				if (map[i][j] == null){
					map[i][j] = " ";
					map[i][j] = " ";
				}
			}
		}
	}
	
	/***
	 * Testers
	
	// for testing purposes
	public void printGrid(){
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				System.out.print("[" + map[i][j] + "] ");
			}
			System.out.println("");
		}
	}
	
	// for testing purposes as well
	public static void main(String[] args){
		Grid grid = new Grid();
		grid.printGrid();
	}
	
	***/
	
	/***
	 * Movement methods
	 */
	
	// move hunter, return potential death message
	public String moveHunter(String direction){
		int tempI = 0;
		int tempJ = 0;
		String dir = null;
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				if (mapFog[i][j] == "O"){				
					// previous space becomes map. get resultant position
					if (direction == "up"){
						mapFog[i][j] = map[i][j];
						tempI = i - 1;
						tempJ = j;
						dir = "up";
					}
					if (direction == "right"){
						mapFog[i][j] = map[i][j];
						tempI = i;
						tempJ = j + 1;
						dir = "right";
					}
					if (direction == "down"){
						mapFog[i][j] = map[i][j];
						tempI = i + 1;
						tempJ = j;
						dir = "down";
					}
					if (direction == "left"){
						mapFog[i][j] = map[i][j];
						tempI = i;
						tempJ = j - 1;
						dir = "left";
					} 
				}
			}
		}
		
		
		// update out of loop, so H isn't discovered again
		
		//wrap around logic 
		if (tempJ == -1){
			tempJ = map.length-1;
		}
		if (tempJ == map.length){
			tempJ = 0;
		}
		if (tempI == -1){
			tempI = map.length-1;
		}
		if (tempI == map.length){
			tempI = 0;
		}
		
		// the mapFog (foreground) will have the hunter
		mapFog[tempI][tempJ] = "O";
		
		notifyObservers(dir);
		// check death
		String deathMessage = checkDeath(tempI, tempJ);
		return deathMessage;
	
	}
	
	// return a death message
	public String checkDeath(int i, int j){
		String deathMessage = "";
		if (map[i][j] == "P"){
			deathMessage = "You fell in a pit and died";
		}
		if (map[i][j] == "W"){
			deathMessage = "You got eaten by the Wumpus";
		}
		return deathMessage;
	}
	
	// fire arrow, return win message
	public String fireArrow(String direction){
		firedArrow = true;
		String winMessage = "";
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				if (mapFog[i][j] == "O"){
					if (direction == "up" || direction == "down"){
						for (int k=0; k<map.length; k++){
							if (map[k][j] == "W"){
								winMessage = "You have slayed the Wumpus!";
							}
						}
					}
					if (direction == "right" || direction == "left"){
						for (int k=0; k<map.length; k++){
							if (map[i][k] == "W"){
								winMessage = "You have slayed the Wumpus!";
							}
						}
					}
				}
			}
		}
		return winMessage;
	}
	
	// get a message for the player
	public String getHint(){
		String hint = "                                            ";
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				if (mapFog[i][j] == "O"){
					if (i+1 != map.length){
						if (map[i+1][j] == "S"){
							hint = "You smell something foul";
						}
						if (map[i+1][j] == "B"){
							hint = "You feel a chill going up your spine";
						}
						if (map[i+1][j] == "G"){
							hint = "You feel a chill going up your spine as you smell somthing foul";
						}
					}
					if (j+1 != map.length){
						if (map[i][j+1] == "S"){
							hint = "You smell something foul";
						}
						if (map[i][j+1] == "B"){
							hint = "You feel a chill going up your spine";
						}
						if (map[i][j+1] == "G"){
							hint = "You feel a chill going up your spine as you smell somthing foul";
						}
					}
					if (i-1 != -1){
						if (map[i-1][j] == "S"){
							hint = "You smell something foul";
						}
						if (map[i-1][j] == "B"){
							hint = "You feel a chill going up your spine";
						}
						if (map[i-1][j] == "G"){
							hint = "You feel a chill going up your spine as you smell somthing foul";
						}
					}
					if (j-1 != -1){
						if (map[i][j-1] == "S"){
							hint = "You smell something foul";
						}
						if (map[i][j-1] == "B"){
							hint = "You feel a chill going up your spine";
						}
						if (map[i][j-1] == "G"){
							hint = "You feel a chill going up your spine as you smell somthing foul";
						}
					}
				}
			}
		}
		return hint;
	}
	
	/***
	 * For the View
	 */
	
	// turns Grid into a string!
	public String gridToString(){
		String eol = System.getProperty("line.separator");
		String theString = "";
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				theString += "[" + map[i][j] + "] ";
			}
			theString += eol;
		}
		return theString;
	}
	
	// returns the fog view
	public String fogToString(){
		String eol = System.getProperty("line.separator");
		String theString = "";
		for (int i=0; i<map.length; i++){
			for (int j=0; j<map.length; j++){
				theString += "[" + mapFog[i][j] + "] ";
			}
			theString += eol;
		}
		return theString;
	}
	
	public int getMapLength(){
		return map.length;
	}
	
	public String[][] getMapFog(){
		return mapFog;
	}
	
	public boolean getFiredArrow(){
		return firedArrow;
	}

}
