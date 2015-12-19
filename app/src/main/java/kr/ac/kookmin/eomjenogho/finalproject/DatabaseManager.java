package kr.ac.kookmin.eomjenogho.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseManager extends SQLiteOpenHelper{
    public DatabaseManager(Context context, String name, CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // table name : logTable
        String sql = "create table logTable (id integer primary key autoincrement, " +
                "tag text not null, story text not null, lat real, lng real);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void remove(SQLiteDatabase db)
    {
        String sql = "drop table logTable";
        db.execSQL(sql);
    }
}
