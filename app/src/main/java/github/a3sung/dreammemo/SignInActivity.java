package github.a3sung.dreammemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import github.a3sung.dreammemo.account.AccountController;
import github.a3sung.dreammemo.serverconnector.ErrorCallback;
import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class SignInActivity extends AppCompatActivity {

    private EditText txtId;
    private EditText txtPw;
    private Intent getFlag;
    private Handler loginSuccessHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
            finish();
            if(getFlag.getBooleanExtra("isBackToShare", false)){
                Intent goToWritePostListActivity = new Intent(SignInActivity.this, WriteDreamPostActivity.class);
                goToWritePostListActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goToWritePostListActivity.putExtra("id", getFlag.getStringExtra("id"));
                goToWritePostListActivity.putExtra("title", getFlag.getStringExtra("title"));
                goToWritePostListActivity.putExtra("keywords", getFlag.getStringExtra("keywords"));
                goToWritePostListActivity.putExtra("context", getFlag.getStringExtra("context"));
                startActivity(goToWritePostListActivity);
            }
            else{
                Intent goToDreamPostListActivity = new Intent(SignInActivity.this, DreamPostListActivity.class);
                startActivity(goToDreamPostListActivity);
            }
        }
    };

    private Handler loginFailHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        txtId = findViewById(R.id.txtId);
        txtPw = findViewById(R.id.txtPw);
        getFlag = getIntent();
    }

    public void onClickSignIn(View view){
        ServerConnector svConn = ServerConnector.getInstatnce();
        String userId = txtId.getText().toString();
        String userPw = txtPw.getText().toString();
        Log.d("SignInActivity", String.format("test %s %s", userId, userPw));


        svConn.requestGet(ServerConnector.BASE_URL + String.format("signIn?userID=%s&userPW=%s", userId, userPw), new RequestCallback() {
            @Override
            public void requestCallback(String result) {
                try {
                    Log.d("SignInActivity", "test" + result);
                    JSONObject resultObj = new JSONObject(result);
                    String token = resultObj.getString("token");
                    Log.d("Account", "token info : " + token);

                    // 로그인 성공
                    AccountController.getInstance().signIn(token);
                    Message msg = loginSuccessHandler.obtainMessage();
                    loginSuccessHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorCallback() {
            @Override
            public void errCallback(int resultCode) {
                // TODO: (중요도1) 로그인 실패 시 로그인 제한 되게 하기
                Message msg = loginFailHandler.obtainMessage();
                loginFailHandler.sendMessage(msg);
            }
        });
    }

    public void onClickSignUp(View view){
        Intent toSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
        toSignUp.putExtra("userId", txtId.getText().toString());
        startActivity(toSignUp);
    }
}
