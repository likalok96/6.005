package minesweeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import minesweeper.Square.SquareState;

public class SquareTest {
    /**
     * Square
     *  isBoom:
     *      True or False
     *  State:
     *      untouched, flagged and dug
     * 
     * 
     */

    @Test
    public void testState(){
        Square square = new Square(false);
        Square squareBoom = new Square(true);


        assertEquals(square.getState(), SquareState.untouched);
        assertEquals(squareBoom.getState(), SquareState.untouched);

        square.setState(SquareState.flagged);
        squareBoom.setState(SquareState.flagged);

        assertEquals(square.getState(), SquareState.flagged);
        assertEquals(squareBoom.getState(), SquareState.flagged);


        square.setState(SquareState.dug);
        squareBoom.setState(SquareState.dug);

        assertEquals(square.getState(), SquareState.dug);
        assertEquals(squareBoom.getState(), SquareState.dug);

        assertFalse(square.getBoom());
        assertTrue(squareBoom.getBoom());
    }

    @Test
    public void testString(){
        Square square = new Square(false);

        assertEquals(square.toString(), "-");

        square.setState(SquareState.flagged);

        assertEquals(square.toString(), "F");


        square.setState(SquareState.dug);

        assertEquals(square.toString(), " ");
    }
}
