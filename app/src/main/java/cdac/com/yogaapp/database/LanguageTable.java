package cdac.com.yogaapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by vinod on 4/16/2019.
 */

public class LanguageTable {

    public static final String TABLE_NAME = "language";
    public static final String MESAAGE_ID = "id";
    public static final String LANGUAGE = "language";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String VALUE = "value";

    private static final String createTableQuery = "CREATE TABLE `language` (\n"+
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n"+
            "\t`language`\tTEXT,\n"+
            "\t`type`\tTEXT,\n"+
            "\t`name`\tTEXT,\n"+
            "\t`value`\tTEXT\n"+
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

    public static long insert(SQLiteDatabase db, String language, String type, String name, String value)
    {

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(LANGUAGE, language);
        values.put(VALUE, value);
        values.put(TYPE, type);


        long id = -1;
        try {
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



