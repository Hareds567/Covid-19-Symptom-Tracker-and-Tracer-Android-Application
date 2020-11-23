package com.example.myworkplace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] programName;

    public ProgramAdapter(Context context, String[] programName, int[] images) {
        super(context, R.layout.single_item, R.id.textView1, programName);
        this.context = context;
        this.images = images;
        this.programName = programName;
        this.images = images;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View singleItem = convertView;
        ProgramViewHolder holder = null;
        if(singleItem == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_item, parent, false);
            holder = new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);

        }

        else {
            holder = (ProgramViewHolder) singleItem.getTag();
        }

        holder.itemImage.setImageResource(images[position]);
        holder.programTitle.setText(programName[position]);

        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You clicked" + programName[position], Toast.LENGTH_SHORT).show();
            }
        });


        return singleItem;
    }
}
