package cs402.project3;

import android.app.DatePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Date;

public class CreateBillActivity extends AppCompatActivity {

    Bill billToSave;
    EditText billName;
    EditText billAmount;
    EditText dueDate;
    EditText reminderDate;
    Button saveBillButton;
    Button resetFieldsButton;
    RadioButton monthlyYes;
    RadioButton monthlyNo;
    DatePickerDialog datePickerDialog;
    Boolean isMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);

        billName = (EditText)findViewById(R.id.editText1);
        billAmount = (EditText)findViewById(R.id.editText2);

        dueDate = (EditText) findViewById(R.id.editText3);
        dueDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(CreateBillActivity.this, android.R.style.Theme_Material_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dueDate.setText((monthOfYear + 1) + "/"
                                        + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        reminderDate = (EditText) findViewById(R.id.editText4);
        reminderDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(CreateBillActivity.this, android.R.style.Theme_Material_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                reminderDate.setText((monthOfYear + 1) + "/"
                                        + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        monthlyYes = (RadioButton)findViewById(R.id.radioButtonYes);
        monthlyYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMonthly = true;
            }
        });

        monthlyNo = (RadioButton)findViewById(R.id.radioButton2);
        monthlyNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMonthly = false;
            }
        });

        if(monthlyYes.isChecked() && monthlyNo.isChecked()){
            Toast.makeText(CreateBillActivity.this, "Is This Bill Monthly? It Can't Be Both!!", Toast.LENGTH_LONG).show();

        }

        saveBillButton = (Button)findViewById(R.id.saveBill);
        saveBillButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String tmpBillName = billName.getText().toString();
                if(TextUtils.isEmpty(tmpBillName)){
                    Toast.makeText(CreateBillActivity.this, "Please Enter a Bill Name", Toast.LENGTH_SHORT).show();
                }

                double tmpBillAmount = 0.0;
                try {
                    tmpBillAmount = Double.parseDouble(billAmount.getText().toString());

                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                    Toast.makeText(CreateBillActivity.this, "Please Enter a Valid Bill Amount", Toast.LENGTH_SHORT).show();

                }
                DateFormat formatter =  new SimpleDateFormat("MM/dd/yyyy");
                Date tmpBillDueDate = null;
                try{
                    String tmpDate = dueDate.getText().toString();
                    tmpBillDueDate = formatter.parse(tmpDate);
                }catch (java.text.ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(CreateBillActivity.this, "Please Enter a Valid Due Date", Toast.LENGTH_SHORT).show();

                }

                Date tmpBillReminderDate = null;
                try{
                    String tmpDate2 = reminderDate.getText().toString();
                    tmpBillReminderDate = formatter.parse(tmpDate2);
                }catch (java.text.ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(CreateBillActivity.this, "Please Enter a Valid Reminder Date", Toast.LENGTH_SHORT).show();

                }

//                Realm realm = Realm.getDefaultInstance();
                billToSave = new Bill(tmpBillName, tmpBillAmount, tmpBillDueDate, tmpBillReminderDate);
                billToSave.setMonthly(isMonthly);

//                realm.beginTransaction();
//                Bill realmBill = realm.copyToRealmOrUpdate(billToSave);
//                realm.commitTransaction();

                BillsController.addBill(billToSave);
                if(!TextUtils.isEmpty(tmpBillName) && tmpBillAmount != 0 && tmpBillDueDate != null && tmpBillReminderDate != null) {
                    finish();
                }

            }
        });


        resetFieldsButton = (Button)findViewById(R.id.resetFieldsButton);
        resetFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billName.setText("");
                billAmount.setText("");
            }
        });
    }
}
