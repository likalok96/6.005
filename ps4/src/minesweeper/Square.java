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
    private int boomCount;

    public Square(boolean isBoom){
        this.isBoom = isBoom;
        this.state = SquareState.untouched;
        this.boomCount = 0;
    }

    public boolean getBoom(){
        return isBoom;
    }

    public SquareState getState(){
        return state;
    }

    public int getBoomCount(){
        return boomCount;
    }

    public void setBoomCount(int count){
        boomCount = count;
    }

    public void setState(SquareState state){
        this.state = state;
    }

    @Override
    public String toString(){
        switch (state) {
            case untouched:
                return "-";
            case flagged:
                return "F";
            case dug:
                return boomCount == 0 ? " " : Integer.toString(boomCount);
            default:
                throw new RuntimeException("unexpected input");
        }
    }
    
}
