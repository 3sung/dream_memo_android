package github.a3sung.dreammemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import github.a3sung.dreammemo.account.Account;
import github.a3sung.dreammemo.account.AccountController;
import github.a3sung.dreammemo.serverconnector.ErrorCallback;
import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class ViewCommentActivity extends AppCompatActivity {

    private Button backButton, refreshButton;
    private Intent getintent;
    private String boardID;
    private String UserID;
    private String commentID;
    private String Content;
    private List<Comment> comments;
    private List<Comment> saveList;
    private CommentAdapter adapter;
    private ListView commentList;
    private EditText commentText;
    private Button writeButton;
    private String commentResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        comments = new ArrayList<Comment>();
        saveList = new ArrayList<Comment>();

        getintent = getIntent();
        boardID = getintent.getExtras().getString("boardID");
        UserID = getintent.getExtras().getString("UserID");


        commentList =findViewById(R.id.DreamComment);
        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commentText = findViewById(R.id.comment);
        refreshButton = findViewById(R.id.btnRefresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadComment();
            }
        });
        writeButton = findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(commentText.length()>1){

                    ServerConnector svConn = ServerConnector.getInstatnce();

                    svConn.requestPostWithAuthorizeToken(ServerConnector.BASE_URL + "board/replies",AccountController.getInstance().getAccountToken(), String.format("boardID=%s&content=%s", boardID, commentText.getText().toString()), new RequestCallback() {
                        @Override
                        public void requestCallback(String result) {

                            Log.d("Replies", "Replies Up success!");
                            Message msg = commentSuccessHandler.obtainMessage();
                            commentSuccessHandler.sendMessage(msg);
                        }
                    }, new ErrorCallback() {
                        @Override
                        public void errCallback(int resultCode) {
                            Log.d("TAG", "err code" + resultCode);
                            Message msg = commentFailHandler.obtainMessage();
                            commentFailHandler.sendMessage(msg);

                        }
                    });

                    commentText.getText().clear();

                }
                else{
                    Toast.makeText(getApplicationContext(), "두 글자 이상 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });




        //get comment
        loadComment();





    }

    private void loadComment(){
        comments = new ArrayList<>();
        saveList = new ArrayList<>();
        adapter = new CommentAdapter(getApplicationContext(),comments,saveList);
        commentList.setAdapter(adapter);
        //get comment
        ServerConnector svConn = ServerConnector.getInstatnce();
        svConn.requestGet(ServerConnector.BASE_URL + "board/replies?boardID="+boardID, new RequestCallback() {
            @Override
            public void requestCallback(String result) {
                    commentResult = result;
                    Message msg = getcommentSuccessHandler.obtainMessage();
                    getcommentSuccessHandler.sendMessage(msg);

            }
        });
    }

    private Handler commentSuccessHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "댓글 등록", Toast.LENGTH_LONG).show();
            loadComment();
        }
    };

    private Handler commentFailHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "댓글 등록 실패", Toast.LENGTH_LONG).show();

            super.handleMessage(msg);
        }
    };
    private Handler getcommentSuccessHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(commentResult);
                for(int i=0; i<jsonArray.length();i++) {
                    commentID =jsonArray.getJSONObject(i).getString("ID");
                    boardID = jsonArray.getJSONObject(i).getString("BoardID");
                    UserID = jsonArray.getJSONObject(i).getString("UserID");
                    Content = jsonArray.getJSONObject(i).getString("Content");

                    Comment comment = new Comment(commentID,boardID,UserID, Content);
                    comments.add(comment);
                    saveList.add(comment);
                }
                adapter = new CommentAdapter(getApplicationContext(),comments,saveList);
                commentList.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };

    private Handler getcommentFailHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "댓글 가져오기 실패", Toast.LENGTH_LONG).show();

            super.handleMessage(msg);
        }
    };



}
