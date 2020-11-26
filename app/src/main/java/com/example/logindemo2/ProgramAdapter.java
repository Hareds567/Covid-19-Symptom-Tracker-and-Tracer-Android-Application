package com.example.logindemo2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class ProgramAdapter extends ArrayAdapter<String> {
    Context context;
    String[] programName;

    public ProgramAdapter(Context context, String[] programName) {
        super(context, R.layout.single_item, R.id.textView1, programName);
        this.context = context;
        this.programName = programName;
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

        holder.programTitle.setText(programName[position]);

        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You clicked" + programName[position], Toast.LENGTH_SHORT).show();
                String test = programName[position];

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MyCovidData_UpdateWorkplace MyCovidData_UpdateWorkplace = new MyCovidData_UpdateWorkplace();
                Bundle arguments = new Bundle();

                arguments.putString("WORKPLACE", test);

                System.out.println("Program Adapter Workplace: " + test);
                System.out.println("Arguments from the Program Adapter: " + arguments);
                MyCovidData_UpdateWorkplace.setArguments(arguments);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,MyCovidData_UpdateWorkplace, MyCovidData_UpdateWorkplace.getTag()).addToBackStack(null).commit();

            }
        });


        return singleItem;
    }
}