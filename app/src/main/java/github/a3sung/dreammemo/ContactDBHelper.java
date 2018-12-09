package github.a3sung.dreammemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
                + "context TEXT"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIntoLocal(String title, String context){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO LOCAL_MEMO VALUES(null, '" + title +"', '" + context + "');");
        db.close();
    }
}
