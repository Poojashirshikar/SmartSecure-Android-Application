package com.example.homepage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class SendFeedback extends Fragment {

    DatabaseHelper databaseHelper;
   EditText t10;

    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_feedback, container, false);

        t10 = (EditText) view.findViewById(R.id.edittext);


        button = view.findViewById(R.id.save);

        databaseHelper = new DatabaseHelper(getActivity());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userfeed=t10.getText().toString();

                if (TextUtils.isEmpty(userfeed)) {
                    Toast.makeText(getActivity(), "Give your feedback", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInsert = databaseHelper.feed(userfeed);
                    if (isInsert) {
                        Toast.makeText(getActivity(), "Feedback send Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Feedback send Failed", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        return view;
    }
}