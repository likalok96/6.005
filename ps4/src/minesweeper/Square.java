package minesweeper;
/**
 * Mutable data type represent a square in minesweeper board.
 * Conatains State of untouched, dug, flagged.
 * It conatains either a bomb or not.
 */
public class Square {

    public enum SquareState{
        untouched, flagged, dug
    }

    private boolean isBoom;
    private SquareState state;
    //private int boomCount;

    /**
     * Abstract Function: 
     *      AF(isBoom, state) = a square in a board contains a bomm if isBoom is true and a state of SquareState.
     * Rep variant:
     *      
     * Rep exposure:
     *      isBoom and state are private and immutable.
     * 
     */

    /**
     * Make a square with initial state untouched.
     * 
     * @param isBoom true if square contains a boom.
     */
    public Square(boolean isBoom){
        this.isBoom = isBoom;
        this.state = SquareState.untouched;
        //this.boomCount = 0;
        //checkRep();
    }

/*     private void checkRep(){
        if(state.equals(SquareState.dug)){
            assert !isBoom;
        }
    } */

    /**
     * Check whether square contains a boom
     * 
     * @return true if square contains a boom
     */

    public boolean getBoom(){
        return isBoom;
    }

    /*
     * Set square to contains a boom or not
     * 
     */
    public void setBoom(boolean boom){
        isBoom = boom;
       // checkRep();
    }
    /**
     * Check square state
     * @return
     */

    public SquareState getState(){
        return state;
    }

/*     public int getBoomCount(){
        return boomCount;
    }

    public void setBoomCount(int count){
        boomCount = count;
    } */

    /**
     * Set square state
     * @param state state should be changed from untouched to other state.
     */
    public void setState(SquareState state){
        this.state = state;
        //checkRep();
    }

    @Override
    public String toString(){
        switch (state) {
            case untouched:
                return "-";
            case flagged:
                return "F";
            case dug:
            return " ";
                //return boomCount == 0 ? " " : Integer.toString(boomCount);
            default:
                throw new RuntimeException("unexpected input");
        }
    }
    
}
