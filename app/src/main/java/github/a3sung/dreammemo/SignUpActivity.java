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

import github.a3sung.dreammemo.serverconnector.ErrorCallback;
import github.a3sung.dreammemo.serverconnector.RequestCallback;
import github.a3sung.dreammemo.serverconnector.ServerConnector;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtId;
    private EditText txtPw;
    private EditText txtPwConfirmed;
    private EditText txtEmail;

    private Handler signUpSuccessHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
            finish();
        }
    };

    private Handler signUpFailHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtId = findViewById(R.id.txtId);
        txtPw = findViewById(R.id.txtPw);
        txtPwConfirmed = findViewById(R.id.txtPwConfirmed);
        txtEmail = findViewById(R.id.txtEmail);

        Intent intent = getIntent();
        String userId = intent.getExtras().getString("userId");
        if (userId != null && !userId.isEmpty()){
            txtId.setText(userId);
        }
    }

    public void onClickSignUp(View view){

        String userId = txtId.getText().toString();
        String userPw = txtPw.getText().toString();
        String userPwConfirmed = txtPwConfirmed.getText().toString();
        String userEmail = txtEmail.getText().toString();

        if (!validateForm(userId, userPw, userPwConfirmed, userEmail)){
            return;
        }

        ServerConnector svConn = ServerConnector.getInstatnce();
        svConn.requestPost(ServerConnector.BASE_URL + "signUp", String.format("userID=%s&userEmail=%s&userPW=%s", userId, userEmail, userPw), new RequestCallback() {
            @Override
            public void requestCallback(String result) {
                Log.d("Account", "Sign Up success!");
                Message msg = signUpSuccessHandler.obtainMessage();
                signUpSuccessHandler.sendMessage(msg);
            }
        }, new ErrorCallback() {
            @Override
            public void errCallback(int resultCode) {
                Log.d("TAG", "err code" + resultCode);
                Message msg = signUpFailHandler.obtainMessage();
                signUpFailHandler.sendMessage(msg);
            }
        });


    }

    private boolean validateForm(String userId, String userPw, String userPwConfirmed, String userEmail){
        if (!userPw.equals(userPwConfirmed)){
            Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
