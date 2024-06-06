package expressivo;

import java.math.BigDecimal;
import java.util.Map;

public class Variable implements Expression {
    private final String var;

    public Variable(String var){
        this.var = var;
    }

    @Override
    public boolean needBracket(char opt2){
        return false;
    }

    @Override
    public boolean equals(Object thatObject){
        if (thatObject instanceof Variable) {
            return super.equals(thatObject);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return var.hashCode();
    }

    @Override
    public String toString(){
        return var;
    }

    @Override
    public Expression differentiate(String variable){
        return var.equals(variable) ? new Numeric(1) : new Numeric(0);
    }

    @Override
    public Expression simplify(Map<String,Double> environment){
        return environment.containsKey(var)? new Numeric(BigDecimal.valueOf(environment.get(var)).stripTrailingZeros()) : this;
    }

}
