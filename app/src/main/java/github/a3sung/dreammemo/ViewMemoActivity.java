package github.a3sung.dreammemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewMemoActivity extends AppCompatActivity {

    String selected_id;
    ContactDBHelper dbHelper;
    TextView title;
    TextView keywords;
    TextView contexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memo);

        Intent intent = getIntent();
        dbHelper = new ContactDBHelper(getApplicationContext());
        selected_id = intent.getStringExtra("id");
        String[] selected_context = dbHelper.getSelectedItem(selected_id);
        title = (TextView) findViewById(R.id.local_memo_view_title);
        keywords = (TextView) findViewById(R.id.local_memo_view_keywords);
        contexts = (TextView) findViewById(R.id.local_memo_view_context);
        Button delete = (Button) findViewById(R.id.local_memo_delete);
        Button update = (Button) findViewById(R.id.local_memo_update);

        title.setText(selected_context[0]);
        keywords.setText(selected_context[1]);
        contexts.setText(selected_context[2]);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteItem(getApplicationContext(), selected_id);
                Intent gotoList = new Intent(ViewMemoActivity.this, MyDreamPostListActivity.class);
                gotoList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoList);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localMemoModify = new Intent(ViewMemoActivity.this, ModifyMemoActivity.class);
                localMemoModify.putExtra("id", selected_id);
                localMemoModify.putExtra("title", title.getText().toString());
                localMemoModify.putExtra("keywords", keywords.getText().toString());
                localMemoModify.putExtra("context", contexts.getText().toString());
                startActivity(localMemoModify);
            }
        });
    }
}
