/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Expression

    @Test
    public void testExpressionString(){
        Numeric one = new Numeric(1);
        Numeric two = new Numeric(2);
        Operation opt = new Operation('+', one,two);

        Expression expression = new Operation('*', one,opt);

        assertEquals("1*(1+2)",expression.toString());
    }

    @Test
    public void testExpressionEqual(){
        Numeric one = new Numeric(1);
        Numeric two = new Numeric(1);
        Operation opt = new Operation('+', one,two);
        Operation opt2 = new Operation('+', one,two);

        Expression expression = new Operation('*', one,opt);

        Expression expression2 = new Operation('+', new Numeric(1),new Numeric(2));

        assertTrue(one.hashCode() == two.hashCode());
    }
    
}
