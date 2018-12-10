package github.a3sung.dreammemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class WriteMemoActivity extends AppCompatActivity {
    private Button cancel;
    private Button save;
    private ContactDBHelper dbHelper = null;

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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoList();
            }
        });
    }

    private void init_tables(){
        dbHelper = new ContactDBHelper(this);
    }

    private void save_values(){
        EditText title = (EditText)findViewById(R.id.local_memo_title_text);
        EditText context = (EditText)findViewById(R.id.local_memo_content_text);
        EditText keywords = (EditText) findViewById(R.id.local_memo_keyword_text);

        Long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy.MM.dd");
        String publishDate = simpleformat.format(date);

        dbHelper.insert(title.getText().toString(), context.getText().toString(), publishDate, keywords.getText().toString());
        Toast.makeText(getApplication(), "저장 완료!", Toast.LENGTH_SHORT).show();
        gotoList();
    }

    private void gotoList(){
        Intent intent = new Intent(WriteMemoActivity.this, MemoListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
