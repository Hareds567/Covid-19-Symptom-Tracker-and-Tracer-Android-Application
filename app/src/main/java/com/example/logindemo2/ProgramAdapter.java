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
        parent.setVisibility(View.VISIBLE);
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


        boolean result = true;
        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You clicked" + programName[position], Toast.LENGTH_SHORT).show();
                String test = programName[position];

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MyCovidData_UpdateWorkplace MyCovidData_UpdateWorkplace = new MyCovidData_UpdateWorkplace();  //my covid data fragment
                MyCovidData_WorkPlace workplace = new MyCovidData_WorkPlace(); //workplace fragment

                Bundle superBundle = new Bundle();

                Bundle arg_for_UpdateWorkplace = new Bundle(); // creates a bundle to send data to update_worplace fragment
                arg_for_UpdateWorkplace.putString("WORKPLACE", test); //argument that contains the name of the selected workplace

                Bundle arg_for_Workplace = new Bundle();
                arg_for_Workplace.putBoolean("CLICKED", true);

                superBundle.putBundle("ARG_FOR_UPDATEWORKPLACE", arg_for_UpdateWorkplace);
                superBundle.putBundle("ARG_FOR_WORKPLACE", arg_for_Workplace);

                System.out.println("Program Adapter Workplace: " + test);
                System.out.println("Arguments from the Program Adapter: " + arg_for_UpdateWorkplace);
               // singleItem.setVisible.setVisibility(View)

                MyCovidData_UpdateWorkplace.setArguments(superBundle); //Sets the arguments containing both bundles;
                workplace.setArguments(superBundle);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,workplace, workplace.getTag()).commit();
                parent.setVisibility(View.GONE);
            }
        });
        return singleItem;
    }
}