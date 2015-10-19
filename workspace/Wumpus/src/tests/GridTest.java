package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Grid;

public class GridTest {

	// test Wumpus and Blood code
	@Test
	public void testGridWumpusBloodAndGoop() {
		
		// test no wrap, fall in pit, test toString methods
		Grid grid = new Grid();
		grid.setWumpus(5, 5);
		assertEquals(grid.getTileAt(5, 5), "W");
		assertEquals(grid.getTileAt(5+1, 5), "B");
		assertEquals(grid.getTileAt(5+2, 5), "B");
		assertEquals(grid.getTileAt(5, 5+1), "B");
		assertEquals(grid.getTileAt(5, 5+2), "B");
		assertEquals(grid.getTileAt(5-1, 5), "B");
		assertEquals(grid.getTileAt(5-2, 5), "B");
		assertEquals(grid.getTileAt(5, 5-1), "B");
		assertEquals(grid.getTileAt(5, 5-2), "B");
		assertEquals(grid.getTileAt(5+1, 5+1), "B");
		assertEquals(grid.getTileAt(5+1, 5-1), "B");
		assertEquals(grid.getTileAt(5-1, 5+1), "B");
		assertEquals(grid.getTileAt(5-1, 5+1), "B");
		grid.setPit(5, 8);
		assertEquals(grid.getTileAt(5,7), "G");
		grid.setHunter(5,9);
		grid.moveHunter("left");
		assertEquals(grid.checkDeath(5, 8), "You fell in a pit and died");
		String mapAsString = grid.gridToString();
		String mapAsFog = grid.fogToString();
		
		// test wrap at NW corner, move all directions, and get eaten
		grid = new Grid();
		grid.setWumpus(0, 0);
		assertEquals(grid.getTileAt(0, 0), "W");
		assertEquals(grid.getTileAt(9, 0), "B");
		assertEquals(grid.getTileAt(0, 1), "B");
		assertEquals(grid.getTileAt(0, 9), "B");
		assertEquals(grid.getTileAt(1, 0), "B");
		assertEquals(grid.getTileAt(8, 0), "B");
		assertEquals(grid.getTileAt(0, 8), "B");
		assertEquals(grid.getTileAt(0, 2), "B");
		assertEquals(grid.getTileAt(2, 0), "B");
		assertEquals(grid.getTileAt(1, 1), "B");
		assertEquals(grid.getTileAt(9, 1), "B");
		assertEquals(grid.getTileAt(1, 9), "B");
		assertEquals(grid.getTileAt(9, 9), "B");
		grid.setPit(9,1);
		assertEquals(grid.getTileAt(9,0), "G");
		grid.setHunter(3,0);
		grid.moveHunter("up");
		assertEquals(grid.getHint(), "You feel a chill going up your spine");
		grid.moveHunter("down");
		grid.moveHunter("left");
		grid.moveHunter("right");
		grid.moveHunter("up");
		assertEquals(grid.checkDeath(1, 0), "");
		grid.moveHunter("up");
		assertEquals(grid.checkDeath(0, 0), "You got eaten by the Wumpus");
		grid.setFog();

		// test wrap at (1,1), get all sorts of hints, shoot arrows everywhere
		grid = new Grid();
		grid.setWumpus(1, 1);
		assertEquals(grid.getTileAt(1, 1), "W");
		assertEquals(grid.getTileAt(2, 1), "B");
		assertEquals(grid.getTileAt(1, 2), "B");
		assertEquals(grid.getTileAt(0, 1), "B");
		assertEquals(grid.getTileAt(1, 0), "B");
		assertEquals(grid.getTileAt(2, 2), "B");
		assertEquals(grid.getTileAt(0, 0), "B");
		assertEquals(grid.getTileAt(2, 0), "B");
		assertEquals(grid.getTileAt(0, 2), "B");
		assertEquals(grid.getTileAt(3, 1), "B");
		assertEquals(grid.getTileAt(1, 3), "B");
		assertEquals(grid.getTileAt(9, 1), "B");
		assertEquals(grid.getTileAt(1, 9), "B");
		grid.setPit(5,5);
		grid.setPit(2,3);
		grid.setPit(9,0);
		assertEquals(grid.getTileAt(2, 2), "G");
		assertEquals(grid.getTileAt(1, 3), "G");
	}
}