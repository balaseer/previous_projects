package cs402.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HourlyActivity extends AppCompatActivity {

    User hourlyUser;
    EditText hourlyWage;
    EditText hoursPerWeek;
    EditText overtime;
    Button finish;
    double monthlyWorth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);

        Intent fromRegister = getIntent();
        User fromRegisterUser = (User) fromRegister.getSerializableExtra("hourly_user");
        hourlyUser = fromRegisterUser;

        hourlyWage = (EditText)findViewById(R.id.hourlyEditText);
        hoursPerWeek = (EditText)findViewById(R.id.hourPerWeek);
        overtime = (EditText)findViewById(R.id.numOTHours);


        finish = (Button)findViewById(R.id.finishHourlyButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wage = hourlyWage.getText().toString();
                String hours = hoursPerWeek.getText().toString();
                String ot = overtime.getText().toString();

                if(!TextUtils.isDigitsOnly(wage) || !TextUtils.isDigitsOnly(hours) || !TextUtils.isDigitsOnly(ot)){

                    Toast.makeText(HourlyActivity.this, "Please Enter Valid Values", Toast.LENGTH_LONG).show();
                }

                double hourlyWageValue = Double.parseDouble(wage);
                double hoursPerWeekValue = Double.parseDouble(hours);
                double overtimeValue = Double.parseDouble(ot);

                if(hoursPerWeekValue <= 40 && !ot.isEmpty()){
                    Toast.makeText(HourlyActivity.this, "Can't Have OT With Less Than 40 Hours A Week", Toast.LENGTH_LONG).show();
                    overtimeValue = 0;
                    monthlyWorth = (hourlyWageValue * hoursPerWeekValue);

                }

                if(hoursPerWeekValue <= 40 && ot.isEmpty()){
                    monthlyWorth = (hourlyWageValue * hoursPerWeekValue);
                    overtimeValue = 0;
                }

                if(hoursPerWeekValue > 40 && ot.isEmpty()){
                    overtimeValue= 40 - hoursPerWeekValue;
                    String newOT = Double.toString(overtimeValue);
                    hoursPerWeek.setText("40");
                    overtime.setText(newOT);

                    monthlyWorth = (hourlyWageValue * hoursPerWeekValue) + ((hourlyWageValue * 1.5) * overtimeValue);
                }

                if(hoursPerWeekValue > 40 && !ot.isEmpty()){

                    monthlyWorth = (hourlyWageValue * hoursPerWeekValue) + ((hourlyWageValue * 1.5) * overtimeValue);

                }
                hourlyUser.setMonthlyWorth(monthlyWorth);

//                Realm realm = Realm.getDefaultInstance();
//                realm.beginTransaction();
//                User realmUser = realm.copyToRealmOrUpdate(hourlyUser);
//                realm.commitTransaction();

                Intent backToMain = new Intent(HourlyActivity.this, MainActivity.class);
                backToMain.putExtra("isUser", hourlyUser);
                startActivity(backToMain);
            }
        });

    }
}
