package github.a3sung.dreammemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyDreamPostListActivity extends AppCompatActivity {
    private ArrayList<String> lists;
    private ContactDBHelper dbHelper;
    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dream_post_list);
        dbHelper = new ContactDBHelper(getApplicationContext());
        lists = dbHelper.getResult();
        //adapter = new ArrayAdapter(this, R.layout.activity_my_dream_post_list, lists);
        //listView.setAdapter(adapter);
    }
}
