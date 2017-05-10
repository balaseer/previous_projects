package cs402.project3;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CalendarViewActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat formatMonth = new SimpleDateFormat("  MMMM - yyyy", Locale.getDefault());
    private ArrayList<Bill> listOfAllBills;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);
        actionBar.setTitle(null);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.calendarView);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.isAnimating();


        listOfAllBills = BillsController.getBills();

        for (int i = 0; i < listOfAllBills.size(); i++) {

            Date date = listOfAllBills.get(i).getDueDate();

            long epoch = 0;
            DateFormat formatter = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            String currDate = date.toString();
            try {
                epoch = formatter.parse(currDate).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            event = new Event(Color.RED, epoch, listOfAllBills.get(i).getName() +
                    " is due on this day. This Amount is " + listOfAllBills.get(i).getAmount());
            compactCalendarView.addEvent(event);

            if (listOfAllBills.get(i).isMonthly() == true) {

                long monthlyEpoch;

                for (int j = 0; j <= 12; j++) {
                    //  long monthlyEpoch = (epoch + TimeUnit.DAYS.toMillis(30));

                    Date monthlyReminderDate = new Date(epoch * 1000);
                    int iYear = monthlyReminderDate.getYear();
                    int iMonth = monthlyReminderDate.getMonth();
                    Calendar cal = new GregorianCalendar(iYear, iMonth, 1);
                    int dayInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                    if (dayInMonth == 30) {

                        monthlyEpoch = (epoch + TimeUnit.DAYS.toMillis(30));

                        event = new Event(Color.RED, monthlyEpoch, listOfAllBills.get(i).getName() +
                                " is due on this day. This Amount is " + listOfAllBills.get(i).getAmount());
                        compactCalendarView.addEvent(event);

                       // epoch = (epoch + TimeUnit.DAYS.toMillis(30));
                    }

                    if (dayInMonth == 31) {

                        monthlyEpoch = (epoch + TimeUnit.DAYS.toMillis(29));

                        event = new Event(Color.RED, monthlyEpoch, listOfAllBills.get(i).getName() +
                                " is due on this day. This Amount is " + listOfAllBills.get(i).getAmount());
                        compactCalendarView.addEvent(event);

                        //epoch = (epoch + TimeUnit.DAYS.toMillis(30));

                    }

                    if (dayInMonth == 29) {

                        monthlyEpoch = (epoch + TimeUnit.DAYS.toMillis(30));

                          event = new Event(Color.RED, monthlyEpoch, listOfAllBills.get(i).getName() +
                                " is due on this day. This Amount is " + listOfAllBills.get(i).getAmount());
                        compactCalendarView.addEvent(event);

                       // epoch = (epoch + TimeUnit.DAYS.toMillis(29));

                    }

                    if (dayInMonth == 28) {

                        monthlyEpoch = (epoch + TimeUnit.DAYS.toMillis(32));

                          event = new Event(Color.RED, monthlyEpoch, listOfAllBills.get(i).getName() +
                                " is due on this day. This Amount is " + listOfAllBills.get(i).getAmount());
                        compactCalendarView.addEvent(event);

                       // epoch = (epoch + TimeUnit.DAYS.toMillis(28));

                    }

                     epoch = (epoch + TimeUnit.DAYS.toMillis(30));
                }
            }


            compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    Context context = getApplicationContext();


                    for (int i = 0; i < listOfAllBills.size(); i++) {

                        if (dateClicked.toString().compareTo(listOfAllBills.get(i).getDueDate().toString()) == 0) {
                            Toast.makeText(context, listOfAllBills.get(i).getName() +
                                    " is due on this day. This Amount is " + listOfAllBills.get(i).getAmount(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "No Bills Scheduled for that day", Toast.LENGTH_SHORT).show();
                        }

                    }


                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    actionBar.setTitle(formatMonth.format(firstDayOfNewMonth));
                }
            });
        }

    }
}
