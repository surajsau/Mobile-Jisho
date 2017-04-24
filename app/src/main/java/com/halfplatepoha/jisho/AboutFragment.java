package com.halfplatepoha.jisho;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends BaseFragment {

    public AboutFragment() {}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_about;
    }

    @OnClick(R.id.btnDeveloper)
    public void mailToDev() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, "i@surajsau.in");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding jisho");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
