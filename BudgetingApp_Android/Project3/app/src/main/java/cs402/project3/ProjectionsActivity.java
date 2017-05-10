package cs402.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProjectionsActivity extends AppCompatActivity {

    EditText enterUserName;
    EditText enterPassword;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projections);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);

        enterUserName = (EditText)findViewById(R.id.enterUserName);
        enterPassword= (EditText)findViewById(R.id.enterPassword);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.notRegistered);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIsRegistered = new Intent(ProjectionsActivity.this, StatisticsActivity.class);
                startActivity(userIsRegistered);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerUser = new Intent(ProjectionsActivity.this, RegisterActivity.class);
                startActivity(registerUser);
            }
        });
    }
}
