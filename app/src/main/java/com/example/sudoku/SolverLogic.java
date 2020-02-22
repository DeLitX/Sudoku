package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SolverLogic {
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
    private Button calculate;
    private int[][] matrix=new int[9][9];


    public void start(){
        choosenNumber.x=-1;
        choosenNumber.y=-1;
        buildUI();
        addActionListener();
        fromMatrixToSudoku(matrix);
    }
    private void buildUI(){
        for (int i=0;i<buttons.length;i++)
            buttons[i]=new Button(context);
        for(int i=0;i<layoutParams.length;i++)
            layoutParams[i]=new RelativeLayout.LayoutParams(screenWidth,screenHeight);
        for(int i=0;i<numbers.length;i++)
            for(int k=0;k<numbers.length;k++) {
                numbers[i][k] = new SudokuNumbers();
                textOfNumbers[i][k] = new TextView(context);
            }

        calculate=new Button(context);
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
        layoutParams[0].height=mainSquareSize*105/1000;
        layoutParams[0].width=mainSquareSize*105/1000;
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

        calculate.setLayoutParams(layoutParams[3]);
        calculate.setX(buttons[9].getX()+mainSquareSize/125+calculate.getLayoutParams().width);
        calculate.setY(buttons[9].getY());
        calculate.setBackgroundColor(Color.GREEN);
        calculate.setVisibility(View.VISIBLE);
        calculate.setText("Solve");
        calculate.setTextColor(Color.BLACK);
        calculate.setTextSize(mainSquareSize*2/100);
        calculate.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        rl.addView(calculate);
    }
    public int[][] getMatrix() {
        return matrix;
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
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                if(matrix[x][y]!=0){
                    numbers[x][y].setCurrentNumber(matrix[x][y]);
                    numbers[x][y].setDefault(true);
                    textOfNumbers[x][y].setText(matrix[x][y]+"");
                    textOfNumbers[x][y].setTextColor(Color.GRAY);
                }
            }
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
                                for(int i=0;i<9;i++){
                                    for(int k=0;k<9;k++){
                                        if(!numbers[i][k].getIsDefault()){
                                            numbers[i][k].setCurrentNumber(0);
                                            textOfNumbers[i][k].setText(" ");
                                            for(int l=0;l<9;l++){
                                                numbers[i][k].setIsNumber(true,l);
                                            }
                                        }else{
                                            numbers[i][k].setIsNumber(true,numbers[i][k].getCurrentNumber()-1);
                                        }
                                    }
                                }
                                if(choosenNumber.x!=-1&&choosenNumber.y!=-1){
                                    if(numbers[choosenNumber.x][choosenNumber.y].getCurrentNumber()==0){
                                        highlighted.setText("");
                                    }else{
                                        highlighted.setText(numbers[choosenNumber.x][choosenNumber.y].getCurrentNumber()+"");
                                    }
                                }
                            }else {
                                if(choosenNumber.x!=-1&&choosenNumber.y!=-1){
                                    if(!numbers[choosenNumber.x][choosenNumber.y].getIsDefault()){
                                        numbers[choosenNumber.x][choosenNumber.y].setCurrentNumber(n);
                                        if(n==0){
                                            textOfNumbers[choosenNumber.x][choosenNumber.y].setText("");
                                            highlighted.setText("");
                                        }
                                        else{
                                            textOfNumbers[choosenNumber.x][choosenNumber.y].setText(numbers[choosenNumber.x][choosenNumber.y].getCurrentNumber()+"");
                                            highlighted.setText(textOfNumbers[choosenNumber.x][choosenNumber.y].getText());
                                        }

                                    }
                                }
                            }
                        }
                    }
            );
        }
        calculate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        solve();
                    }
                }
        );
    }
    private void solve(){
        SudokuNumbers[][] newNumbers=numbersEqual(numbers);
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                if(newNumbers[x][y].getCurrentNumber()!=0){
                    newNumbers=markWrong(x,y,newNumbers);
                }
            }
        }
        newNumbers=checkForNumbers(newNumbers);

        if(!checkSudoku(newNumbers)){
            newNumbers= suggestion(newNumbers);
        }
        if(newNumbers==null){
            Toast.makeText(context,"It is unsolvable",Toast.LENGTH_LONG).show();
        }else{
            writeNumbers(newNumbers);
            if(checkSudoku(newNumbers))
                Toast.makeText(context,"it right",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"it wrong",Toast.LENGTH_LONG).show();
            numbers=numbersEqual(newNumbers);
        }
    }
    private SudokuNumbers[][] markWrong(int x, int y, SudokuNumbers[][]currentMatrix){
        for(int i=0;i<9;i++){
            if(i!=currentMatrix[x][y].getCurrentNumber()-1){
                currentMatrix[x][y].setIsNumber(false,i);
            }
        }
        for(int i=0;i<9;i++){
            if(i!=y){
                currentMatrix[x][i].setIsNumber(false,currentMatrix[x][y].getCurrentNumber()-1);
            }
            if(i!=x){
                currentMatrix[i][y].setIsNumber(false,currentMatrix[x][y].getCurrentNumber()-1);
            }
        }
        for(int i=0;i<3;i++){
            for(int k=0;k<3;k++){
                if(x-x%3+k!=x&&y-y%3+i!=y)
                    currentMatrix[x-x%3+k][y-y%3+i].setIsNumber(false,currentMatrix[x][y].getCurrentNumber()-1);
            }
        }
        return currentMatrix;
    }
    private SudokuNumbers[][] checkForNumbers(SudokuNumbers[][] currentMatrix){
        currentMatrix=checkForObviousNumbers(currentMatrix);
        currentMatrix=checkForNumbersInColumns(currentMatrix);
        currentMatrix=checkForNumbersInLines(currentMatrix);
        currentMatrix=checkForNumbersInSquare(currentMatrix);
        return currentMatrix;
    }
    private void writeNumbers(SudokuNumbers[][]currentMatrix){
        for(int x=0;x<9;x++){
            for(int y=0;y<9;y++){
                if(currentMatrix[x][y].getCurrentNumber()!=0)
                textOfNumbers[x][y].setText(currentMatrix[x][y].getCurrentNumber()+"");
            }
        }
        if(choosenNumber.x!=-1&&choosenNumber.y!=-1)
            highlighted.setText(currentMatrix[choosenNumber.x][choosenNumber.y].getCurrentNumber()+"");
    }
    private SudokuNumbers[][] checkForObviousNumbers(SudokuNumbers[][] currentMatrix){
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                if(currentMatrix[x][y].getCurrentNumber()==0){
                    int count=0;
                    int position=-1;
                    for(int i=0;i<9;i++){
                        if(currentMatrix[x][y].getIsNumber(i)){
                            count++;
                            position=i;
                        }
                    }
                    if(count==1){
                        currentMatrix[x][y].setCurrentNumber(position+1);
                        currentMatrix=markWrong(x,y,currentMatrix);
                        currentMatrix=checkForNumbers(currentMatrix);
                    }
                }
            }
        }
        return currentMatrix;
    }
    private  SudokuNumbers[][] checkForNumbersInSquare(SudokuNumbers[][] currentMatrix){
        for(int i=0;i<9;i++){
            for(int k=0;k<3;k++){
                for(int l=0;l<3;l++){
                    int count=0;
                    Point position=new Point();
                    for(int a=0;a<3;a++){
                        for(int b=0;b<3;b++){
                            if(currentMatrix[k*3+a][l*3+b].getIsNumber(i)){
                                if(currentMatrix[k*3+a][l*3+b].getCurrentNumber()!=0){
                                    a=3;
                                    count=2;
                                    break;
                                }
                                count++;
                                position.x=k*3+a;
                                position.y=l*3+b;
                            }
                        }
                    }
                    if(count==1){
                        currentMatrix[position.x][position.y].setCurrentNumber(i+1);
                        currentMatrix=markWrong(position.x,position.y,currentMatrix);
                        currentMatrix=checkForNumbers(currentMatrix);
                    }
                }
            }
        }
        return currentMatrix;
    }
    private SudokuNumbers[][] checkForNumbersInLines(SudokuNumbers[][]currentMatrix){
        for(int i=0;i<9;i++){
            for(int k=0;k<9;k++){
                int count=0;

                Point position=new Point();
                for(int l=0;l<9;l++){
                    if(currentMatrix[l][k].getIsNumber(i)){
                        if(currentMatrix[l][k].getCurrentNumber()!=0){
                            count=2;
                            break;
                        }
                        count++;
                        position.x=l;
                        position.y=k;
                    }
                }
                if(count==1){
                    currentMatrix[position.x][position.y].setCurrentNumber(i+1);
                    currentMatrix=markWrong(position.x,position.y,currentMatrix);
                    currentMatrix=checkForNumbers(currentMatrix);
                }
            }
        }
        return currentMatrix;
    }
    private SudokuNumbers[][] checkForNumbersInColumns(SudokuNumbers[][]currentMatrix){
        for(int i=0;i<9;i++){
            for(int l=0;l<9;l++){
                int count=0;
                Point position=new Point();
                for(int k=0;k<9;k++){
                    if(currentMatrix[l][k].getIsNumber(i)){
                        if(currentMatrix[l][k].getCurrentNumber()!=0){
                            count=2;
                            break;
                        }
                        count++;
                        position.x=l;
                        position.y=k;
                    }
                }
                if(count==1){
                    currentMatrix[position.x][position.y].setCurrentNumber(i+1);
                    currentMatrix=markWrong(position.x,position.y,currentMatrix);
                    currentMatrix=checkForNumbers(currentMatrix);
                }
            }
        }
        return currentMatrix;
    }
    private SudokuNumbers[][] suggestion(SudokuNumbers[][] previousIteration){
        SudokuNumbers[][] currentMatrix=previousIteration;
        for(int k=2;k<=9;k++){
            for(int y=0;y<9;y++){
                for(int x=0;x<9;x++){
                    if(currentMatrix[x][y].getCurrentNumber()==0){
                        int count=0;
                        for(int i=0;i<9;i++){
                            if(currentMatrix[x][y].getIsNumber(i))
                                count++;
                        }
                        if(count==k){
                            for(int i=0;i<9;i++){
                                if(currentMatrix[x][y].getIsNumber(i)){
                                    SudokuNumbers[][] newMatrix=numbersEqual(currentMatrix);
                                    newMatrix[x][y].setCurrentNumber(i+1);
                                    newMatrix=solveRec(newMatrix,x,y);
                                    if(newMatrix==null){
                                        currentMatrix[x][y].setIsNumber(false,i);
                                    }
                                    else{
                                        return newMatrix;
                                    }
                                }
                            }
                        }else if(count==0){
                            return null;
                        }
                    }
                }
            }
        }
        if(checkSudoku(currentMatrix))
            return currentMatrix;
        else
            return null;
    }
    private SudokuNumbers[][] solveRec(SudokuNumbers[][] currentMatrix, int x, int y){
        currentMatrix=markWrong(x,y,currentMatrix);
        currentMatrix=checkForNumbers(currentMatrix);
        if(checkSudoku(currentMatrix))
            return currentMatrix;
        else
            return  suggestion(currentMatrix);
    }
    private boolean checkSudoku(SudokuNumbers[][] currentMatrix){
        if(checkLines(currentMatrix)&&checkColumns(currentMatrix)&&checkSquares(currentMatrix))
            return true;
        else
            return false;
    }
    private boolean checkLines(SudokuNumbers[][] currentMatrix){
        for(int i=0;i<9;i++){
            int sum=0;
            for(int k=0;k<9;k++){
                sum+=currentMatrix[k][i].getCurrentNumber();
            }
            if(sum!=45)
                return false;
        }
        return true;
    }

    private boolean checkColumns(SudokuNumbers[][] currentMatrix){
        for(int i=0;i<9;i++){
            int sum=0;
            for(int k=0;k<9;k++){
                sum+=currentMatrix[i][k].getCurrentNumber();
            }
            if(sum!=45)
                return false;
        }
        return true;
    }
    private boolean checkSquares(SudokuNumbers[][] currentMatrix){
        for(int i=0;i<3;i++){
            for(int k=0;k<3;k++){
                int sum=0;
                for(int y=0;y<3;y++){
                    for(int x=0;x<3;x++){
                        sum+=currentMatrix[k*3+x][i*3+y].getCurrentNumber();
                    }
                }
                if(sum!=45)
                    return false;
            }
        }
        return true;
    }
    private SudokuNumbers[][] numbersEqual(SudokuNumbers[][] currentMatrix){
        SudokuNumbers[][] newMatrix=new SudokuNumbers[9][9];
        for(int i=0;i<9;i++){
            for(int k=0;k<9;k++){
                newMatrix[i][k]=new SudokuNumbers();
                for(int l=0;l<9;l++)
                    newMatrix[i][k].setIsNumber(currentMatrix[i][k].getIsNumber(l),l);
                newMatrix[i][k].setCurrentNumber(currentMatrix[i][k].getCurrentNumber());
                newMatrix[i][k].setDefault(currentMatrix[i][k].getIsDefault());
            }
        }
        return newMatrix;
    }
}
