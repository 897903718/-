package chanlytech.com.laborsupervision.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.arialyy.frame.sqlite.SQLiteDbHelper;

/**
 * Created by Lyy on 2015/9/11.
 */
public class MyDBHelper extends SQLiteDbHelper {
    private String CREATE_TABLE = "create table message( _id integer primary key,name varchar,time varchar,imageurl varchar,content varchar,type integer,objid varchar,url varchar)";
//    private String CREATE_TEMP_TABLE = "alter table message rename to _temp_message";
//    private String INSERT_DATA = "insert into message select *,'111' from _temp_message";//（注意' '是为新加的字段插入默认值的必须加上，否则就会出错）。
//    private String DROP_TABLE = "drop table _temp_message";

    public MyDBHelper(Context context) {
        super(context, "laodongshebao.db", 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
//        db.execSQL(CREATE_TEMP_TABLE);
//        db.execSQL(CREATE_TABLE);
//        db.execSQL(INSERT_DATA);
//        db.execSQL(DROP_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS message");
//        onCreate(db);

    }
}
