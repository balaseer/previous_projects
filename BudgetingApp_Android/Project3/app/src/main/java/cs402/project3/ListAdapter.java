package cs402.project3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

    /**
     * Created by itsmebadr on 4/7/17.
     */

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.BillsHolder>{

        private List<Bill> listData;
        private LayoutInflater inflater;

        private ItemClickCallback itemClickCallback;

        public interface ItemClickCallback {
            void onItemClick(int p);
            void onSecondaryIconClick(int p);
        }

        public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
            this.itemClickCallback = itemClickCallback;
        }

        public ListAdapter(List<Bill> listData, Context c){

            this.inflater = LayoutInflater.from(c);
            this.listData = listData;
        }


        @Override
        public BillsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new BillsHolder(view);
        }

        @Override
        public void onBindViewHolder(BillsHolder holder, int position) {

            Bill bill = listData.get(position);
            holder.title.setText(bill.getName());
            holder.subTitle.setText("Bill Amount: " + bill.getAmount() + ". Bill Due Date: " + bill.getDueDate().toString());
            holder.icon.setImageResource(bill.getImageResId());
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        class BillsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


            private TextView title;
            private TextView subTitle;
            private ImageView icon;
            private View container;


            public BillsHolder(View itemView) {
                super(itemView);

                title = (TextView)itemView.findViewById(R.id.lbl_item_text);
                subTitle = (TextView)itemView.findViewById(R.id.lbl_item_sub_title);
                icon = (ImageView)itemView.findViewById(R.id.im_item_icon);
                container = itemView.findViewById(R.id.cont_item_root);
                container.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.cont_item_root){
                    itemClickCallback.onItemClick(getAdapterPosition());
                } else {
                    itemClickCallback.onSecondaryIconClick(getAdapterPosition());
                }
            }
        }
    }

