package com.example.homepage;

import static android.widget.Toast.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class personaldetails extends Fragment {


    private EditText t1, t2, t3, t4, t5;
    private Button insert_btn;
    private DatabaseHelper dbhelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personaldetails, container, false);


        t1 = (EditText) view.findViewById(R.id.editTextText);
        t2 = (EditText) view.findViewById(R.id.editTextPhone);
        t3 = (EditText) view.findViewById(R.id.editTextTextEmailAddress);
        t4 = (EditText) view.findViewById(R.id.editTextTextHomeLocation);
        t5 = (EditText) view.findViewById(R.id.editTextTextWorkLocation);

        insert_btn = view.findViewById(R.id.savebutton);

        dbhelper = new DatabaseHelper(getActivity());

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = t1.getText().toString();
                String mobile_no = t2.getText().toString();
                String email = t3.getText().toString();
                String home_location = t4.getText().toString();
                String work_location = t5.getText().toString();

                if (!name.isEmpty() && !mobile_no.isEmpty() && !email.isEmpty() && !home_location.isEmpty() && !work_location.isEmpty()) {
                    //int contact = Integer.parseInt(mobile_no);

                    boolean isInserted = dbhelper.insertData(name,mobile_no, email, home_location, work_location);

                    if (isInserted==true) {
                        Toast.makeText(getActivity(), "Saved Successfully",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Enter All Fileds",Toast. LENGTH_SHORT).show();
                }

            }
        });





        return view;

    }

}


