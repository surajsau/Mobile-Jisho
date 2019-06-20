package com.halfplatepoha.jisho;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.halfplatepoha.jisho.utils.VerbInflection;
import com.halfplatepoha.jisho.viewholders.InflectionViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class InflectionDialogFragment extends DialogFragment {

    private static final String EXTRA_PRIMARY_STRING = "extra_primary_string";
    private static final String EXTRA_INFLECTIONS = "extra_inflections";

    private ArrayList<VerbInflection> inflections;
    private String primaryString;

    @BindView(R.id.tvPrimaryString)
    TextView tvPrimaryString;

    @BindView(R.id.inflectionsContainer)
    LinearLayout inflectionsContainer;

    public static InflectionDialogFragment getInstance(String primaryString, ArrayList<VerbInflection> inflections) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_INFLECTIONS, inflections);
        bundle.putString(EXTRA_PRIMARY_STRING, primaryString);

        InflectionDialogFragment frag = new InflectionDialogFragment();
        frag.setArguments(bundle);
        return frag;
    }

    public InflectionDialogFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primaryString = getArguments().getString(EXTRA_PRIMARY_STRING);
        inflections = getArguments().getParcelableArrayList(EXTRA_INFLECTIONS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.fragment_inflection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tvPrimaryString.setText(primaryString);

        for(VerbInflection inflection : inflections) {
            InflectionViewHolder vh = new InflectionViewHolder(getActivity(), inflectionsContainer, inflection);
            inflectionsContainer.addView(vh.bind().getView());
        }
    }
}
