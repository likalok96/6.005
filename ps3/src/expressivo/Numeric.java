package expressivo;

import java.math.BigDecimal;

public class Numeric implements Expression {
    public final Number num;
    
    public Numeric(Number num){
        this.num = num;
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
        return String.valueOf(num);
    }

}

