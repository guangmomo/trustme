package data;

/**
 * Created by xu on 17-1-10.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 用于创建数据库的类
 * 数据库的结构：
 *
 *
 *
 *
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "trust_me.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (_id integer primary key autoincrement, nickname varchar(30), mobile varchar(30), password varchar(30), school varchar(30), dong varchar(30),lou varchar(30),amount float)");
        db.execSQL("create table dynamic (_id integer primary key autoincrement, sponsor varchar(30), receiver varchar(30), dong varchar(30),lou varchar(30), tailnumber varchar(30), time varchar(30),range integer, state integer, shop varchar(30), money float)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
