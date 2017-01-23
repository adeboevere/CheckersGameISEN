package isen.checkers.test;

import static org.junit.Assert.*;

import org.junit.Test;

import isen.checkers.Application;
import isen.checkers.Player;

import static org.assertj.core.api.Assertions.*;

public class AppTest {

	//Application app = new Application();
	
	@Test
	public void testPlay() {
		Player player = new Player(true);
        Player otherPlayer = new Player(false);
		
		assertThat(player.isWhite).isTrue();
		assertThat(otherPlayer.isWhite).isFalse();
	}

	@Test
	public void testFill() {
		int[][] array = new int[10][10];

        array = Application.fill(array, 0);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                assertThat(array[i][j]).isEqualTo(0);
            }
        }
	}

	@Test
	public void testMouseListen() {
		
	}

	@Test
	public void testInvokeGameOver() {
	}

	@Test
	public void testDisplayImage() {
	}

	@Test
	public void testMove() {
	}

	@Test
	public void testDecrementPieces() {
	}

	@Test
	public void testInvertMove() {
	}

	@Test
	public void testCheckCapture() {
	}

	@Test
	public void testCheckQueenCapture() {
	}

	@Test
	public void testCheckCaptureIntInt() {
	}

	@Test
	public void testSetup() {
		
	}

	@Test
	public void testSetBlack() {
	}

	@Test
	public void testSetWhite() {
		
	}

	@Test
	public void testPaintComponentGraphics() {
		
	}

	@Test
	public void testSameColor() {
		
	}

	@Test
	public void testOppositeColor() {
	
	}

}
