package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class ChoosenLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameLogic game=new GameLogic();
        View view=new TextView(getBaseContext());
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_choosen_level);
        Intent intent=getIntent();
        int [][]levelMatrix=new int[9][9];
        for(int i=0;i<9;i++)
            levelMatrix[i]=intent.getIntArrayExtra(LevelChoose.PUT_LEVEL_MATRIXES+i);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        game.setScreenHeight(displayMetrics.heightPixels);
        game.setScreenWidth(displayMetrics.widthPixels);
        game.rl=findViewById(R.id.field);
        game.context=view.getContext();
        game.setMatrix(levelMatrix);
        game.setLevel(intent.getIntArrayExtra(LevelChoose.PUT_LEVEL));
        game.setSharedPreferences(getSharedPreferences(game.getStringSharedPrefs(),MODE_PRIVATE));
        game.start();
    }
}
