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
    
    // TODO: Abstraction function, rep invariant, rep exposure, thread safety
    
    // TODO: Specify, test, and implement in problem 2

    private final Square[][] board;
    private final int rows;
    private final int columns;
    private final Double boomChance = 0.25;

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

    public int getColumns(){
        return columns;
    }

    public int getRows(){
        return rows;
    }

    public synchronized SquareState getSquareState(int col, int row){
        return board[row][col].getState();
    }

    public synchronized void flag(int col, int row){
        if(board[row][col].getState().equals(SquareState.dug)) return;
        board[row][col].setState(SquareState.flagged);
    }

    public synchronized void unflag(int col, int row){
        if(board[row][col].getState().equals(SquareState.dug)) return;
        board[row][col].setState(SquareState.untouched);
    }

    public synchronized boolean isBoom(int col, int row){
        return board[row][col].getBoom();
    }

    public synchronized void setBoom(int col, int row, boolean boom){
        board[row][col].setBoom(boom);
    }

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

    private int[][] getNeighborList(int col, int row){
        int[][] result = {{row-1,col-1}, {row-1,col}, {row-1,col+1},
                          {row,col-1},            {row,col+1},
                          {row+1,col-1}, {row+1,col}, {row+1,col+1}};
        return result;
    }

    private boolean checkSquareInRange(int col, int row){
        if(col<0 || row<0 || col>=columns || row>=rows){
            return false;
        }
        return true;
    }

    public int countBoom(int col, int row){
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
