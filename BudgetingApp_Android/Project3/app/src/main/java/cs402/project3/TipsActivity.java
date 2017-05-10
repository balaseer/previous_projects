package cs402.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TipsActivity extends AppCompatActivity {

    User tipsUser;
    EditText tipsPerDay;
    EditText daysPerWeek;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);

        Intent fromRegister = getIntent();
        User fromRegisterUser = (User) fromRegister.getSerializableExtra("tips_user");
        tipsUser = fromRegisterUser;

        tipsPerDay = (EditText)findViewById(R.id.tipsPerDay);
        daysPerWeek = (EditText)findViewById(R.id.tipsWorkDays);

        finish = (Button)findViewById(R.id.finishTipsButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String averageTipsPerDay = tipsPerDay.getText().toString();
                String workDaysPerWeeks = daysPerWeek.getText().toString();

                if(!TextUtils.isDigitsOnly(averageTipsPerDay) || !TextUtils.isDigitsOnly(workDaysPerWeeks)){

                    Toast.makeText(TipsActivity.this, "Please Enter Valid Values", Toast.LENGTH_LONG).show();
                }

                double tmpTipsPerDay = Double.parseDouble(averageTipsPerDay);
                double tmpDaysPerWeek = Double.parseDouble(workDaysPerWeeks);

                double monthlyWorth = (tmpTipsPerDay * tmpDaysPerWeek) * 4;

                tipsUser.setMonthlyWorth(monthlyWorth);

//                Realm realm = Realm.getDefaultInstance();
//                realm.beginTransaction();
//                User realmUser = realm.copyToRealmOrUpdate(tipsUser);
//                realm.commitTransaction();

                Intent backToMain = new Intent(TipsActivity.this, MainActivity.class);
                backToMain.putExtra("isUser", tipsUser);
                startActivity(backToMain);
            }
        });


    }
}
