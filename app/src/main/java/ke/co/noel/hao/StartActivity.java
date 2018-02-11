package ke.co.noel.hao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ke.co.noel.hao.R;
import ke.co.noel.hao.authentication.LoginActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Intent lockIntent = new Intent(this, LoginActivity.class);
        startActivity(lockIntent);
    }
}
