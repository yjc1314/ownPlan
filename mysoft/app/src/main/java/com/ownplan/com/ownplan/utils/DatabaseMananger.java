package com.ownplan.com.ownplan.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseMananger {
    private static MysqliteHelper Dbhelper;
    public static MysqliteHelper getIntance(Context context)
    {
        if( Dbhelper == null)
        {
            Dbhelper = new MysqliteHelper(context);
        }
        return Dbhelper;


    }
    //插入操作
    public static void DBinsert(SQLiteDatabase db ,String sql) {
        if(db != null) {
            db.execSQL(sql);
            db.close();
        }
    }
    //查找操作
    public static Cursor DBselect(SQLiteDatabase db ,String sql ,String [] strings) {

        if(db != null) {
            Cursor cursor = db.rawQuery(sql,strings);

            return cursor;

        }
        return null;
    }
    //删除元素。
    public static void DBdelete(SQLiteDatabase db ,String sql)
    {
        if(db != null) {

            db.execSQL(sql);
            db.close();
        }
    }
}
