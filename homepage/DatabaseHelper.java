package com.example.homepage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String dbname="smartSQ.db";
    private static final String username="name";
    private static final String usermo="mobile";
    private static final String useremail="email";
    private static final String userhome="home";
    private static final String userwork="work";
    private static final String uname="name";
    private static final String password="pass";
    private static final String ufeed="userfeed";




    public DatabaseHelper(FragmentActivity context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String qry="create table userdetail(id integer primary key autoincrement,name text,mobile TEXT,email text,home text,work text)";
        db.execSQL(qry);
        String qry1="create table login(id integer primary key autoincrement,name text,pass text)";
        db.execSQL(qry1);
        String query ="create table feedbacktable(id integer primary key autoincrement,userfeed text)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS userdetail");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS login");
        onCreate(db);

        db.execSQL("drop table if exists feedbacktable");
        onCreate(db);
    }
    public boolean insertData(String name, String mobile,String email,String home,String work){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(username,name);
        contentValues.put(usermo,mobile);
        contentValues.put(useremail,email);
        contentValues.put(userhome,home);
        contentValues.put(userwork,work);

        long result=db.insert("userdetail",null,contentValues);
        return result !=-1;


    }
    public boolean logindata(String name, String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(uname,name);
        contentValues.put(password,pass);


        long result=db.insert("login",null,contentValues);
        return result !=-1;


    }
    public boolean authoUser(String name,String pass){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from login where name=? and pass=?",new String[]{name,pass});
        boolean result=cursor.getCount()>0;
        cursor.close();
        return result;
    }



    public boolean feed(String userfeed) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ufeed,userfeed);

        long result=db.insert("feedbacktable",null,contentValues);
        return result !=-1;

    }
}
