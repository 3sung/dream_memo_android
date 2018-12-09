package github.a3sung.dreammemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class MemoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TTESTT","resume");
        ServerConnector svConn = ServerConnector.getInstatnce();
        svConn.requestPost(ServerConnector.BASE_URL + "signUp", String.format("userID=%s&userEmail=%s&userPW=%s", "test", "test@test.com", "test"), new RequestCallback() {
            @Override
            public void requestCallback(String result) {
                Log.d("TTESTT","register result " + result);
            }
        });
        svConn.requestGet(ServerConnector.BASE_URL + "signIn?userID=test&userPW=test", new RequestCallback() {
            @Override
            public void requestCallback(String result) {
                Log.d("TTESTT","login result " + result);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TTESTT","pause");
    }
}
