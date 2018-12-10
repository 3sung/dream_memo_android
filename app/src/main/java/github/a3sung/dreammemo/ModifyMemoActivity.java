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

public class ModifyMemoActivity extends AppCompatActivity {

    EditText title;
    EditText keywords;
    EditText context;
    String id;
    private ContactDBHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_memo);

        title = (EditText) findViewById(R.id.local_memo_title_text);
        keywords = (EditText) findViewById(R.id.local_memo_keyword_text);
        context = (EditText) findViewById(R.id.local_memo_content_text);
        Intent getInfo = getIntent();
        Button cancel = (Button) findViewById(R.id.local_memo_cancel);
        Button save = (Button) findViewById(R.id.local_memo_save);

        id = getInfo.getStringExtra("id");
        title.setText(getInfo.getStringExtra("title"));
        keywords.setText(getInfo.getStringExtra("keywords"));
        context.setText(getInfo.getStringExtra("context"));
        dbHelper = new ContactDBHelper(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goList = new Intent(ModifyMemoActivity.this, ViewMemoActivity.class);
                goList.putExtra("id", id);
                startActivity(goList);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteItem(getApplicationContext(), id);
                Intent goList = new Intent(ModifyMemoActivity.this, MyDreamPostListActivity.class);
                EditText title = (EditText)findViewById(R.id.local_memo_title_text);
                EditText context = (EditText)findViewById(R.id.local_memo_content_text);
                EditText keywords = (EditText)findViewById(R.id.local_memo_keyword_text);

                Long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy.MM.dd");
                String publishDate = simpleformat.format(date);

                dbHelper.insert(title.getText().toString(), context.getText().toString(), publishDate, keywords.getText().toString());
                Toast.makeText(getApplication(), "수정 완료!", Toast.LENGTH_SHORT).show();
                startActivity(goList);
            }
        });
    }
}
