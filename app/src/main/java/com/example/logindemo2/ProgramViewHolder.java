package com.example.logindemo2;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class ProgramViewHolder {
    ImageView itemImage;
    TextView programTitle;

    ProgramViewHolder(View v) {
        itemImage = v.findViewById(R.id.imageView);
        programTitle = v.findViewById(R.id.textView1);
    }

}