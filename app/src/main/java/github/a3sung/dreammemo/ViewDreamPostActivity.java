package github.a3sung.dreammemo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewDreamPostActivity extends AppCompatActivity  {
    private Intent getintent;
    private String boardID;
    private String UserID;
    private String DreamContent;
    private String CommentContent;
    private String Time;
    private String Title;
    private TextView titleboard;
    private TextView commentContent;
    private TextView DreamComment;
    private Button ShowComment;
    private Button chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dream_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getintent = getIntent();
        Title = getintent.getExtras().getString("Title");
        UserID = getintent.getExtras().getString("UserID");
        DreamContent = getintent.getExtras().getString("DreamContent");
        CommentContent = getintent.getExtras().getString("CommentContent");
        boardID = getintent.getExtras().getString("boardID");
        titleboard = (TextView)findViewById(R.id.titleboard);
        commentContent=(TextView)findViewById(R.id.commentContent);
        DreamComment=(TextView)findViewById(R.id.DreamComment);
        ShowComment = findViewById(R.id.ShowComment);
        ShowComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDreamPostActivity.this,ViewCommentActivity.class);
                intent.putExtra("boardID",boardID);
                intent.putExtra("UserID",UserID);
                startActivity(intent);

            }
        });
        chart = findViewById(R.id.chart);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        titleboard.setText(Title);
        commentContent.setText(CommentContent);
        DreamComment.setText(DreamContent);


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

}