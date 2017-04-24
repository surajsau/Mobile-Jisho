package com.halfplatepoha.jisho;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.model.Japanese;
import com.halfplatepoha.jisho.model.Sense;
import com.halfplatepoha.jisho.model.Word;
import com.halfplatepoha.jisho.viewholders.SearchResultSenseViewHolder;
import com.halfplatepoha.jisho.viewholders.SenseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by surjo on 21/04/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private ArrayList<Word> words;
    private Context mContext;
    private LayoutInflater inflater;

    private MainAdapterActionListener listener;

    public MainAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setMainAdapterActionListener(MainAdapterActionListener listener) {
        this.listener = listener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = inflater.inflate(R.layout.row_word, parent, false);
        return new MainViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Word word = words.get(position);
        Japanese primary = word.getJapanese().get(0);

        holder.ivCommon.setVisibility(word.is_common() ? View.VISIBLE : View.GONE);

        if(primary.getWord() != null) {
            holder.tvHiragana.setText(primary.getReading());
            holder.tvJapanese.setText(primary.getWord());
        } else {
            holder.tvHiragana.setVisibility(View.GONE);
            holder.tvJapanese.setText(primary.getReading());
        }

        if(word.getSenses() != null && !word.getSenses().isEmpty()) {
            holder.sensesContainer.setVisibility(View.VISIBLE);
            holder.sensesContainer.removeAllViews();

            for(int i=0; i < word.getSenses().size() && i < 2; i++) {
                Sense sense = word.getSenses().get(i);
                SearchResultSenseViewHolder vh = new SearchResultSenseViewHolder(mContext, holder.sensesContainer, sense);
                holder.sensesContainer.addView(vh.getView());
            }
        }
    }

    public void addWord(Word word) {
        if(words == null)
            words = new ArrayList<>();
        int position = words.size();
        words.add(word);

        notifyItemInserted(position);
    }

    public void clearWords() {
        if(words != null) {
            int size = words.size();
            words.clear();

            notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public int getItemCount() {
        if(words != null)
            return words.size();
        return 0;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvJapanese)
        TextView tvJapanese;

        @BindView(R.id.ivCommon)
        ImageView ivCommon;

        @BindView(R.id.tvHiragana)
        TextView tvHiragana;

        @BindView(R.id.sensesContainer)
        LinearLayout sensesContainer;

        @BindView(R.id.tvOtherForms)
        TextView tvOtherForms;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.rowCard)
        void seeMore() {
            Analytics.getInstance().recordClick("Search_Details", "");
            if(listener != null)
                listener.onSearchResultClicked(words.get(getAdapterPosition()));
        }
    }

    public interface MainAdapterActionListener {
        void onSearchResultClicked(Word word);
    }
}
