package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class Solver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);
        View view=new TextView(getBaseContext());
        SolverLogic solver=new SolverLogic();
        /*int[][] matrix=new int[][]{     {8,0,0,0,0,0,0,0,0},
                                        {0,0,7,5,0,0,0,0,9},
                                        {0,3,0,0,0,0,1,8,0},
                                        {0,6,0,0,0,1,0,5,0},
                                        {0,0,9,0,4,0,0,0,0},
                                        {0,0,0,7,5,0,0,0,0},
                                        {0,0,2,0,7,0,0,0,4},
                                        {0,0,0,0,0,3,6,1,0},
                                        {0,0,0,0,0,0,8,0,0}};
        solver.setMatrix(matrix);*/

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        solver.setScreenHeight(displayMetrics.heightPixels);
        solver.setScreenWidth(displayMetrics.widthPixels);
        solver.rl=findViewById(R.id.field);
        solver.context=view.getContext();
        solver.start();
    }
}
