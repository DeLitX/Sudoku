package com.example.sudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LevelChoose extends AppCompatActivity {
    Button levelsEasy[]=new Button[20];
    RelativeLayout.LayoutParams layoutParams;
    RelativeLayout rl;
    public static final String PUT_LEVEL="level:";
    public static final String PUT_LEVEL_MATRIXES="level matrix";
    int countOfButtonsInRow;
    int levelMatrixes[][][][]=new int [4][100][9][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choose);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        layoutParams=new RelativeLayout.LayoutParams(displayMetrics.widthPixels*7/36,displayMetrics.widthPixels*7/36);
        rl=findViewById(R.id.field);
        countOfButtonsInRow=5;
        setLevelsMatxixes();
        buildUI();
        addActionListener();
    }
    private void addActionListener(){
        for(int i=0;i<levelsEasy.length;i++){
            final int n=i;
            levelsEasy[i].setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(LevelChoose.this,ChoosenLevel.class);
                            intent.putExtra(PUT_LEVEL,new int[]{1,n+1});
                            for(int i=0;i<9;i++)
                                intent.putExtra(PUT_LEVEL_MATRIXES+i,levelMatrixes[0][n][i]);
                            startActivity(intent);
                        }
                    }
            );
        }
    }
    private void buildUI(){
        for(int i=0;i<levelsEasy.length;i++){
            levelsEasy[i]=new Button(getBaseContext());
            levelsEasy[i].setLayoutParams(layoutParams);
            levelsEasy[i].setX(layoutParams.width/42+(i%countOfButtonsInRow)*(levelsEasy[i].getLayoutParams().width+layoutParams.width/42));
            levelsEasy[i].setY(layoutParams.width/42+(i/countOfButtonsInRow)*(levelsEasy[i].getLayoutParams().height+layoutParams.width/42));
            levelsEasy[i].setText("Level "+(i+1));
            levelsEasy[i].setVisibility(View.VISIBLE);
            rl.addView(levelsEasy[i]);
        }
    }
    private void setLevelsMatxixes(){
        levelMatrixes[0][0]=new int [][]   {{8,0,0,0,0,0,0,0,0},
                                            {0,0,7,5,0,0,0,0,9},
                                            {0,3,0,0,0,0,1,8,0},
                                            {0,6,0,0,0,1,0,5,0},
                                            {0,0,9,0,4,0,0,0,0},
                                            {0,0,0,7,5,0,0,0,0},
                                            {0,0,2,0,7,0,0,0,4},
                                            {0,0,0,0,0,3,6,1,0},
                                            {0,0,0,0,0,0,8,0,0}};
    }
}
