package cs402.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SalaryActivity extends AppCompatActivity {

    User salaryUser;
    EditText userSalary;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);


        Intent fromRegister = getIntent();
        User fromRegisterUser = (User) fromRegister.getSerializableExtra("salary_user");
        salaryUser = fromRegisterUser;

        userSalary = (EditText)findViewById(R.id.userSalary);
        finish = (Button)findViewById(R.id.finishButtonSalary);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String salary = userSalary.getText().toString();
                if(!TextUtils.isDigitsOnly(salary) || salary.isEmpty()){

                    Toast.makeText(SalaryActivity.this, "Please Enter a Valid Salary", Toast.LENGTH_LONG).show();
                }
                double monthlyWorth = Double.parseDouble(salary);
                salaryUser.setMonthlyWorth(monthlyWorth);

//                Realm realm = Realm.getDefaultInstance();
//                realm.beginTransaction();
//                User realmUser = realm.copyToRealmOrUpdate(salaryUser);
//                realm.commitTransaction();

                Intent backToMain = new Intent(SalaryActivity.this, MainActivity.class);
                backToMain.putExtra("isUser", salaryUser);
                startActivity(backToMain);
            }
        });

    }
}
