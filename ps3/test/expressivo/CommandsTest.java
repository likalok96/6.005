/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    // differentiate(expression, variable):
    /**     expression: Numeric, Variable , Operation
     *      Variable: [a-zA-Z]+ valid or not 
     *      Numeric: int or float
     *      Operation: 
     *          opt: '+','*'
     *          left: Numeric, Variable , Operation
     *          right: Numeric, Variable , Operation
     * 
     *      variable:
     *          expression contains variable or not
     *          expression contains other variable or not
     * 
     * simplify(expression, environment):
     *      expression: Numeric, Variable , Operation
     *      Variable: [a-zA-Z]+ valid or not 
     *      Numeric: int or float
     *      Operation: 
     *          opt: '+','*'
     *          left: Numeric, Variable , Operation
     *          right: Numeric, Variable , Operation
     * 
     *      environment:
     *          contains all variable or not
     *          contains other extra variable or not
     *          
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Commands.differentiate() and Commands.simplify()

    @Test
    public void testDiffValue() {
        Numeric num = new Numeric(1);
        Variable var = new Variable("x");
        Variable var2 = new Variable("y");
        assertEquals(Commands.differentiate(num.toString(),"x"), "0");
        assertEquals(Commands.differentiate(var.toString(),"x"), "1");
        assertEquals(Commands.differentiate(var2.toString(),"x"), "0");
    }

    @Test
    public void testDiffOperation() {
        Numeric num = new Numeric(1);
        Numeric num2 = new Numeric(2);
        //Numeric num3 = new Numeric(1.1);

        Variable var = new Variable("x");
        Variable var2 = new Variable("y");

        Operation opt = new Operation('+', var, var2);
        Operation opt2 = new Operation('+', num, var2);
        Operation opt3 = new Operation('*', var, var2);
        Operation opt4 = new Operation('*', num, var2);
        Operation opt5 = new Operation('*', var, var);
        //Operation opt6 = new Operation('*', var2, var2);

        Operation opt7 = new Operation('+', num, num2);
        Operation opt8 = new Operation('*', num, num2);

        Operation opt9 = new Operation('*', opt, opt3);

        assertEquals(Commands.differentiate(opt.toString(),"x"), "1+0");
        assertEquals(Commands.differentiate(opt2.toString(),"x"), "0+0");

        assertEquals(Commands.differentiate(opt3.toString(),"x"), "x*0+y*1");
        assertEquals(Commands.differentiate(opt3.toString(),"y"), "x*1+y*0");
        assertEquals(Commands.differentiate(opt3.toString(),"z"), "x*0+y*0");
        assertEquals(Commands.differentiate(opt3.toString(),"x"), "x*0+y*1");
        assertEquals(Commands.differentiate(opt3.toString(),"x"), "x*0+y*1");

        assertEquals(Commands.differentiate(opt4.toString(),"x"), "1*0+y*0");
        assertEquals(Commands.differentiate(opt5.toString(),"x"), "x*1+x*1");
        assertEquals(Commands.differentiate(opt7.toString(),"x"), "0+0");
        assertEquals(Commands.differentiate(opt8.toString(),"x"), "1*0+2*0");

        assertEquals(Commands.differentiate(opt9.toString(),"x"), "(x+y)*(x*0+y*1)+x*y*(1+0)");

    }

    @Test
    public void testSimpValue() {
        Map<String, Double> map = new HashMap<>();
        map.put("x", 2.);
        Numeric num = new Numeric(1);
        Variable var = new Variable("x");
        Variable var2 = new Variable("y");
        
        assertEquals(Commands.simplify(num.toString(),map), "1");
        assertEquals(Commands.simplify(var.toString(),map), "2");
        assertEquals(Commands.simplify(var2.toString(),map), "y");

    }

    @Test
    public void testSimpOperation() {
        Map<String, Double> map = new HashMap<>();
        map.put("x", 2.);
        Numeric num = new Numeric(1);
        Numeric num2 = new Numeric(2);
        //Numeric num3 = new Numeric(1.1);

        Variable var = new Variable("x");
        Variable var2 = new Variable("y");

        Operation opt = new Operation('+', var, var2);
        Operation opt2 = new Operation('+', num, var2);
        Operation opt3 = new Operation('*', var, var2);
        //Operation opt4 = new Operation('*', num, var2);
        Operation opt5 = new Operation('*', var, var);
        //Operation opt6 = new Operation('*', var2, var2);

        Operation opt7 = new Operation('+', num, num2);
        //Operation opt8 = new Operation('*', num, num2);
        Operation opt9 = new Operation('*', opt, opt3);

        
        assertEquals(Commands.simplify(opt.toString(),map), "2+y");
        assertEquals(Commands.simplify(opt2.toString(),map), "1+y");
        assertEquals(Commands.simplify(opt3.toString(),map), "2*y");
        map.put("y", 5.);
        assertEquals(Commands.simplify(opt.toString(),map), "7");
        assertEquals(Commands.simplify(opt5.toString(),map), "4");
        assertEquals(Commands.simplify(opt7.toString(),map), "3");

        assertEquals(Commands.simplify(opt9.toString(),map), "70");

    }
    
}
