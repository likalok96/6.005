package expressivo;

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

}
