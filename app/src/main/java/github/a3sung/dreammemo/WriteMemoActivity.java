package github.a3sung.dreammemo;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WriteMemoActivity extends AppCompatActivity {
    private Button cancel;
    private Button save;
    ContactDBHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_memo);

        cancel = (Button)findViewById(R.id.local_memo_cancel);
        save = (Button)findViewById(R.id.local_memo_save);
        init_tables();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_values();
            }
        });
    }

    private void init_tables(){
        dbHelper = new ContactDBHelper(this);
    }

    private void save_values(){
        EditText title = (EditText)findViewById(R.id.local_memo_title_text);
        EditText keyword = (EditText)findViewById(R.id.local_memo_keyword_text);
        EditText context = (EditText)findViewById(R.id.local_memo_content_text);

        dbHelper.insertIntoLocal(title.toString(), context.toString());
        Toast.makeText(getApplication(), "저장 완료!", Toast.LENGTH_SHORT);
    }
}
