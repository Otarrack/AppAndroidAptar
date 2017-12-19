package com.florian.projet.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.florian.projet.R;

public class MyHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView volumeTextView;
    public TextView wasteQuantityTextView;
    public TextView wastePercentTextView;

    public MyHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.recycle_item_title);
        volumeTextView = itemView.findViewById(R.id.recycle_item_volume_res);
        wasteQuantityTextView = itemView.findViewById(R.id.recycle_item_waste_quantity_res);
        wastePercentTextView = itemView.findViewById(R.id.recycle_item_waste_percent_res);
    }
}
