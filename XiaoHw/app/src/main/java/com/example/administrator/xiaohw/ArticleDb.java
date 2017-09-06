package com.example.administrator.xiaohw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xfeng on 16/7/29.
 */
public class ArticleDb extends SQLiteOpenHelper {
    public ArticleDb(Context context) {
        super(context, "article_table", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
            post_date   :提交的时间
            post_content:提交的内容
            post_title  :提交的标题
         */
        db.execSQL("CREATE TABLE article_table(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "post_date TEXT DEFAULT \"\","+
                "post_content TEXT DEFAULT \"\","+
                "post_title TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
