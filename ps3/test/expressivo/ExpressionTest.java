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
    // Partition:
    /**     Numeric:
     *          int vs int, double vs double, int vs double
     *          with same or different value
     *          small values: 1,2 | Larget value: Max int +1
     *  
     *      Variable:
     *          same or different String
     * 
     *      Operation(Expression):
     *          same or different opt char: '+', '*'
     *          same or different Numeric
     *          same or differnet Variable
     *          swap left and right of same Variable or Numerice: Operation(opt:'+', left:1, right:2) vs Operation(opt:'+', left:2, right:1) 
     * 
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testNumericEqual(){
        Numeric num1 = new Numeric(1);
        Numeric num2 = new Numeric(1);

        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertTrue(num1.equals(num2));
    }

    @Test
    public void testNumericNotEqual(){
        Numeric num1 = new Numeric(1);
        Numeric num2 = new Numeric(2);
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertFalse(num1.equals(num2));
    }

    
    @Test
    public void testMaxNumericEqual(){
        Numeric num1 = new Numeric(Integer.MAX_VALUE+1);
        Numeric num2 = new Numeric(Integer.MAX_VALUE+1);
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertTrue(num1.equals(num2));
    }

    @Test
    public void testMaxNumericNotEqual(){
        Numeric num1 = new Numeric(Integer.MAX_VALUE+1);
        Numeric num2 = new Numeric(Integer.MAX_VALUE+2);
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertFalse(num1.equals(num2));
    }

    @Test
    public void testDoubleNumericEqual(){
        Numeric num1 = new Numeric(1);
        Numeric num2 = new Numeric(1.000);
        Numeric num3 = new Numeric(1.000);
        Numeric num4 = new Numeric(1.000000000000000000000001);
        Numeric num5 = new Numeric(1.000000000000000000000001);
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertTrue(num1.equals(num2));
        assertTrue(num2.equals(num3));
        assertTrue(num4.equals(num5));
    }

    @Test
    public void testDoubleNumericNotEqual(){
        Numeric num1 = new Numeric(1);
        Numeric num2 = new Numeric(2);
        Numeric num3 = new Numeric(1.000);
        Numeric num4 = new Numeric(1.000000000000001);
        Numeric num5 = new Numeric(1.000000000000000000001);
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertFalse(num1.equals(num2));
        assertFalse(num1.equals(num4));
        // long floating points needs to same value
        assertTrue(num1.equals(num5));

        assertFalse(num3.equals(num2));
        assertFalse(num3.equals(num4));
        // long floating points needs to same value
        assertTrue(num3.equals(num5));

        //assertFalse(num1.hashCode()==(num5.hashCode()));
        // assertTrue(num4.equals(num5));
    }

    @Test
    public void testNumericHashCodeEqual(){
        Numeric num1 = new Numeric(1);
        Numeric num2 = new Numeric(1);
        Numeric num3 = new Numeric(1.000);
        Numeric num4 = new Numeric(1.0000);
        Numeric num5 = new Numeric(1.000000000000000000001);

        assertEquals(num1.hashCode(),num2.hashCode());
        assertEquals(num3.hashCode(),num4.hashCode());
        // long floating points needs to same value
        assertEquals(num3.hashCode(),num5.hashCode());
    }

    @Test
    public void testNumericHashCodeNotEqual(){
        Numeric num1 = new Numeric(1);
        Numeric num3 = new Numeric(1.000);
        Numeric num4 = new Numeric(1.000000000000001);
        Numeric num5 = new Numeric(1.000000000000000000001);

        assertNotEquals(num1.hashCode(),num3.hashCode());
        assertNotEquals(num1.hashCode(),num4.hashCode());
        assertNotEquals(num1.hashCode(),num5.hashCode());
        assertNotEquals(num3.hashCode(),num4.hashCode());
        assertNotEquals(num4.hashCode(),num5.hashCode());
    }

    @Test
    public void testVariableEqual(){
        Variable var1 = new Variable("x");
       // Variable var2 = new Variable("x");
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertTrue(var1.equals(var1));
    }

    @Test
    public void testVariableNotEqual(){
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("x");
        Variable var3 = new Variable("y");
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertFalse(var1.equals(var2));
        assertFalse(var1.equals(var3));
    }

    @Test
    public void testVariableHashCodeEqual(){
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("x");
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertEquals(var1.hashCode(),var2.hashCode());
    }

    @Test
    public void testVariableHashCodeNotEqual(){
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("y");
        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertNotEquals(var1.hashCode(),var2.hashCode());
    }

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
        Numeric two = new Numeric(2);

        Variable x = new Variable("x");
        //Variable x2 = new Variable("x");
        Operation optx = new Operation('+', x,two);
        Operation optx2 = new Operation('+', x,two);

        Operation opt = new Operation('+', one,two);
        Operation opt2 = new Operation('+', one,two);
        Expression opt3 = new Operation('+', new Numeric(1),new Numeric(2));

        Expression expression = new Operation('*', one,opt);
        Expression expression2 = new Operation('*', one,opt);

        assertTrue(optx.equals(optx2));
        assertTrue(opt.equals(opt2));
        assertTrue(opt.equals(opt3));
        assertTrue(expression.equals(expression2));
    }

    @Test
    public void testExpressionNotEqual(){
        Numeric one = new Numeric(1);
        Numeric two = new Numeric(2);
        Numeric three = new Numeric(3);
        Variable x = new Variable("x");
        Variable x2 = new Variable("x");
        Variable y = new Variable("y");

        Operation optx = new Operation('+', x,two);
        Operation optx2 = new Operation('+', x2,one);
        Operation opty = new Operation('+', y,one);


        Operation opt = new Operation('+', one,two);
        Operation opt2 = new Operation('+', two,one);
        Operation opt3 = new Operation('+', one,three);
        Operation opt4 = new Operation('*', one,three);
        //Expression opt3 = new Operation('+', new Numeric(1),new Numeric(2));

        Expression expression = new Operation('*', one,opt);
        Expression expression2 = new Operation('*', opt,one);

        assertFalse(optx.equals(opty));
        assertFalse(optx.equals(optx2));
        assertFalse(opt.equals(opt2));
        assertFalse(opt.equals(opt3));
        assertFalse(opt.equals(opt4));
        assertFalse(expression.equals(expression2));
    }

    @Test
    public void testExpressionHashCode(){
        Numeric one = new Numeric(1);
        Numeric two = new Numeric(2);
        Numeric three = new Numeric(3);

        Variable x = new Variable("x");
        Variable x2 = new Variable("x");
        Variable y = new Variable("y");

        Operation optx = new Operation('+', x,one);
        Operation optx2 = new Operation('+', x2,one);
        Operation opty = new Operation('+', y,one);

        Operation opt = new Operation('+', one,two);
        Operation opt2 = new Operation('+', two,one);
        Operation opt3 = new Operation('+', one,three);
        Operation opt4 = new Operation('*', one,three);
        //Expression opt3 = new Operation('+', new Numeric(1),new Numeric(2));

        Expression expression = new Operation('*', one,opt);
        Expression expression2 = new Operation('*', opt,one);

        assertEquals(optx.hashCode(),optx2.hashCode());
        assertEquals(opt.hashCode(),opt2.hashCode());
        assertEquals(expression.hashCode(),expression2.hashCode());
        assertNotEquals(opty.hashCode(),optx.hashCode());
        assertNotEquals(opt3.hashCode(),opt4.hashCode());
        assertNotEquals(opt.hashCode(),opt4.hashCode());

    }
    
}
