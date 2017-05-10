package cs402.project3;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class StatisticsActivity extends AppCompatActivity {

    TextView totalBills;
    TextView moneyLeft;
    Button fin;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);

        totalBills = (TextView)findViewById(R.id.totalBillsAmountForUser);
        moneyLeft = (TextView)findViewById(R.id.moneyLeftForUser);
        fin = (Button)findViewById(R.id.backToHome);

        String sumOfBills = Double.toString(BillsController.sumOfBills());
        totalBills.setText(sumOfBills);

        double allBills = BillsController.sumOfBills();
        double monthlyWorth = User.getMonthlyWorth();
        double left = monthlyWorth - allBills;
        String moneyLeftString = Double.toString(left);
        moneyLeft.setText(moneyLeftString);


        // Here, a BarChart is initialized from xml
        BarChart chart = (BarChart) findViewById(R.id.bar_chart);

        ArrayList<BarEntry> BarEntry = new ArrayList<>();
        for(int i =0; i < BillsController.getBills().size(); i++){
            BarEntry.add(new BarEntry(i, (float) BillsController.getBill(i).getAmount()));

        }

        BarDataSet dataSet = new BarDataSet(BarEntry, "Finances");

        BarData data = new BarData(dataSet);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        chart.setData(data);

        chart.setFitBars(true);
        chart.setDescription("X Axis = Bill, Y Axis = Cost");


        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}


