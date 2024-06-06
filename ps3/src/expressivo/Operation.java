package expressivo;

import java.util.Map;

public class Operation implements Expression {
    
    public final char opt;
    public final Expression left,right;

    public Operation(char opt, Expression left, Expression right){
        this.opt = opt;
        this.left = left;
        this.right = right;
    }
    

    @Override
    public boolean needBracket(char opt2){
        //if(operation instanceof Operation){
            //Operation opt2 = (Operation)operation;
            return opt2=='*' && opt=='+';
        //}
        //return false;
    }

    @Override
    public boolean equals(Object thatObject){
        if (thatObject instanceof Operation) {
            Operation that = (Operation)thatObject;
            return this.opt == that.opt && this.left.equals(that.left) && this.right.equals(that.right);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash;
        hash = Character.hashCode(opt) * (left.hashCode() + right.hashCode());
        return hash;
    }

    @Override
    public String toString(){
        String leftStr = left.needBracket(opt) ? "(" + left.toString() + ")" : left.toString();
        String rightStr = right.needBracket(opt) ? "(" + right.toString() + ")" : right.toString();

        //if(opt=='+') return "(" + leftStr + opt + rightStr + ")";
        return leftStr + opt + rightStr;
    }

    @Override
    public Expression differentiate(String variable){
        Expression diffLeft = left.differentiate(variable);
        Expression diffRight = right.differentiate(variable);

        return opt=='+' ? new Operation(opt, diffLeft, diffRight) :
                          new Operation('+', new Operation('*', left, diffRight), new Operation('*', right, diffLeft));
    }

    @Override
    public Expression simplify(Map<String,Double> environment){
        Expression simpLeft = left.simplify(environment);
        Expression simpRight = right.simplify(environment);
        if(simpLeft instanceof Numeric && simpRight instanceof Numeric){
            Numeric numLeft = (Numeric)simpLeft;
            Numeric numRight = (Numeric)simpRight;
            //Number x = numLeft.num + numLeft.num;
            return opt=='+' ? new Numeric(numLeft.getNum().add(numRight.getNum())) : new Numeric(numLeft.getNum().multiply(numRight.getNum()));
        }

        return new Operation(opt, simpLeft, simpRight);
    }


}
