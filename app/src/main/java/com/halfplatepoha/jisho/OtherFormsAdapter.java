package com.halfplatepoha.jisho;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.model.Japanese;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by surjo on 21/04/17.
 */

public class OtherFormsAdapter extends RecyclerView.Adapter<OtherFormsAdapter.OtherFormsViewHolder> {

    private ArrayList<Japanese> otherForms;

    private LayoutInflater inflater;

    public OtherFormsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public OtherFormsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View otherFormView = inflater.inflate(R.layout.row_other_forms, parent, false);
        return new OtherFormsViewHolder(otherFormView);
    }

    @Override
    public void onBindViewHolder(OtherFormsViewHolder holder, int position) {
        Japanese japanese = otherForms.get(position);
        if(japanese.getWord() != null) {
            holder.tvJapanese.setText(japanese.getWord());
            holder.tvHiragana.setText(japanese.getReading());
        } else {
            holder.tvHiragana.setVisibility(View.GONE);
            holder.tvJapanese.setText(japanese.getReading());
        }
    }

    @Override
    public int getItemCount() {
        if(otherForms != null)
            return otherForms.size();
        return 0;
    }

    public void addOtherForm(Japanese otherForm) {
        if(otherForms == null)
            otherForms = new ArrayList<>();
        int position = otherForms.size();
        otherForms.add(otherForm);

        notifyItemInserted(position);
    }

    public class OtherFormsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvHiragana)
        TextView tvHiragana;

        @BindView(R.id.tvJapanese)
        TextView tvJapanese;

        public OtherFormsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.otherForm)
        public void openSearchScreen() {

        }
    }
}
