package com.florian.projet.viewModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.florian.projet.R;

class MyHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    ProgressBar progressBar;

    MyHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.recycle_item_title);
        progressBar = itemView.findViewById(R.id.recycle_item_progress);
    }
}
