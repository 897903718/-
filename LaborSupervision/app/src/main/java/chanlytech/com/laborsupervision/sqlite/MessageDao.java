package chanlytech.com.laborsupervision.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import chanlytech.com.laborsupervision.entiy.MessageEntity;

/**
 * Created by Lyy on 2015/9/11.
 */
public class MessageDao {
    private MyDBHelper myDBHelper;
    private SQLiteDatabase mDatabase;
    private Cursor mCursor;
    private int mId;

    public MessageDao(Context context) {
        super();
        myDBHelper = new MyDBHelper(context);

    }

    /**
     * 插入数据
     */
    public void insertData(MessageEntity messageEntity) {
        mDatabase = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", messageEntity.getTitle());
        values.put("time", messageEntity.getTime());
        values.put("imageurl", messageEntity.getImageUrl());
        values.put("content", messageEntity.getDescription());
        mDatabase.insert("message", null, values);
        mDatabase.close();


    }

    /**
     * 查询message表中的所有数据
     */
    public List<MessageEntity> selectData(MessageEntity messageEntity) {
        mDatabase = myDBHelper.getWritableDatabase();
        mCursor = mDatabase.query("message", new String[]{"_id", "name", "time", "imageurl", "content"}, null, null, null, null, "_id desc");
        List<MessageEntity> messageEntities = new ArrayList<MessageEntity>();
        while (mCursor.moveToNext()) {
            mId = mCursor.getInt(mCursor.getColumnIndexOrThrow("_id"));
            String name = mCursor.getString(mCursor.getColumnIndexOrThrow("name"));
            String time = mCursor.getString(mCursor.getColumnIndexOrThrow("time"));
            String imageurl = mCursor.getString(mCursor.getColumnIndexOrThrow("imageurl"));
            String content = mCursor.getString(mCursor.getColumnIndexOrThrow("content"));
            messageEntity = new MessageEntity(mId + "", name, content, imageurl, time);
            messageEntities.add(messageEntity);
        }
        mCursor.close();
        mDatabase.close();
        return messageEntities;
    }

    /**
     * 删除单条数据
     */
    public void deleteData(MessageEntity messageEntity) {
        mDatabase = myDBHelper.getWritableDatabase();
        mDatabase.delete("message", "_id=?", new String[]{messageEntity.getId() + ""});
        mDatabase.close();
    }

    /**
     * 删除所有数据
     */
    public void deleteAllData() {
        mDatabase = myDBHelper.getWritableDatabase();
        mDatabase.delete("message", null, null);
        mDatabase.close();
    }


    /**
     * 插入数据
     */
    public void insertData1(MessageEntity messageEntity) {
        mDatabase = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", messageEntity.getTitle());
        values.put("time", messageEntity.getTime());
        values.put("imageurl", messageEntity.getImageUrl());
        values.put("content", messageEntity.getDescription());
        values.put("type", messageEntity.getType());
        values.put("objid", messageEntity.getObjId());
        values.put("url", messageEntity.getUrl());
        mDatabase.insert("message", null, values);
        mDatabase.close();

    }

    /**
     * 查询message表中的所有数据
     */
    public List<MessageEntity> selectData1(MessageEntity messageEntity) {
        mDatabase = myDBHelper.getWritableDatabase();
        mCursor = mDatabase.query("message", null, null, null, null, null, null);
        List<MessageEntity> messageEntities = new ArrayList<MessageEntity>();
        while (mCursor.moveToNext()) {
            mId = mCursor.getInt(mCursor.getColumnIndexOrThrow("_id"));
            String name = mCursor.getString(mCursor.getColumnIndexOrThrow("name"));
            String time = mCursor.getString(mCursor.getColumnIndexOrThrow("time"));
            String imageurl = mCursor.getString(mCursor.getColumnIndexOrThrow("imageurl"));
            String content = mCursor.getString(mCursor.getColumnIndexOrThrow("content"));
            int type = mCursor.getInt(mCursor.getColumnIndexOrThrow("type"));
            String objid = mCursor.getString(mCursor.getColumnIndexOrThrow("objid"));
            String url = mCursor.getString(mCursor.getColumnIndexOrThrow("url"));
            messageEntity = new MessageEntity(mId + "", name, content, imageurl, time, type, objid, url);
            messageEntities.add(messageEntity);
        }
        mCursor.close();
        mDatabase.close();
        return messageEntities;
    }

    /**
     * 修改message表中某一条数据
     */
    public void modifyData(MessageEntity messageEntity) {
        ContentValues values = new ContentValues();
        mDatabase = myDBHelper.getWritableDatabase();
        values.put("imageurl", "1");
        String whereClause = "time=?";
        String[] whereArgs = new String[]{messageEntity.getTime()};
        mDatabase.update("message", values, whereClause, whereArgs);
        mDatabase.close();
    }


    /**
     * 根据表名获取该表在数据库中所有的列名
     */
    public ArrayList<String> getList(String tablename) {
        ArrayList<String> list = new ArrayList<>();
        mDatabase = myDBHelper.getWritableDatabase();
        mCursor = mDatabase.query(tablename, null, null, null, null, null, null);
        int col = mCursor.getColumnCount();
        for (int i = 0; i < col; i++) {
            String colName = mCursor.getColumnName(i);
            list.add(colName);
        }
        return list;
    }

    /**
     * 万能插入
     */
    public void insterIoto(Object ob) {
        Class cl = ob.getClass();
        //根据对象获取该对象的属性
        Field[] fields = cl.getDeclaredFields();
        mDatabase = myDBHelper.getWritableDatabase();
        //获取所有的列名
        ArrayList<String> col = getList(cl.getSimpleName());
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < col.size(); i++) {
                for (Field field : fields) {
                    if (col.get(i).equals(field.getName())) {
                        field.setAccessible(true);
                        //判断数据类型
                        if (field.getType().toString().equals("class java.lang.String")) {//String类型
                            values.put(field.getName(), String.valueOf(field.get(ob)));
                        } else if (field.getType().toString().equals("int")) {//int 类型
                            values.put(field.getName(), Integer.valueOf(field.get(ob).toString()));
                        } else if (field.getType().toString().equals("class java.lang.Float")) {//Float
                            values.put(field.getName(), Float.valueOf(field.get(ob).toString()));
                        } else if (field.getType().toString().equals("class java.lang.Double")) {//double
                            values.put(field.getName(), Double.valueOf(field.get(ob).toString()));
                        }
                    }
                }
            }
            mDatabase.insert(cl.getSimpleName(), null, values);
            mDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
