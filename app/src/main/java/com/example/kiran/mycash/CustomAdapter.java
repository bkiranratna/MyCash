package com.example.kiran.mycash;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> arrAmtSourceType;
    ArrayList<String> arrTransType;
    ArrayList<String> arrAmount;
    ArrayList<String> arrTransDate;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> arrAmtSourceType, ArrayList<String> arrTransType, ArrayList<String> arrAmount, ArrayList<String> arrTransDate) {
        this.context = context;
        this.arrAmtSourceType = arrAmtSourceType;
        this.arrTransType = arrTransType;
        this.arrAmount = arrAmount;
        this.arrTransDate=arrTransDate;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_transaction, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.mAmtSourceType.setText(arrAmtSourceType.get(position));
        holder.mTransType.setText(arrTransType.get(position));
        holder.mAmount.setText(arrAmount.get(position));
        holder.mTransDate.setText(arrTransDate.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, arrAmount.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrAmtSourceType.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mAmtSourceType, mTransType, mAmount,mTransDate;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            mAmtSourceType = (TextView) itemView.findViewById(R.id.amountsourcetype);
            mTransType = (TextView) itemView.findViewById(R.id.transactiontype);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
            mTransDate = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
