package cs402.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);


 //       Realm.init(this);
        EventBus.getDefault().register(this);
 //       DataBaseController.getBillsFromDB();
//       DataBaseController.getUserFromDatabase();

        GridView gridview = (GridView) findViewById(R.id.mainGridview);
        gridview.setAdapter(new ImageAdapter(this));

        Intent completedUser = getIntent();
        User isUser = (User) completedUser.getSerializableExtra("isUser");
        user = isUser;


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent navigate;
                if(position == 0){
                    navigate = new Intent(MainActivity.this, CreateBillActivity.class);
                    startActivity(navigate);
                }

                if(position == 1){
                    navigate = new Intent(MainActivity.this, ListOfBillsActivity.class);
                    startActivity(navigate);
                }

                if(position == 2){
                    navigate = new Intent(MainActivity.this, CalendarViewActivity.class);
                    startActivity(navigate);
                }

                if(position == 3){
                    if(user == null){
                        navigate = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(navigate);
                    } else if(user.isUser()) {
                        navigate = new Intent(MainActivity.this, StatisticsActivity.class);
                        startActivity(navigate);
                    }
                }
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BillAddedEvent BillAddedEvent) {

    }


}