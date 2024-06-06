package expressivo;

import java.math.BigDecimal;
import java.util.Map;

public class Numeric implements Expression {
    public final Number num;
    
    public Numeric(Number num){
        this.num = num;
    }

    public BigDecimal getNum(){
        BigDecimal num1 = BigDecimal.valueOf(num.doubleValue());
        return num1;
    }

    @Override
    public boolean needBracket(char opt2){
        return false;
    }

    @Override
    public boolean equals(Object thatObject){
        if (thatObject instanceof Numeric) {
            BigDecimal num1 = BigDecimal.valueOf(num.doubleValue());
            Numeric num2 =  (Numeric)thatObject;
            
            //BigDecimal num3 = BigDecimal.valueOf(num2.num.doubleValue()); 

            return num1.compareTo(BigDecimal.valueOf(num2.num.doubleValue()))==0;
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash;
        hash = num.hashCode();
        return hash;
    }

    @Override
    public String toString(){
        BigDecimal num1 = BigDecimal.valueOf(num.doubleValue());
        return num1.stripTrailingZeros().toPlainString();
        //return String.valueOf(num);
    }

    @Override
    public Expression differentiate(String variable){
        return new Numeric(0);
    }

    @Override
    public Expression simplify(Map<String,Double> environment){
        return this;
    }


}

