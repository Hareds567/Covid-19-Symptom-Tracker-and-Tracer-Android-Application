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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ProgramAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] programName;
    private LayoutInflater layoutInflater;
    private View singleItem;
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
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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

                MyCovidData_UpdateWorkplace myCovidData_UpdateWorkplace = new MyCovidData_UpdateWorkplace();  //my covid data fragment
                Bundle arguments = new Bundle(); // creates a bundle to send data to update_worplace fragment
                arguments.putString("WORKPLACE", test); //argument that contains the name of the selected workplace
                myCovidData_UpdateWorkplace.setArguments(arguments);


                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,myCovidData_UpdateWorkplace, myCovidData_UpdateWorkplace.getTag()).addToBackStack(null).commit();
                parent.setVisibility(View.GONE);
            }
        });
        return singleItem;
    }
}