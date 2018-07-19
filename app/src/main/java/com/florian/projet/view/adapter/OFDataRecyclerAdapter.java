package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.OFData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OFDataRecyclerAdapter extends RecyclerView.Adapter<OFDataRecyclerAdapter.MyHolder> {
    private ArrayList<OFData> OFDataArrayList;

    public OFDataRecyclerAdapter(ArrayList<OFData> OFDataArrayList) {
        this.OFDataArrayList = OFDataArrayList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_of_data, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        String numOf = OFDataArrayList.get(position).getNumOf();
        String date = formatter.format(OFDataArrayList.get(position).getDate());
        String quantity = Double.toString(OFDataArrayList.get(position).getQuantity()) + "";

        holder.ofNumTextView.setText(numOf);
        holder.dateTextView.setText(date);
        holder.qtProducedTextView.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return OFDataArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView ofNumTextView;
        TextView dateTextView;
        TextView qtProducedTextView;


        MyHolder(View itemView) {
            super(itemView);
            ofNumTextView = itemView.findViewById(R.id.recycler_of_data_of_num);
            dateTextView = itemView.findViewById(R.id.recycler_of_data_date_res);
            qtProducedTextView = itemView.findViewById(R.id.recycler_of_data_qt_res);
        }
    }
}