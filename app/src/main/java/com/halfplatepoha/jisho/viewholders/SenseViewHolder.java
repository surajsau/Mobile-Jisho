package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.model.Sense;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.utils.VerbInflection;
import com.halfplatepoha.jisho.utils.VerbInflectionUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 21/04/17.
 */

public class SenseViewHolder extends BaseViewHolder<Sense> {

    @BindView(R.id.tvInfo)
    TextView tvInfo;

    @BindView(R.id.tvSeeAlso)
    TextView tvSeeAlso;

    @BindView(R.id.tvSensePoS)
    TextView tvSensePoS;

    @BindView(R.id.tvSense)
    TextView tvSense;

    @BindView(R.id.tvLink)
    TextView tvLink;

    @BindView(R.id.tvTags)
    TextView tvTags;

    @BindView(R.id.tvInflection)
    TextView tvInflection;

    private SenseActionListener senseActionListener;

    private String primaryString;

    public SenseViewHolder(String primaryString, Context context, ViewGroup parent, Sense sense, SenseActionListener senseActionListener) {
        super(context, parent, sense);
        this.senseActionListener = senseActionListener;
        this.primaryString = primaryString;
    }

    @Override
    public void bindView(Sense sense) {
        if(sense != null && sense.getEnglish_definitions() != null && !sense.getEnglish_definitions().isEmpty()) {
            if (sense.getParts_of_speech() != null && !sense.getParts_of_speech().isEmpty()) {
                tvSensePoS.setVisibility(View.VISIBLE);
                tvSensePoS.setText(Utils.getCommaSeparatedString(sense.getParts_of_speech()));
            }

            if (sense.getLinks() != null && !sense.getLinks().isEmpty()) {
                tvLink.setVisibility(View.VISIBLE);
                tvLink.setText(sense.getLinks().get(0).getText());
                tvLink.setTag(sense.getLinks().get(0).getUrl());
            }

            if (sense.getSee_also() != null && !sense.getSee_also().isEmpty()) {
                tvSeeAlso.setVisibility(View.VISIBLE);
                tvSeeAlso.setText(String.format("See also %s", sense.getSee_also().get(0)));
                tvSeeAlso.setTag(sense.getSee_also().get(0));
            }

            if (sense.getTags() != null && !sense.getTags().isEmpty()) {
                tvTags.setVisibility(View.VISIBLE);
                tvTags.setText(Utils.getCommaSeparatedString(sense.getTags()));
            }

            tvSense.setText(Utils.getCommaSeparatedString(sense.getEnglish_definitions()));

            if (!VerbInflection.TYPE_NONE.equalsIgnoreCase(sense.getType())) {
                tvInflection.setVisibility(View.VISIBLE);
                tvInflection.setTag(sense);
            }
        }
    }

    @OnClick(R.id.tvLink)
    public void openLink() {
        if(senseActionListener != null)
            senseActionListener.onLinkClick(tvLink.getTag().toString());
    }

    @OnClick(R.id.tvSeeAlso)
    public void seeAlso() {
        if(senseActionListener != null)
            senseActionListener.seeAlso(tvSeeAlso.getTag().toString());
    }

    @OnClick(R.id.tvInflection)
    public void showInflection() {
        if(senseActionListener != null)
            senseActionListener.onInflectionClicked(getInflections((Sense)tvInflection.getTag()));
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.row_sense;
    }

    public interface SenseActionListener {
        void onLinkClick(String link);
        void seeAlso(String seeAlso);
        void onInflectionClicked(ArrayList<VerbInflection> inflections);
    }

    private ArrayList<VerbInflection> getInflections(Sense sense) {
        VerbInflectionUtils inflectionUtils = new VerbInflectionUtils(primaryString, sense.getType());
        ArrayList<VerbInflection> inflections = new ArrayList<>();
        inflections.add(inflectionUtils.getInflection(VerbInflection.NON_PAST));
        inflections.add(inflectionUtils.getInflection(VerbInflection.NON_PAST_POLITE));
        inflections.add(inflectionUtils.getInflection(VerbInflection.PAST));
        inflections.add(inflectionUtils.getInflection(VerbInflection.PAST_POLITE));
        inflections.add(inflectionUtils.getInflection(VerbInflection.TE_FORM));
        inflections.add(inflectionUtils.getInflection(VerbInflection.POTENTIAL));
        inflections.add(inflectionUtils.getInflection(VerbInflection.PASSIVE));
        inflections.add(inflectionUtils.getInflection(VerbInflection.CAUSATIVE));
        inflections.add(inflectionUtils.getInflection(VerbInflection.CAUSATIVE_PASSIVE));
        inflections.add(inflectionUtils.getInflection(VerbInflection.IMPERATIVE));
        return inflections;
    }

}
