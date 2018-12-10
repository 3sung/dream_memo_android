package github.a3sung.dreammemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDreamPostListActivity extends AppCompatActivity {
    private ArrayList<String> lists;
    private ArrayList<String> listIds;
    private ContactDBHelper dbHelper;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button localMemoWrite;
    private EditText editSearch;
    private ArrayList<String> lists_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dream_post_list);
        dbHelper = new ContactDBHelper(getApplicationContext());
        lists = dbHelper.getResult();
        listIds = dbHelper.getIdList();
        listView = (ListView) findViewById(R.id.local_memo_lists);
        localMemoWrite = (Button)findViewById(R.id.local_memo_write);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists);
        listView.setAdapter(adapter);
        editSearch = (EditText) findViewById(R.id.keyword_search);
        lists_copy = new ArrayList<String>();
        lists_copy.addAll(lists);


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
                intent.putExtra("id", listIds.get(position));
                startActivity(intent);
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });

    }

    public void search(String searchText) {
        lists.clear();

        // if searchText input is null, show all list
        if(searchText.length() == 0) {
            lists.addAll(lists_copy);
        }

        else {
            for(int i=0; i<lists_copy.size(); i++) {
                String[] temp = lists_copy.get(i).split(" ");
                if (temp[0].contains(searchText)) {
                    Log.d("TEST", "editText: " + searchText);
                    Log.d("TEST", "lists(0): " + lists_copy.get(0));
                    lists.add(lists_copy.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

}
