package github.a3sung.dreammemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import github.a3sung.dreammemo.account.AccountController;

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
        final String[] selected_context = dbHelper.getSelectedItem(selected_id);
        title = (TextView) findViewById(R.id.local_memo_view_title);
        keywords = (TextView) findViewById(R.id.local_memo_view_keywords);
        contexts = (TextView) findViewById(R.id.local_memo_view_context);
        Button delete = (Button) findViewById(R.id.local_memo_delete);
        Button update = (Button) findViewById(R.id.local_memo_update);
        Button share = (Button) findViewById(R.id.local_memo_share);
        Button analysis = (Button) findViewById(R.id.local_memo_analysis);

        title.setText(selected_context[0]);
        keywords.setText(selected_context[1]);
        contexts.setText(selected_context[2]);

        analysis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String rawKeywordString = keywords.getText().toString();
                Log.d("DreamTag", rawKeywordString);
                String[] rawKeywords = rawKeywordString.split("#");
                JSONArray keywords = new JSONArray();
                for (String rawKeyword : rawKeywords){
                    rawKeyword.replaceAll(" ","").replaceAll(",", "");
                    if (!rawKeyword.isEmpty()){
                        keywords.put(rawKeyword);
                    }

                }
                Log.d("DreamTag", keywords.toString());
                Intent toAnalysis = new Intent(ViewMemoActivity.this, DreamAnalysisActivity.class);
                toAnalysis.putExtra("tags", keywords.toString());
                startActivity(toAnalysis);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteItem(getApplicationContext(), selected_id);
                Intent gotoList = new Intent(ViewMemoActivity.this, MemoListActivity.class);
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeShare = new Intent(ViewMemoActivity.this, WriteDreamPostActivity.class);
                writeShare.putExtra("id", selected_id);
                writeShare.putExtra("title", title.getText().toString());
                writeShare.putExtra("keywords", keywords.getText().toString());
                writeShare.putExtra("context", contexts.getText().toString());
                startActivity(writeShare);
            }
        });
    }
}
