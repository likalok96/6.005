/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import minesweeper.Square.SquareState;

/**
 * Board represent a minesweeper board formed by Squares.
 */
public class Board {
    
    /**
     * Abstract Function: 
     *      AF(board, rows, columns) = a minesweeper board with rows * columns size filled with Square.
     * Rep variant:
     *      rows & columns >0
     *      
     * Rep exposure:
     *      rows & columns are private final
     *      board is only return from toString as String
     *      boomChance is never return
     * 
     * Thread Safty:
     *      method are guarded with object lock synchronized
     *      only get row and columns are not as it will not change
     * 
     */
    
    //private final Object boardLock = new Object();

    private final Square[][] board;
    private final int rows;
    private final int columns;
    private final Double boomChance = 0.25;

    /**
     * Make a Board with random boom position
     * 
     * @param col columns of board
     * @param row rows of board
     */
    public Board(int col, int row) {
        columns = col;
        rows = row;
        board = new Square[row][col];
        for(int rows = 0; rows<row; rows++){
            for(int cols =0; cols<col; cols++){
                board[rows][cols] = Math.random()>boomChance ? new Square(false) : new Square(true);
            }
        }
    }


    /**
     * Make a Board from file
     * @param file to make Board from file
     * @throws IOException
     */
    public Board(File file) throws IOException{

        List<String> lines = Files.readAllLines(file.toPath());

        String[] size = lines.get(0).split(" ");
        columns = Integer.parseInt(size[1]);
        rows = Integer.parseInt(size[0]);
        board = new Square[rows][columns];

        //line.remove(0);

        for(int row = 1; row<lines.size();row++){
            String[] line = lines.get(row).split(" ");
            for(int col =0; col<line.length; col++){
                board[row-1][col] = line[col].equals("0") ? new Square(false) : new Square(true);
            }
        }

    }
    /**
     * get columns of board
     * @return columns of board
     */
    public int getColumns(){
        return columns;
    }
    /**
     * get rows of board
     * @return rows of board
     */
    public int getRows(){
        return rows;
    }
    /**
     * get board[row][col] square state
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     * @return state of the square
     */
    public synchronized SquareState getSquareState(int col, int row){
        return board[row][col].getState();
    }
    /**
     * change board[row][col] square state from untouched to flag
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     */
    public synchronized void flag(int col, int row){
        if(board[row][col].getState().equals(SquareState.dug)) return;
        board[row][col].setState(SquareState.flagged);
    }
    /**
     * change board[row][col] square state from flag to untouched
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     */
    public synchronized void unflag(int col, int row){
        if(board[row][col].getState().equals(SquareState.dug)) return;
        board[row][col].setState(SquareState.untouched);
    }
    /**
     * check board[row][col] square contains boom or not
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     * @return true if the sqaure contains a boom
     */
    public synchronized boolean isBoom(int col, int row){
        return board[row][col].getBoom();
    }
    /**
     * change board[row][col] square to contains boom or not
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     */
    public synchronized void setBoom(int col, int row, boolean boom){
        board[row][col].setBoom(boom);
    }
    /**
     * change board[row][col] square to dug
     * recursively check square nearby if sqaure it in the board OR Not conatins boom OR Not already dug
     * And dug those sqaures
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     */
    public synchronized void dig(int col, int row){

/*         if(board[row][col].getState().equals(SquareState.dug)){
            int count = countBoom(col, row);
            board[row][col].setBoomCount(count);
        } */

        if(board[row][col].getBoom() || !checkSquareInRange(row, col) || board[row][col].getState().equals(SquareState.dug)){
            return;
        }

        board[row][col].setState(SquareState.dug);
        int boomCount = countBoom(col, row);
        //board[row][col].setBoomCount(boomCount);

        int[][] neighbor = getNeighborList(col, row);

        if(boomCount==0){
            for(int[] coor : neighbor){
                //&& !board[coor[0]][coor[1]].getState().equals(SquareState.dug)
                if(checkSquareInRange(coor[1], coor[0]) ){
                    dig(coor[1], coor[0]);
                    //board[coor[1]][coor[0]].setState(SquareState.dug);
                }
            }
    }

    }
    /**
     * get board[row][col] square nearby squares list which contains out of range indexes
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     * @return nearby squares list which contains out of range indexes
     */
    private int[][] getNeighborList(int col, int row){
        int[][] result = {{row-1,col-1}, {row-1,col}, {row-1,col+1},
                          {row,col-1},            {row,col+1},
                          {row+1,col-1}, {row+1,col}, {row+1,col+1}};
        return result;
    }
    /**
     * Check in col OR row is out or range
     * @param col col in and outside board 
     * @param row row in and outside board 
     * @return  true if 0 <= col <columns & 0 <= row < rows,otherise false
     */
    private boolean checkSquareInRange(int col, int row){
        if(col<0 || row<0 || col>=columns || row>=rows){
            return false;
        }
        return true;
    }
    /**
     * count number of neaby squares booms
     * @param col col in board where, 0 <= col <columns
     * @param row row in board where, 0 <= row < rows
     * @return number of neaby squares booms
     */
    public synchronized int countBoom(int col, int row){
        int boom = 0;

        int[][] neighbor = getNeighborList(col, row);

        for(int[] coor : neighbor){
            if(checkSquareInRange(coor[1], coor[0])){
                if(board[coor[0]][coor[1]].getBoom()){
                    boom++;
                }
            }
        }

        return boom;
    }

    @Override
    public synchronized String toString(){
        String str = "";

        for(int row = 0; row<board.length;row++){
            for(int col =0; col<board[row].length; col++){
                if(countBoom(col, row)>0 && board[row][col].getState().equals(SquareState.dug)){
                    str += String.valueOf(countBoom(col, row));
                    //str += "g";
                }else{
                    str += board[row][col].toString();
                }
                if(col!=board[row].length-1) str += " ";
            }
            str += "\n";
        }

        return str;
    }

}
