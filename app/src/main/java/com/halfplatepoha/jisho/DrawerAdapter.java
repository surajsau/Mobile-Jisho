package com.halfplatepoha.jisho;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.view.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by surjo on 22/04/17.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ROW_ITEM = 2;

    public enum DrawerItem{
        HOME("Home"), HISTORY("History"), FAVORITE("Favorite"), ABOUT("About");

        private String title;
        DrawerItem(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private DrawerListener drawerListener;

    private LayoutInflater inflater;

    public DrawerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setDrawerListener(DrawerListener drawerListener) {
        this.drawerListener = drawerListener;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                View draweItemView = inflater.inflate(R.layout.row_drawer, parent, false);
                return new DrawerViewHolder(draweItemView);

            case TYPE_ROW_ITEM:
                View rowItemView = inflater.inflate(R.layout.row_drawer, parent, false);
                return new DrawerViewHolder(rowItemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        holder.tvTitle.setText(DrawerItem.values()[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return DrawerItem.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE_HEADER;
        return TYPE_ROW_ITEM;
    }

    public class DrawerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        CustomTextView tvTitle;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.drawerItem)
        public void drawerItemSelected() {
            Analytics.getInstance().recordClick("Drawer", DrawerItem.values()[getAdapterPosition()].getTitle());
            if(drawerListener != null)
                drawerListener.drawerItemSelected(DrawerItem.values()[getAdapterPosition()]);
        }
    }

    public interface DrawerListener {
        void drawerItemSelected(DrawerItem item);
    }
}
