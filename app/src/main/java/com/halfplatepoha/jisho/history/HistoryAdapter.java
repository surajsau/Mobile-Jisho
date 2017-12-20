package com.halfplatepoha.jisho.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.analytics.Analytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by surjo on 22/04/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private ArrayList<String> history;
    private OnSearchClickedListener searchClickedListener;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setSearchClickedListener(OnSearchClickedListener searchClickedListener) {
        this.searchClickedListener = searchClickedListener;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View historyView = inflater.inflate(R.layout.row_history, parent, false);
        return new HistoryViewHolder(historyView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.tvHistory.setText(history.get(position));
    }

    @Override
    public int getItemCount() {
        if(history != null)
            return history.size();
        return 0;
    }

    public void addHistory(String historyString) {
        if(history == null)
            history = new ArrayList<>();
        int position = history.size();
        history.add(historyString);

        notifyItemInserted(position);
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvHistory)
        TextView tvHistory;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.history)
        public void historyClicked() {
            Analytics.getInstance().recordClick("History_Search", history.get(getAdapterPosition()));
            if(searchClickedListener != null)
                searchClickedListener.onSearchClicked(history.get(getAdapterPosition()));
        }
    }

    public interface OnSearchClickedListener {
        void onSearchClicked(String searchString);
    }
}
