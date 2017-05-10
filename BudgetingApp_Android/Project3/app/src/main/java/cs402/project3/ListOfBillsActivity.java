package cs402.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListOfBillsActivity extends AppCompatActivity implements ListAdapter.ItemClickCallback {

    private RecyclerView recView;
    private ListAdapter adapter;
    private ArrayList listOfBills;
    Bill bill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo3);

        listOfBills = BillsController.getBills();

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListAdapter(listOfBills, this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
    }



    @Override
    public void onItemClick(int p) {

        bill = (Bill) listOfBills.get(p);


        Intent listItemSummery = new Intent(ListOfBillsActivity.this, EditBillActivity.class);
        listItemSummery.putExtra("bill_to_edit", bill);
        startActivityForResult(listItemSummery, 1);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bill updatedBill = (Bill) data.getSerializableExtra("update_list");
                BillsController.removeBill(bill);
                BillsController.addBill(updatedBill);
                Intent refresh = new Intent(this, ListOfBillsActivity.class);
                startActivity(refresh);
                this.finish();
            }

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "List UpDate Did Not Work", Toast.LENGTH_LONG).show();
            }
        }
    }

        @Override
    public void onSecondaryIconClick(int p) {

    }
}
