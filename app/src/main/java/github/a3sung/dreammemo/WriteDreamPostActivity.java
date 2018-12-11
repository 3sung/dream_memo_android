package github.a3sung.dreammemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Time;

import github.a3sung.dreammemo.account.AccountController;
import github.a3sung.dreammemo.serverconnector.ErrorCallback;
import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class WriteDreamPostActivity extends AppCompatActivity {
    EditText title;
    EditText keywords;
    EditText comment;
    EditText context;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_dream_post);
        Intent getInfo = getIntent();
        title = (EditText) findViewById(R.id.local_share_title_text);
        comment = (EditText) findViewById(R.id.local_share_comment_text);
        keywords = (EditText) findViewById(R.id.local_share_keyword_text);
        context = (EditText) findViewById(R.id.local_share_context_text);
        Button share = (Button) findViewById(R.id.local_share_share);
        Button cancel = (Button) findViewById(R.id.local_share_cancel);

        id = getInfo.getStringExtra("id");
        title.setText(getInfo.getStringExtra("title"));
        keywords.setText(getInfo.getStringExtra("keywords"));
        context.setText(getInfo.getStringExtra("context"));

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountController userController = AccountController.getInstance();
                ServerConnector contextSender = ServerConnector.getInstatnce();
                ServerConnector keywordSender = ServerConnector.getInstatnce();
                String token = userController.getAccountToken();

                contextSender.requestPostWithAuthorizeToken(ServerConnector.BASE_URL + "board", token, String.format("title=%s&dreamContent=%s&commentContent=%s", title.getText().toString(), title.getText().toString(), title.getText().toString()), new RequestCallback(){

                    @Override
                    public void requestCallback(String result) {
                        Log.d("Account", "Sign Up success!");
                        Message msg = shareSuccess.obtainMessage();
                        shareSuccess.sendMessage(msg);
                    }
                }, new ErrorCallback() {
                    @Override
                    public void errCallback(int resultCode) {
                        Log.d("TAG", "err code" + resultCode);
                        Message msg = shareFail.obtainMessage();
                        shareFail.sendMessage(msg);
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goView = new Intent(WriteDreamPostActivity.this, ViewMemoActivity.class);
                goView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goView.putExtra("id", id);
                startActivity(goView);
            }
        });
    }

    private Handler shareSuccess = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "공유 완료!", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private Handler shareFail = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "공유 실패!", Toast.LENGTH_SHORT).show();
            finish();
        }
    };
}
