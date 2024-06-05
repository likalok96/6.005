package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParseTest {

    // Testing strategy
    // Partition:
    /**     Numeric: 
     *          begin with 0
     *          end with decimal point 0
     *          integer
     *          begin with .
     *      Variable:
     *          with trailing space or not
     *      Expression:
     *          num: double or int
     *          var: variable
     *          opt: operation with '+','*'
     *          bracketExp: (var opt num)/(num opt var)
     *          complex: bracketExp opt bracketExp 
     */


    @Test
    public void testNumericEqual(){
        String num0 = "01";
        String num1 = "1";
        String num2 = "1.1";
        String num3 = "1.000000000000000000000001";
        String num4 = "0.1";
        String num5 = ".1";
        String num6 = "1.10";

        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertEquals(Expression.parse(num0).toString(),num1);
        assertEquals(Expression.parse(num1).toString(),num1);
        assertEquals(Expression.parse(num2).toString(),num2);
        assertEquals(Expression.parse(num3).toString(),num3);
        assertEquals(Expression.parse(num5).toString(),num4);
        assertEquals(Expression.parse(num6).toString(),num2);

    }

    @Test
    public void testVariableEqual(){
        String var0 = "x";
        String var1 = " x ";
        //String var3 = "x";

        //assertTrue(expression.hashCode() == expression2.hashCode());
        assertEquals(Expression.parse(var0).toString(),var0);
        assertEquals(Expression.parse(var1).toString(),var0);

       //assertEquals(Expression.parse(var3).toString(),"123");
/*         assertEquals(Expression.parse(num2).toString(),num2);
        assertEquals(Expression.parse(num3).toString(),num3);
        assertEquals(Expression.parse(num5).toString(),num4);
        assertEquals(Expression.parse(num6).toString(),num2); */

    }

    @Test
    public void testExpressionEqual(){
        String var0 = "1+1";
        String var1 = "(1+1)";
        String var2 = "1*2";
        String var3 = "(1*2)";
        String var31 = "(1)*(2)";
        String var4 = "1+2*3";
        String var5 = "(1+2)*3";
        String var6 = "(1*2)+3";
        String var7 = "1*2+3";
        String var8 = "(1+2)*(3+4)";
        String var9 = "1+x+y+2";
        String var10 = "(0.1+x)*1*(y+2)";
        String var11 = "(0.1*x)+1+(y+2)";
        String var12 = "0.1*x+1+y+2";

        assertEquals(Expression.parse(var0).toString(),var0);
        assertEquals(Expression.parse(var1).toString(),var0);
        assertEquals(Expression.parse(var2).toString(),var2);
        assertEquals(Expression.parse(var3).toString(),var2);
        assertEquals(Expression.parse(var31).toString(),var2);
        assertEquals(Expression.parse(var4).toString(),var4);
        assertEquals(Expression.parse(var5).toString(),var5);
        assertEquals(Expression.parse(var6).toString(),var7);
        assertEquals(Expression.parse(var8).toString(),var8);
        assertEquals(Expression.parse(var9).toString(),var9);
        assertEquals(Expression.parse(var10).toString(),var10);
        assertEquals(Expression.parse(var11).toString(),var12);


    }
    
}
