package com.example.myworkplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView lvProgram;
    String[] workplaces = {"Scales Hall" , "Waterbury Hall", "Sheldon Hall", "Hart Hall", "Riggs Hall",
            "Johnson Hall", "Oneida Hall", "Seneca Hall", "Onondaga Hall", "Cayuga Hall", "Shineman Center", "Marano Campus Center", "Wilbur Hall", "Park Hall", "Penfield Library",
            "Hewitt Union", "Mahar Hall", "Lanigan Hall", "Cooper Dining Center", "Lakeside Dining Center", "Littlepage Dining Center",
            "Pathfinder Dining Center"};

    int[] images = {R.drawable.reshall, R.drawable.reshall2, R.drawable.reshall3, R.drawable.reshall4, R.drawable.reshall5,
                    R.drawable.reshall7, R.drawable.reshall8, R.drawable.reshall9, R.drawable.reshall10,
                    R.drawable.reshall11,  R.drawable.workplace, R.drawable.workplace1, R.drawable.workplace2, R.drawable.workplace3,
                    R.drawable.workplace4, R.drawable.workplace5,  R.drawable.workplace6,  R.drawable.workplace7,  R.drawable.workplace8,
                    R.drawable.workplace9};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvProgram = findViewById(R.id.lvProgram);
        ProgramAdapter programAdapater = new ProgramAdapter(this, workplaces, images);
        lvProgram.setAdapter(programAdapater);
    }

    //
}
