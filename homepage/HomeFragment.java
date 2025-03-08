package com.example.homepage;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

    public class HomeFragment extends Fragment {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
        private static final int PERMISSION_REQUEST_CODE = 1003;
        TextView textView;
    ImageButton imageButton;
    private static final int REQUEST_CONTACT_PICKER=1001;
    private static final int REQUEST_SMS_PERMISSION=1002;
    private String emgmsg="This is an emergency.Please help";
    private String phoneno;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);


        textView=view.findViewById(R.id.text_input);
        imageButton= view.findViewById(R.id.image_mick);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        checkPermission();

       return view;


    }
    private void checkPermission(){
        /*if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED )
        {
            requestPermissions(new String[]
            {
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_CONTACT,
                Manifest.permission.READ_AUDIO
            },REQUEST_SMS_PERMISSION);
        }*/
        ActivityCompat.requestPermissions(getActivity(), new String[]{
               android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECORD_AUDIO
        }, PERMISSION_REQUEST_CODE);
    }

    private void speak(){
        //intent to show speech to text dialog
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Give Your Command");
        //start intent
        try{
            //if there was no error
            //show dialog
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch(Exception e){
            //if there was some error
            //get message of error and show
           Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();

        }

    }
    private void pickcontact(){
        Intent contactpickerintent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactpickerintent,REQUEST_CONTACT_PICKER);
    }

    //RECEIVE VOCE INPUT AND HANDLE IT.
    @Override
   /* public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && null != data)
                {
                    //get text array from voice intent
                    ArrayList<String>result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String command= result.get(0).toLowerCase();
                    if(result!=null && result.contains("call to")){
                        String contactname=command.replace("call to","").trim();
                        String Phone_no=getContactNumber(contactname);
                        if(Phone_no!=null){
                            sendemergencySMS(Phone_no);
                        }else {
                            Toast.makeText(getContext(), "Contact Not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(),"Give Commad again",Toast.LENGTH_SHORT).show();
                    }
                    //set to textview
                    textView.setText(result.get(0));
                }
               // break;
            }
        }
    }*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String contactName = results.get(0).toLowerCase();
                findContactAndSendMessage(contactName);
            }
        }
    }
        private void findContactAndSendMessage(String contactName) {
            String contactNumber = getContactNumber(contactName);
            if (contactNumber != null) {
                sendSms(contactNumber, "This is an emergency message.");
            } else {
                Toast.makeText(getActivity(), "Contact not found", Toast.LENGTH_SHORT).show();
            }
        }

        @SuppressLint("Range")
        private String getContactNumber(String contactName) {
            String phoneNumber = null;
            ContentResolver contentResolver = getActivity().getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (displayName != null && displayName.toLowerCase().contains(contactName)) {
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor phoneCursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{contactId},
                                null
                        );

                        if (phoneCursor != null && phoneCursor.moveToFirst()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneCursor.close();
                        }
                        break;
                    }
                }
                cursor.close();
            }
            return phoneNumber;
        }

        private void sendSms(String phoneNumber, String message) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getActivity(), "Emergency message sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
        }
   // @SuppressLint("Range")
    /*private void retriveContactNumber(Uri contactUri){
        String id;
        Cursor cursor=requireActivity().getContentResolver().query(contactUri,null,null,null,null);
        if(cursor!=null && cursor.moveToFirst()){
            id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phonecursor=requireActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",new String[]{id},null);
            if(phonecursor!=null && phonecursor.moveToFirst()){
                phoneno=phonecursor.getString(phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phonecursor.close();
                sendemergencySMS();
            }
            cursor.close();
        }
    }*/




}