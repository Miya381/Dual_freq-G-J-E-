package com.kubolab.gnss.gnssloggerR;

/**
 * Created by KuboLab on 2018/02/12.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQLiteManager extends SQLiteOpenHelper {
    public Context m_context;
    public static final String TAG = "DBOpenHelper";
    public static final String DB_NAME = "GNSSNavigation";
    static final String CREATE_TABLE = "create table mytable ( _id integer primary key autoincrement, data integer not null );";
    static final String DROP_TABLE = "drop table mytable;";
    public static final int DB_VERSION = 1;

    public SQLiteManager(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        Log.d(TAG, "onCreate version : " + db.getVersion());
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL(DROP_TABLE);
    }

    public double searchIndex(final SQLiteDatabase db, final String column){
        double tmp = 0;
        // テーブルからデータを検索
        Cursor cursor = db.query(
                "sample_table", new String[] {"_id", column},
                null, null, null, null, "_id DESC");
        // 参照先を一番始めに
        boolean isEof = cursor.moveToFirst();
        // データを取得していく
        while(isEof) {
            tmp = cursor.getDouble(cursor.getColumnIndex("data"));
            isEof = cursor.moveToNext();
        }
        // 忘れずに閉じる
        cursor.close();
        return tmp;
    }
}
