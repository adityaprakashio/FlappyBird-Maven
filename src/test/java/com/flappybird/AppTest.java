package com.flappybird;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testGameSettings() {
        // This simulates a "DevOps sanity check"
        // We are checking if the game window is set to the correct size
        FlappyBird game = new FlappyBird();
        
        // Check if width is 360
        assertEquals("Board width should be 360", 360, game.boardWidth);
        
        // Check if height is 640
        assertEquals("Board height should be 640", 640, game.boardHeight);
        
        System.out.println("✅ UNIT TEST PASSED: Game dimensions are correct.");
    }
}