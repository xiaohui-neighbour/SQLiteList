package com.example.sqlitelist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="MyRelation.db";
    private static final String TABLE_NAME="relation";
    private static final String CREATE_TABLE="create table relation(" +
            "_id integer primary key autoincrement,name text,tel text,groupName text)";
    private SQLiteDatabase db;
    public DatabaseHelper(Context context)
    {
        super(context,DB_NAME,null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public void insert(ContentValues values){
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void del(int id){
        if(null==db)
            db=getWritableDatabase();
        db.delete(TABLE_NAME,"_id=?",new String[]{String.valueOf(id)});
    }
    public Cursor query(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }
    public void close()
    {
        if(db!=null)
            db.close();
    }

}
