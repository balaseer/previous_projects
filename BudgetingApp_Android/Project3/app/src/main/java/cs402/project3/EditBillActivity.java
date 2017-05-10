package cs402.project3;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.Date;

public class EditBillActivity extends AppCompatActivity {

    Bill billToEdit;
    EditText newBillName;
    EditText newBillAmount;
    EditText newDueDate;
    EditText newReminderDate;
    Button newSaveBillButton;
    Button newResetFieldsButton;
    DatePickerDialog newDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);


        Intent fromList = getIntent();
        final Bill passedBill = (Bill) fromList.getSerializableExtra("bill_to_edit");
        billToEdit = passedBill;

        newBillName = (EditText) findViewById(R.id.newBillName);
        newBillAmount = (EditText) findViewById(R.id.newBillAmount);

        newDueDate = (EditText) findViewById(R.id.newBillDueDate);
        newDueDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                newDatePickerDialog = new DatePickerDialog(EditBillActivity.this, android.R.style.Theme_Material_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                newDueDate.setText((monthOfYear + 1) + "/"
                                        + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                newDatePickerDialog.show();
            }
        });

        newReminderDate = (EditText) findViewById(R.id.newBillReminderDate);
        newReminderDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                newDatePickerDialog = new DatePickerDialog(EditBillActivity.this, android.R.style.Theme_Material_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                newReminderDate.setText((monthOfYear + 1) + "/"
                                        + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                newDatePickerDialog.show();
            }
        });


        newSaveBillButton = (Button) findViewById(R.id.saveButton2);
        newSaveBillButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String tmpBillName = newBillName.getText().toString();
                if (TextUtils.isEmpty(tmpBillName)) {
                    Toast.makeText(EditBillActivity.this, "Please Enter a Bill Name", Toast.LENGTH_SHORT).show();
                }

                double tmpBillAmount = 0.0;
                try {
                    tmpBillAmount = Double.parseDouble(newBillAmount.getText().toString());

                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                    Toast.makeText(EditBillActivity.this, "Please Enter a Valid Bill Amount", Toast.LENGTH_SHORT).show();
                }

                if(tmpBillAmount == 0){
                    Toast.makeText(EditBillActivity.this, "Please Enter a Valid Bill Amount", Toast.LENGTH_SHORT).show();
                }

                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date tmpBillDueDate = null;
                try {
                    String tmpDate = newDueDate.getText().toString();
                    tmpBillDueDate = formatter.parse(tmpDate);
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(EditBillActivity.this, "Please Enter a Valid Due Date", Toast.LENGTH_SHORT).show();
                }

                Date tmpBillReminderDate = null;
                try {
                    String tmpDate2 = newReminderDate.getText().toString();
                    tmpBillReminderDate = formatter.parse(tmpDate2);
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(EditBillActivity.this, "Please Enter a Valid Reminder Date", Toast.LENGTH_SHORT).show();

                }

//                Realm realm = Realm.getDefaultInstance();
//                final RealmResults<Bill> tmpRealmBill = realm.where(Bill.class).equalTo("name", billToEdit.getName()).findAll();
                billToEdit.setName(tmpBillName);
                billToEdit.setAmount(tmpBillAmount);
                billToEdit.setDueDate(tmpBillDueDate);
                billToEdit.setReminderDate(tmpBillReminderDate);

//                realm.beginTransaction();
//                tmpRealmBill.deleteAllFromRealm();
//                Bill realmBill = realm.copyToRealmOrUpdate(billToEdit);
//                realm.commitTransaction();
//
//                tmpRealmBill.addChangeListener(new RealmChangeListener<RealmResults<Bill>>() {
//                    @Override
//                    public void onChange(RealmResults<Bill> element) {
//                        element.size();
//                        tmpRealmBill.size();
//                    }
//                });

                Intent backToList = new Intent();
                backToList.putExtra("update_list", billToEdit);
                setResult(RESULT_OK, backToList);

                if(!TextUtils.isEmpty(tmpBillName) && tmpBillAmount != 0 && tmpBillDueDate != null && tmpBillReminderDate != null) {
                    finish();
                }

            }
        });

        newResetFieldsButton = (Button)findViewById(R.id.resetButton2);
        newResetFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBillName.setText("");
                newBillAmount.setText("");
            }
        });

    }

}