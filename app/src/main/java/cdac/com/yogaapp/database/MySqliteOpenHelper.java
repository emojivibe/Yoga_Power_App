package cdac.com.yogaapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo1 on 7/25/2016.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static String databaseName = "yogapower.db";
    private static int databaseVersion = 1;



    public MySqliteOpenHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        VideoStatusTable.onCreate(db);
        LanguageTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LanguageTable.onCreate(db);
        VideoStatusTable.onUpdate(db);
    }
}
