package expressivo;

public class Operation implements Expression {
    
    public final char opt;
    public final Expression left,right;

    public Operation(char opt, Expression left, Expression right){
        this.opt = opt;
        this.left = left;
        this.right = right;
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
        hash = Character.hashCode(opt) + left.hashCode() + right.hashCode();
        return hash;
    }

    @Override
    public String toString(){
        String leftStr = left.toString();
        String rightStr = right.toString();
        if(opt=='+') return "(" + leftStr + opt + rightStr + ")";
        return leftStr + opt + rightStr;
    }


}
