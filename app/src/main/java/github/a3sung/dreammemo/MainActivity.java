package github.a3sung.dreammemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import github.a3sung.dreammemo.account.AccountController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickMemo(View view){

        Intent moveIntent = new Intent(this, MemoListActivity.class);
        startActivity(moveIntent);

    }

    public void onClickPost(View view){

        AccountController accountController = AccountController.getInstance();
        if (accountController.isSignIn()){
            Intent moveIntent = new Intent(this, DreamPostListActivity.class);
            startActivity(moveIntent);
        }
        else {
            Intent moveIntent = new Intent(this, SignInActivity.class);
            startActivity(moveIntent);
        }

    }
}
