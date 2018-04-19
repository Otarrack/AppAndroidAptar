package com.florian.projet.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.florian.projet.R;

import org.apache.poi.ss.formula.functions.T;

public class SectionHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView machineNumberTextView;

//    public TextView volumeTextView;
//    public TextView wasteTextView;
//    public TextView theoreticalPaceTextView;

    public SectionHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.main_recycle_item_title);
        machineNumberTextView = itemView.findViewById(R.id.main_recycle_item_number);


//        volumeTextView = itemView.findViewById(R.id.main_recycle_item_volume_res);
//        wasteTextView = itemView.findViewById(R.id.main_recycle_item_waste_res);
//        theoreticalPaceTextView = itemView.findViewById(R.id.main_recycle_item_theoretical_pace_res);
    }
}
