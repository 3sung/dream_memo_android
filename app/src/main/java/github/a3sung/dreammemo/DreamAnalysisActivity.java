package github.a3sung.dreammemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class DreamAnalysisActivity extends AppCompatActivity {

    private TextView labelContent;
    private TextView labelTag;

    private LinkedList<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_analysis);

        tags = new LinkedList<>();
        labelContent = findViewById(R.id.labelContent);
        labelTag = findViewById(R.id.labelTag);
        labelContent.setText("");
        labelTag.setText("");

        // init tag text
        Intent fromIntent = getIntent();
        try {
            if (fromIntent.getExtras() != null){
                String tagStr = fromIntent.getExtras().getString("tags");
                if (tagStr != null){
                    JSONArray tagObj = new JSONArray(tagStr);
                    for (int i = 0; i < tagObj.length(); i++){
                        tags.add(tagObj.getString(i));
                        labelTag.append(tagObj.getString(i));
                        if (i != tagObj.length()-1){
                            labelTag.append(", ");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        labelContent.setText("해몽 데이터를 가져오는 중입니다.");

        JSONArray jArr = new JSONArray();
        for (String tag : tags){
            jArr.put(tag);
        }

        ServerConnector svConn = ServerConnector.getInstatnce();
        svConn.requestGet(ServerConnector.BASE_URL + "dream_analysis?tags=" + jArr.toString(), new RequestCallback() {
            @Override
            public void requestCallback(String result) {
                try {
                    labelContent.setText("");
                    JSONArray jArr = new JSONArray(result);
                     for (int idx = 0; idx < jArr.length(); idx++){
                         JSONObject jObj = (JSONObject) jArr.get(idx);
                         String tagStr = jObj.getString("tag");
                         String content = jObj.getString("content");
                        labelContent.append(tagStr+"의 풀이\n");
                        if (content != null){
                            labelContent.append(content);
                            labelContent.append("\n");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




    }
}
