/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Test for Board
 */
public class BoardTest {
    
    // Board
    /** Consturctor:
     *      initiate with random boom chance or file
     *  flag, unflag a square:
     *      
     *  dig:
     *      near 0,1,>1 booms square
     *      square contains a boom
     *  toString():
     *      board with dug or flag or untouch squares
     * 
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testRandomBoard(){
        Board board = new Board(3, 3);
        String str = "- - -\n" +
                     "- - -\n" +
                     "- - -\n" ;

        assertEquals(board.toString(), str);
    }

    @Test
    public void testFlag() throws IOException{
        File file = new File("./test/minesweeper/autograder/boards/board_file_5");
        Board board = new Board(file);

        String str1 = "F - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" ;

        String str2 = "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" ;

        board.flag(0, 0);
        assertEquals(board.toString(), str1);

        board.unflag(0, 0);
        assertEquals(board.toString(), str2);

    }


    @Test
    public void testDigBoom() throws IOException{
        File file = new File("./test/minesweeper/autograder/boards/board_file_5");
        Board board = new Board(file);

        String str =  "- - - 1 1 1 -\n" +
                      "- - - 1   1 -\n" +
                      "- - - 1 1 1 -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" ;

        board.dig(4, 1);
        //assertEquals(board.getRows(), str);

        assertEquals(board.toString(), str);
    }

    @Test
    public void testDigNoNearBoom() throws IOException{
        File file = new File("./test/minesweeper/autograder/boards/board_file_5");
        Board board = new Board(file);

        String str =  "      1 - 1  \n" +
                      "      1 - 1  \n" +
                      "      1 1 1  \n" +
                      "             \n" +
                      "             \n" +
                      "1 1          \n" +
                      "- 1          \n" ;

        board.dig(0, 1);
        //assertEquals(board.getRows(), str);

        assertEquals(board.toString(), str);
    }

    @Test
    public void testDigNearBoom() throws IOException{
        File file = new File("./test/minesweeper/autograder/boards/board_file_5");
        Board board = new Board(file);

        String str =  "- - - 1 - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" +
                      "- - - - - - -\n" ;

        board.dig(3, 0);
        //assertEquals(board.getRows(), str);

        assertEquals(board.toString(), str);
    }

    @Test
    public void testDigNearMultiBoom() throws IOException{
        File file = new File("./test/minesweeper/autograder/boards/board_file_1");
        Board board = new Board(file);

        String str =  "- - - - 3\n" +
                      "- - - - -\n" +
                      "- - - - -\n" +
                      "- - - - -\n" +
                      "- - - - -\n" ;

        board.dig(4, 0);
        //assertEquals(board.getRows(), str);

        assertEquals(board.toString(), str);
    }
    
}
