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
                + "date TEXT, "
                + "keywords TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String title, String context, String date, String keywords){
        SQLiteDatabase db=getWritableDatabase();
        String sqlInsert = "INSERT INTO LOCAL_MEMO(title, context, date, keywords) VALUES('" + title + "', '" + context +  "', '" + date + "', '" + keywords.replace(" ", "").replace(",", "#") + "');";
        db.execSQL(sqlInsert);
        db.close();
    }

    public ArrayList<BoardDream> getResult(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<BoardDream> result = new ArrayList<BoardDream>();

        Cursor cursor = db.rawQuery("SELECT * FROM LOCAL_MEMO", null);
        if(!cursor.moveToFirst()){
            return result;
        }
        do{
            BoardDream temp = new BoardDream(cursor.getString(4).replace("#", ","), cursor.getString(1), cursor.getString(3).substring(2));
            result.add(temp);
        }while (cursor.moveToNext());
        return result;
    }

    public ArrayList<String> getIdList(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> result = new ArrayList<String>();

        Cursor cursor = db.rawQuery("SELECT * FROM LOCAL_MEMO", null);
        if(!cursor.moveToFirst()){
            return result;
        }
        do{
            result.add(cursor.getString(0));
        }while (cursor.moveToNext());
        return result;
    }

    public String[] getSelectedItem(String id){
        String result[] = new String[3];
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM LOCAL_MEMO WHERE ID = " + id, null);
        cursor.moveToFirst();
        result[0] = cursor.getString(1);
        result[1] = cursor.getString(4);
        result[2] = cursor.getString(2);
        return result;
    }

    public void deleteItem(Context context, String id){
        SQLiteDatabase db=getWritableDatabase();
        String sqlDelete = "DELETE FROM LOCAL_MEMO WHERE ID = " + id + ";";
        db.execSQL(sqlDelete);
        Toast.makeText(context, "삭제 완료!", Toast.LENGTH_SHORT).show();
    }
}
