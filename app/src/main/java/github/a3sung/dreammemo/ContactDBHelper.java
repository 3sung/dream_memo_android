package github.a3sung.dreammemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DBFILE_CONTACT = "localMemo.db";

    public ContactDBHelper (Context context){
        super(context, DBFILE_CONTACT, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS LOCAL_MEMO("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT, "
                + "context TEXT, "
                + "date TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String title, String context, String date){
        SQLiteDatabase db=getWritableDatabase();
        String sqlInsert = "INSERT INTO LOCAL_MEMO(title, context, date) VALUES('" + title + "', '" + context +  "', '" + date + "');";
        db.execSQL(sqlInsert);
        db.close();
    }

    public ArrayList<String> getResult(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> result = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT * FROM LOCAL_MEMO", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String temp =  cursor.getString(0)
                    + "  "
                    + cursor.getString(1)
                    + "   "
                    + cursor.getString(3);
            result.add(temp);
        }
        return result;
    }
}
