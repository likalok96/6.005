/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * TODO: Description
 */
public class BoardTest {
    
    // TODO: Testing strategy
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO: Tests

    @Test
    public void testFile() throws IOException{
        File file = new File("./test/minesweeper/autograder/boards/board_file_5");
        Board board = new Board(file);

        String str = "- - - - - - -\n" +
                     "- - - - b - -\n" + 
                     "- - - - - - -\n" + 
                     "- - - - - - -\n" + 
                     "- - - - - - -\n" + 
                     "- - - - - - -\n" + 
                     "b - - - - - -\n" ;
        
        board.dig(0, 0);
        //assertEquals(board.getRows(), str);

        assertEquals(board.toString(), str);
    }
    
}
