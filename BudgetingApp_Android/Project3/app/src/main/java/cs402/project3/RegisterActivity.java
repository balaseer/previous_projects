package cs402.project3;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    RadioButton hourly;
    RadioButton salary;
    RadioButton tips;
    Button next;
    boolean isHourly;
    boolean isSalary;
    boolean isTips;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        hourly = (RadioButton)findViewById(R.id.hourlyButton);
        salary = (RadioButton)findViewById(R.id.salaryButton);
        tips = (RadioButton)findViewById(R.id.tipsButton);
        next = (Button)findViewById(R.id.registerNextButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent continueRegister;

                if(!hourly.isChecked() && !salary.isChecked() && !tips.isChecked()){
                    Toast.makeText(RegisterActivity.this, "How Do You Get Paid?!", Toast.LENGTH_LONG).show();
                }

                if(hourly.isChecked()){
                    isHourly = true;
                    isSalary = false;
                    isTips = false;
                }

                if(salary.isChecked()){
                    isHourly = false;
                    isSalary = true;
                    isTips = false;
                }

                if(tips.isChecked()){
                    isHourly = false;
                    isSalary = false;
                    isTips = true;
                }



                final User newUser = new User(0, isSalary, isHourly, isTips);

                if(isSalary){
                    continueRegister = new Intent(RegisterActivity.this, SalaryActivity.class);
                    continueRegister.putExtra("salary_user", newUser);
                    startActivity(continueRegister);
                }

                if(isHourly){
                    continueRegister = new Intent(RegisterActivity.this, HourlyActivity.class);
                    continueRegister.putExtra("hourly_user", newUser);
                    startActivity(continueRegister);
                }

                if(isTips){
                    continueRegister = new Intent(RegisterActivity.this, TipsActivity.class);
                    continueRegister.putExtra("tips_user", newUser);
                    startActivity(continueRegister);
                }
            }
        });



    }
}
