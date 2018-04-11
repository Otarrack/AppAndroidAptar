package com.florian.projet.view.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.viewModel.ArticleViewModel;

public class ArticleOfRecyclerAdapter extends RecyclerView.Adapter<ArticleOfRecyclerAdapter.MyHolder> {
    private ArticleViewModel articleViewModel;

    private CustomItemClickListener listener;
    private Context context;

    public ArticleOfRecyclerAdapter(CustomItemClickListener listener, Context context) {
        this.articleViewModel = ArticleViewModel.getInstance();
        this.listener = listener;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_article_planning_of, viewGroup, false);

        final MyHolder holder = new MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.numOFTextView.setText(String.valueOf(articleViewModel.getOfAt(position).getNumOF()));
        holder.startDateTextView.setText(String.valueOf(articleViewModel.getOFStartDateFormat(articleViewModel.getOfAt(position))));
        holder.endDateTextView.setText(String.valueOf(articleViewModel.getOFEndDateFormat(articleViewModel.getOfAt(position))));
        holder.askedQtTextView.setText(String.valueOf(articleViewModel.getOfAt(position).getQtAsked()));
        holder.itemLayout.setBackground(articleViewModel.drawableOFInTime(articleViewModel.getOfAt(position), context));
    }

    @Override
    public int getItemCount() {
        return articleViewModel.getOfList().size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ConstraintLayout itemLayout;
        TextView numOFTextView;
        TextView startDateTextView;
        TextView endDateTextView;
        TextView askedQtTextView;


        MyHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.recycler_article_of_layout);
            numOFTextView = itemView.findViewById(R.id.recycler_article_of_item_num);
            startDateTextView = itemView.findViewById(R.id.recycler_article_of_item_start_date_res);
            endDateTextView = itemView.findViewById(R.id.recycler_article_of_item_end_date_res);
            askedQtTextView = itemView.findViewById(R.id.recycler_article_of_item_qt_res);
        }
    }
}

