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

import java.util.List;

import github.a3sung.dreammemo.serverconnector.ErrorCallback;
import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class ViewCommentActivity extends AppCompatActivity {

    private Button backButton;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getintent = getIntent();
        boardID = getintent.getExtras().getString("boardID");
        UserID = getintent.getExtras().getString("UserID");

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commentText = findViewById(R.id.comment);
        writeButton = findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(commentText.length()>1){
                    ServerConnector svConn = ServerConnector.getInstatnce();
                    svConn.requestPost(ServerConnector.BASE_URL + "board/replies", String.format("boardID=%s&content=%s", boardID, commentText.getText()), new RequestCallback() {
                        @Override
                        public void requestCallback(String result) {
                            Message msg = commentSuccessHandler.obtainMessage();
                            commentSuccessHandler.sendMessage(msg);
                        }
                    }, new ErrorCallback() {
                        @Override
                        public void errCallback(int resultCode) {
                            Message msg = commentFailHandler.obtainMessage();
                            commentFailHandler.sendMessage(msg);

                        }
                    });
                    Toast.makeText(getApplicationContext(), commentText.getText(), Toast.LENGTH_LONG).show();
                    commentText.getText().clear();

                }
                else{
                    Toast.makeText(getApplicationContext(), "두 글자 이상 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });




//        //get comment
//        ServerConnector svConn = ServerConnector.getInstatnce();
//        svConn.requestGet(ServerConnector.BASE_URL + "board/replies", new RequestCallback() {
//            @Override
//            public void requestCallback(String result) {
//                try {
//                    JSONArray jsonArray = new JSONArray(result);
//
//                    for(int i=0; i<jsonArray.length();i++) {
//                        commentID =jsonArray.getJSONObject(i).getString("ID");
//                        boardID = jsonArray.getJSONObject(i).getString("BoardID");
//                        UserID = jsonArray.getJSONObject(i).getString("UserID");
//                        Content = jsonArray.getJSONObject(i).getString("Content");
//
//                        Comment comment = new Comment(commentID,boardID,UserID, Content);
//                        comments.add(comment);
//                        saveList.add(comment);
//                    }
//                    adapter = new CommentAdapter(getApplicationContext(),comments,saveList);
//                    commentList.setAdapter(adapter);
//
//                    commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            //수정 구현해야됨.
////                            Intent intent = new Intent(DreamPostListActivity.this,ViewDreamPostActivity.class);
////                            intent.putExtra("boardID",boardDreams.get(position).getBoardID());
////                            intent.putExtra("Title", boardDreams.get(position).getTitle());
////                            intent.putExtra("UserID", boardDreams.get(position).getUserID());
////                            intent.putExtra("DreamContent", boardDreams.get(position).getDreamContent());
////                            intent.putExtra("CommentContent", boardDreams.get(position).getCommentContent());
////                            intent.putExtra("Time", boardDreams.get(position).getTime());
////                            DreamPostListActivity.this.startActivity(intent);
//
//                        }
//                    });
//
//                    Message msg = commentSuccessHandler.obtainMessage();
//                    commentSuccessHandler.sendMessage(msg);
//                } catch (JSONException e) {
//
//                    e.printStackTrace();
//
//                    Message msg = commentFailHandler.obtainMessage();
//                    commentFailHandler.sendMessage(msg);
//
//                }
//            }
//        });
//        commentList =(ListView)findViewById(R.id.boardList);





    }
    private Handler commentSuccessHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    private Handler commentFailHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



}
