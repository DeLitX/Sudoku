package com.example.sudoku;

public class SudokuNumbers {
    private boolean[] isNumber=new boolean[]{true,true,true,true,true,true,true,true,true};
    private int currentNumber=0;
    private boolean isDefault=false;

    public void setAllIsNumber(boolean[] isNumber){
        this.isNumber=isNumber;
    }
    public boolean[] getAllIsNumber(){
        return isNumber;
    }
    public boolean getIsDefault() {
        return isDefault;
    }
    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
    public int getCurrentNumber() {
        return currentNumber;
    }
    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }
    public void setIsNumber(boolean a,int place){
        isNumber[place]=a;
    }
    public boolean getIsNumber(int place){
        return isNumber[place];
    }
}
