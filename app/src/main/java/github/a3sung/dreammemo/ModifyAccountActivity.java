package github.a3sung.dreammemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyAccountActivity extends AppCompatActivity {

    private EditText txtPw;
    private EditText txtPwConfirmed;
    private Button btnChangePw;
    private Button btnQuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);

        txtPw = findViewById(R.id.txtPw);
        txtPwConfirmed = findViewById(R.id.txtPwConfirmed);
        btnChangePw = findViewById(R.id.btnChangePw);
        btnQuit = findViewById(R.id.btnQuit);

    }

    public void onClickChangePw(View view){



    }

    public void onClickQuit(View view){

    }
}
