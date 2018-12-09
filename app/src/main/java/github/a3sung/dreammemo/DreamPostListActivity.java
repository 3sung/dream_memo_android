package github.a3sung.dreammemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class DreamPostListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    private ListView boardList;
    private String boardID;
    private String UserID;
    private String DreamContent;
    private String CommentContent;
    private String Time;
    private String Title;
    private List<BoardDream> boardDreams;
    private List<BoardDream> saveList;
    private BoardDreamAdapter adapter;


    private Handler boardSuccessHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Toast.makeText(getApplicationContext(), "게시글 가져오기 성공", Toast.LENGTH_LONG).show();

        }
    };

    private Handler boardFailHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "게시글 가져오기 실패", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_post_list);
        boardDreams = new ArrayList<BoardDream>();
        saveList = new ArrayList<BoardDream>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new Navigation());



        ServerConnector svConn = ServerConnector.getInstatnce();
        svConn.requestGet(ServerConnector.BASE_URL + "board", new RequestCallback() {
            @Override
            public void requestCallback(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0; i<jsonArray.length();i++) {
                        boardID = jsonArray.getJSONObject(i).getString("ID");
                        UserID = jsonArray.getJSONObject(i).getString("UserID");
                        Title = jsonArray.getJSONObject(i).getString("Title");
                        DreamContent = jsonArray.getJSONObject(i).getString("DreamContent");
                        CommentContent = jsonArray.getJSONObject(i).getString("CommentContent");
                        Time = jsonArray.getJSONObject(i).getString("Time");
                        Time = Time.substring(0,10);
                        BoardDream boardDream = new BoardDream(boardID,UserID,Title, DreamContent,  CommentContent,  Time);
                        boardDreams.add(boardDream);
                        saveList.add(boardDream);
                    }
                    adapter = new BoardDreamAdapter(getApplicationContext(),boardDreams,saveList);
                    boardList.setAdapter(adapter);

                    boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Intent intent = new Intent(DreamPostListActivity.this,ViewDreamPostActivity.class);
                                        intent.putExtra("Title", boardDreams.get(position).getTitle());
                                        intent.putExtra("UserID", boardDreams.get(position).getUserID());
                                        intent.putExtra("DreamContent", boardDreams.get(position).getDreamContent());
                                        intent.putExtra("CommentContent", boardDreams.get(position).getCommentContent());
                                        intent.putExtra("Time", boardDreams.get(position).getTime());
                                        DreamPostListActivity.this.startActivity(intent);






                        }
                    });

                    Message msg = boardSuccessHandler.obtainMessage();
                    boardSuccessHandler.sendMessage(msg);
                } catch (JSONException e) {

                    e.printStackTrace();
                    // TODO: (중요도1) 로그인 실패 시 로그인 제한 되게 하기

                    Message msg = boardFailHandler.obtainMessage();
                    boardFailHandler.sendMessage(msg);

                }
            }
        });
        boardList =(ListView)findViewById(R.id.boardList);
        EditText searchKeyword = (EditText) findViewById(R.id.searchKeyword);
        searchKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchKeyword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

        
    
    public void searchKeyword(String search) {
        boardDreams.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if (saveList.get(i).getDreamContent().contains(search))
                boardDreams.add(saveList.get(i));
        }
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
