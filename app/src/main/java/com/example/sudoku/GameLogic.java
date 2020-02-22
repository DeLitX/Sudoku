package com.example.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameLogic {
    private int screenHeight;
    private int screenWidth;
    private int mainSquareSize;
    Context context;
    private RelativeLayout.LayoutParams[] layoutParams=new RelativeLayout.LayoutParams[9];
    RelativeLayout rl;
    private SudokuNumbers[][] numbers=new SudokuNumbers[9][9];
    private TextView[][] textOfNumbers=new TextView[9][9];
    private Button[] buttons=new Button[11];
    private TextView[] squares=new TextView[2];
    private Point choosenNumber=new Point();
    private TextView highlighted;
    private int[][] matrix=new int[9][9];
    private int counter=0;
    private Point level=new Point(0,0);
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS="shared prefs";
    private static final String SAVED_NUMBERS="current number of:";
    private static final String SAVED_DEFAULT="is it default:";


    public void start(){
        buildUI();
        addActionListener();
        loadData();
        fromMatrixToSudoku(matrix);
        choosenNumber.x=-1;
        choosenNumber.y=-1;
        globalSaveData();
    }
    private void globalSaveData(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                editor.putInt(SAVED_NUMBERS+y+x+level.x+level.y,numbers[y][x].getCurrentNumber());
                editor.putBoolean(SAVED_DEFAULT+y+x+level.x+level.y,numbers[y][x].getIsDefault());
            }
        }
        editor.apply();
    }
    private void localSaveData(int y,int x){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(SAVED_NUMBERS+y+x+level.x+level.y,numbers[y][x].getCurrentNumber());
        editor.apply();
    }
    private void loadData(){
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                numbers[y][x].setCurrentNumber(sharedPreferences.getInt(SAVED_NUMBERS+y+x+level.x+level.y,0));
                numbers[y][x].setDefault(sharedPreferences.getBoolean(SAVED_DEFAULT+y+x+level.x+level.y,false));
                if(numbers[y][x].getIsDefault()){
                    textOfNumbers[y][x].setTextColor(Color.GRAY);
                }
                if(numbers[y][x].getCurrentNumber()!=0){
                    textOfNumbers[y][x].setText(numbers[y][x].getCurrentNumber()+"");
                    counter++;
                }
                else
                    textOfNumbers[y][x].setText("");
            }
            if(counter==81)
                checkSudoku();
        }
    }
    private void buildUI(){
        for (int i=0;i<buttons.length;i++)
            buttons[i]=new Button(context);
        for(int i=0;i<layoutParams.length;i++)
            layoutParams[i]=new RelativeLayout.LayoutParams(screenWidth,screenHeight);
        for(int i=0;i<numbers.length;i++)
            for(int k=0;k<numbers.length;k++){
                textOfNumbers[i][k]=new TextView(context);
                numbers[i][k]=new SudokuNumbers();
            }
        squares[0]=new TextView(context);
        squares[1]=new TextView(context);
        highlighted=new TextView(context);
        mainSquareSize=screenWidth;
        squares[0].setX(0);
        squares[0].setY(0);
        squares[0].setBackgroundColor(Color.BLACK);
        squares[0].setVisibility(View.VISIBLE);
        squares[0].setLayoutParams(layoutParams[1]);
        squares[0].getLayoutParams().height=mainSquareSize;
        squares[0].getLayoutParams().width=mainSquareSize;
        rl.addView(squares[0]);
        for(int x=0;x<9;x++){
            for(int y=0;y<9;y++) {
                textOfNumbers[x][y].setX(y * (mainSquareSize * 105 / 1000 + mainSquareSize * 2 / 1000) + mainSquareSize * 13 / 1000 + (y / 3) * mainSquareSize *8/ 1000);
                textOfNumbers[x][y].setY(x * (mainSquareSize * 105 / 1000 + mainSquareSize * 2 / 1000) + mainSquareSize * 13 / 1000 + (x / 3) * mainSquareSize *8/ 1000);
                textOfNumbers[x][y].setBackgroundColor(Color.WHITE);
                textOfNumbers[x][y].setVisibility(View.VISIBLE);
                textOfNumbers[x][y].setLayoutParams(layoutParams[0]);
                textOfNumbers[x][y].setTextColor(Color.BLACK);
                textOfNumbers[x][y].setTextSize(mainSquareSize * 3 / 100);
                textOfNumbers[x][y].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                textOfNumbers[x][y].getLayoutParams().height = mainSquareSize * 105 / 1000;
                textOfNumbers[x][y].getLayoutParams().width = mainSquareSize * 105 / 1000;
                rl.addView(textOfNumbers[x][y]);
            }
        }
        squares[1].setX(0);
        squares[1].setY(mainSquareSize);
        squares[1].setBackgroundColor(Color.GRAY);
        squares[1].setVisibility(View.VISIBLE);
        squares[1].setLayoutParams(layoutParams[2]);
        squares[1].getLayoutParams().height=screenHeight-mainSquareSize;
        squares[1].getLayoutParams().width=screenWidth;
        rl.addView(squares[1]);

        layoutParams[3].height=(screenHeight-mainSquareSize)*9/30;
        layoutParams[3].width=mainSquareSize*6/25;
        for(int i=0;i<3;i++){
            for(int k=0;k<3;k++){
                buttons[1+i*3+k].setLayoutParams(layoutParams[3]);
                buttons[1+i*3+k].setX(mainSquareSize/125+k*buttons[1+i*3+k].getLayoutParams().width+k*mainSquareSize/125);
                buttons[1+i*3+k].setY(mainSquareSize+(screenHeight-mainSquareSize)/120+i*(buttons[1+i*3+k].getLayoutParams().height+(screenHeight-mainSquareSize)/120));
                buttons[1+i*3+k].setBackgroundColor(Color.WHITE);
                buttons[1+i*3+k].setVisibility(View.VISIBLE);
                buttons[1+i*3+k].setText(1+i*3+k+"");
                buttons[1+i*3+k].setTextColor(Color.BLACK);
                buttons[1+i*3+k].setTextSize(mainSquareSize*3/100);
                buttons[1+i*3+k].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                rl.addView(buttons[1+i*3+k]);
            }
        }
        buttons[0].setLayoutParams(layoutParams[3]);
        buttons[0].setX(buttons[3].getX()+mainSquareSize/125+buttons[0].getLayoutParams().width);
        buttons[0].setY(buttons[3].getY());
        buttons[0].setBackgroundColor(Color.WHITE);
        buttons[0].setVisibility(View.VISIBLE);
        buttons[0].setText("CLEAR");
        buttons[0].setTextColor(Color.BLACK);
        buttons[0].setTextSize(mainSquareSize*2/100);
        buttons[0].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        rl.addView(buttons[0]);

        buttons[10].setLayoutParams(layoutParams[3]);
        buttons[10].setX(buttons[6].getX()+mainSquareSize/125+buttons[10].getLayoutParams().width);
        buttons[10].setY(buttons[6].getY());
        buttons[10].setBackgroundColor(Color.WHITE);
        buttons[10].setVisibility(View.VISIBLE);
        buttons[10].setText("CLEAR ALL");
        buttons[10].setTextColor(Color.BLACK);
        buttons[10].setTextSize(mainSquareSize*2/100);
        buttons[10].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        rl.addView(buttons[10]);

        highlighted.setBackgroundColor(Color.YELLOW);
        highlighted.setVisibility(View.INVISIBLE);
        highlighted.setLayoutParams(layoutParams[0]);
        highlighted.setTextColor(Color.BLACK);
        highlighted.setTextSize(mainSquareSize*3/100);
        highlighted.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        highlighted.getLayoutParams().height=mainSquareSize*105/1000;
        highlighted.getLayoutParams().width=mainSquareSize*105/1000;
        rl.addView(highlighted);
    }
    public int[][] getMatrix() {
        return matrix;
    }
    public void setLevel(int[]level1){
        level.x=level1[0];
        level.y=level1[1];
    }
    public void setSharedPreferences(SharedPreferences sharedPreferences){
        this.sharedPreferences=sharedPreferences;
    }
    public String getStringSharedPrefs(){
        return SHARED_PREFS;
    }
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
    private void fromMatrixToSudoku(int[][] matrix){
        boolean isRecreate=false;
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                if(matrix[x][y]!=0){
                    if(!isRecreate){
                        if(sharedPreferences.getInt(SAVED_NUMBERS+x+y+level.x+level.y,0)!=matrix[x][y]||!sharedPreferences.getBoolean(SAVED_DEFAULT+x+y+level.x+level.y,false)) {
                            isRecreate=true;
                            break;
                        }
                    }
                }
            }
        }
        if(isRecreate){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            for(int y=0;y<9;y++){
                for(int x=0;x<9;x++){
                    editor.putInt(SAVED_NUMBERS+x+y+level.x+level.y,matrix[x][y]);
                    if(matrix[x][y]==0){
                        editor.putBoolean(SAVED_DEFAULT+x+y+level.x+level.y,false);
                        textOfNumbers[x][y].setText("");
                        textOfNumbers[x][y].setTextColor(Color.BLACK);
                        numbers[x][y].setCurrentNumber(matrix[x][y]);
                        numbers[x][y].setDefault(false);
                    }
                    else{
                        editor.putBoolean(SAVED_DEFAULT+x+y+level.x+level.y,true);
                        textOfNumbers[x][y].setText(matrix[x][y]+"");
                        textOfNumbers[x][y].setTextColor(Color.GRAY);
                        numbers[x][y].setCurrentNumber(matrix[x][y]);
                        numbers[x][y].setDefault(true);
                    }
                }
            }
            editor.apply();
        }
    }
    private void addActionListener(){
        for(int x=0;x<9;x++){
            final int n=x;
            for(int y=0;y<9;y++){
                final int m=y;
                textOfNumbers[x][y].setOnClickListener(
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choosenNumber.x = n;
                        choosenNumber.y =m;
                        highlighted.setX(textOfNumbers[choosenNumber.x][choosenNumber.y].getX());
                        highlighted.setY(textOfNumbers[choosenNumber.x][choosenNumber.y].getY());
                        highlighted.setText(textOfNumbers[choosenNumber.x][choosenNumber.y].getText());
                        highlighted.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
        for(int i=0;i<buttons.length;i++){
            final int n=i;
            buttons[i].setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (n == 10) {
                                clearAllSudoku();
                            }
                            else{
                                if(choosenNumber.x!=-1&&choosenNumber.y!=-1){
                                    if(!numbers[choosenNumber.x][choosenNumber.y].getIsDefault()){
                                        if(n==0){
                                            textOfNumbers[choosenNumber.x][choosenNumber.y].setText("");
                                            if(numbers[choosenNumber.x][choosenNumber.y].getCurrentNumber()!=0)
                                                counter--;
                                            highlighted.setText("");
                                        }
                                        else{
                                            if(numbers[choosenNumber.x][choosenNumber.y].getCurrentNumber()==0)
                                                counter++;
                                            textOfNumbers[choosenNumber.x][choosenNumber.y].setText(n+"");
                                            highlighted.setText(n+"");
                                        }
                                        numbers[choosenNumber.x][choosenNumber.y].setCurrentNumber(n);
                                        if(counter==81)
                                            checkSudoku();
                                        localSaveData(choosenNumber.x,choosenNumber.y);
                                    }
                                }
                            }
                        }
                    }
            );
        }
    }
    private void clearAllSudoku(){
        for(int i=0;i<9;i++){
            for(int k=0;k<9;k++){
                if(!numbers[i][k].getIsDefault()){
                    if(numbers[i][k].getCurrentNumber()!=0){
                        counter--;
                    }
                    numbers[i][k].setCurrentNumber(0);
                    textOfNumbers[i][k].setText(" ");
                }
            }
        }
        highlighted.setText("");
        globalSaveData();
    }
    private void checkSudoku(){
        if(checkLines()&&checkColumns()&&checkSquares())
            Toast.makeText(context,"Congratulations",Toast.LENGTH_LONG).show();
    }
    private boolean checkLines(){
        for(int i=0;i<9;i++){
            int sum=0;
            for(int k=0;k<9;k++){
                sum+=numbers[k][i].getCurrentNumber();
            }
            if(sum!=45)
                return false;
        }
        return true;
    }
    private boolean checkColumns(){
        for(int i=0;i<9;i++){
            int sum=0;
            for(int k=0;k<9;k++){
                sum+=numbers[i][k].getCurrentNumber();
            }
            if(sum!=45)
                return false;
        }
        return true;
    }
    private boolean checkSquares(){
        for(int i=0;i<3;i++){
            for(int k=0;k<3;k++){
                int sum=0;
                for(int y=0;y<3;y++){
                    for(int x=0;x<3;x++){
                        sum+=numbers[k*3+x][i*3+y].getCurrentNumber();
                    }
                }
                if(sum!=45)
                    return false;
            }
        }
        return true;
    }
}
