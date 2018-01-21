package com.halfplatepoha.jisho.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.db.FavJapanese;
import com.halfplatepoha.jisho.db.FavouriteWord;
import com.halfplatepoha.jisho.view.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by surjo on 22/04/17.
 */
@Deprecated
public class FavoritesAdapter  extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private ArrayList<FavouriteWord> words;
    private OnFavoriteClickListner listner;
    private LayoutInflater inflater;

    public FavoritesAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View favView = inflater.inflate(R.layout.row_favourite, parent, false);
        return new FavoritesViewHolder(favView);
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        if(words.get(position) != null) {
            FavJapanese primary = words.get(position).getJapanese().get(0);
            if (!TextUtils.isEmpty(primary.getWord())) {
                holder.tvJapanese.setText(primary.getWord());
                holder.tvHiragana.setText(primary.getReading());
            } else {
                holder.tvHiragana.setVisibility(View.GONE);
                holder.tvJapanese.setText(primary.getReading());
            }
        }
//        holder.ivCommon.setVisibility(words.get(position).is_common() ? View.VISIBLE : View.GONE;
    }

    @Override
    public int getItemCount() {
        if(words != null)
            return words.size();
        return 0;
    }

    public void addFavorite(FavouriteWord word) {
        if(words == null)
            words = new ArrayList<>();
        int position = words.size();
        words.add(word);

        notifyItemInserted(position);
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvHiragana)
        CustomTextView tvHiragana;

        @BindView(R.id.tvJapanese)
        CustomTextView tvJapanese;

//        @BindView(R.id.ivCommon)
//        ImageView ivCommon;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.favorite)
        public void favoritClick() {
            Analytics.getInstance().recordClick("Fav_Detail", "Fav_Detail");
            if(listner != null)
                listner.onFavoriteClicked(words.get(getAdapterPosition()));
        }
    }

    public interface OnFavoriteClickListner {
        void onFavoriteClicked(FavouriteWord word);
    }
}
