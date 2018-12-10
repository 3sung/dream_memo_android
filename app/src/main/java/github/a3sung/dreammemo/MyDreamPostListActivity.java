package github.a3sung.dreammemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDreamPostListActivity extends AppCompatActivity {
    private ArrayList<String> lists;
    private ContactDBHelper dbHelper;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button localMemoWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dream_post_list);
        dbHelper = new ContactDBHelper(getApplicationContext());
        lists = dbHelper.getResult();
        listView = (ListView) findViewById(R.id.local_memo_lists);
        localMemoWrite = (Button)findViewById(R.id.local_memo_write);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists);
        listView.setAdapter(adapter);

        localMemoWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDreamPostListActivity.this, WriteMemoActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyDreamPostListActivity.this, ViewMemoActivity.class);
                String[] temp = lists.get(position).split(" ");
                intent.putExtra("id", temp[0]);
                startActivity(intent);
            }
        });
    }
}
