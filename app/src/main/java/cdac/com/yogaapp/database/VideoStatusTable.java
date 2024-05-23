package cdac.com.yogaapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by vinod on 4/15/2019.
 */

public class VideoStatusTable {

    public static final String TABLE_NAME = "videoStatus";
    public static final String MESAAGE_ID = "id";
    public static final String STATUS = "status";
    public static final String FILENAME = "name";
    public static final String URL = "url";
    public static final String LANGUAGE = "lang";

    private static final String createTableQuery = "CREATE TABLE `videoStatus` (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`name`\tTEXT,\n" +
            "\t`url`\tTEXT,\n" +
            "\t`lang`\tTEXT,\n" +
            "\t`status`\tTEXT\n" +
            ");";


    public static void onCreate(SQLiteDatabase db){

        try{
            db.execSQL(createTableQuery);
        }
        catch(Exception e)
        {
            Log.d("1234", "create table exception " + e);
        }

    }

    public static void onUpdate(SQLiteDatabase db){
        try{
            String query = "drop table if exists "+TABLE_NAME;
            db.execSQL(query);
            onCreate(db);
        }
        catch(Exception e)
        {
            Log.d("1234", "update table exception "+e);
        }

    }

    public static long insert(SQLiteDatabase db, String fileName, String url, String status, String language)
    {

        ContentValues values = new ContentValues();
        values.put(FILENAME, fileName);
        values.put(URL, url);
        values.put(LANGUAGE, language);
        values.put(STATUS, status);


        long id = -1;
        try {
            // insert(TableName, NullColumn, ContentValues);
            id = db.insert(TABLE_NAME, null, values);

        }
        catch(Exception e)
        {
            Log.d("1234", "exception in insert "+e);
        }

        return id;
    }

    public static Cursor select(SQLiteDatabase db, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy, String limit)
    {

        Cursor cursor = null;
        try{
            cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, groupBy, having, orderBy);
        }
        catch(Exception e)
        {
            Log.d("1234", "exception in select "+e);
        }
        return cursor;
    }

    public static long update(SQLiteDatabase db, ContentValues cv, String whereClause)
    {
        return  db.update(TABLE_NAME, cv, whereClause, null);
    }

    public static long delete(SQLiteDatabase db)
    {
        return db.delete(TABLE_NAME, null, null);
    }


}

