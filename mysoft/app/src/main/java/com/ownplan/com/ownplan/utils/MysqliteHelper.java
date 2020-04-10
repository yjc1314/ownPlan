package com.ownplan.com.ownplan.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MysqliteHelper extends SQLiteOpenHelper {


    public MysqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public MysqliteHelper(Context context)
    {
        super(context, Sqlconstants.DATABASE_NAME,null, Sqlconstants.DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //这里创建有问题
        String sql ="create table "+Sqlconstants.TABLE_PLAN+"("+Sqlconstants.ID+" Integer primary key autoincrement,"+Sqlconstants.TIME+" verchar(20),"+Sqlconstants.DO_WHAT+" verchar(100))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
