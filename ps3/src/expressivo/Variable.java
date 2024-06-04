package expressivo;

import java.math.BigDecimal;

public class Variable implements Expression {
    private final String var;

    public Variable(String var){
        this.var = var;
    }

    @Override
    public boolean equals(Object thatObject){
        if (thatObject instanceof Variable) {
            return super.equals(thatObject);
        }
        return false;
    }

    @Override
    public String toString(){
        return var;
    }

}
