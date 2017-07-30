package com.halfplatepoha.jisho;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halfplatepoha.jisho.utils.IConstants;
import com.thefinestartist.finestwebview.FinestWebView;

import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class LicenseFragment extends BaseFragment {

    private FinestWebView.Builder webBuilder;

    public LicenseFragment() {}

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_license;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webBuilder = new FinestWebView.Builder(getActivity())
                .urlColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight))
                .titleColor(ContextCompat.getColor(getActivity(), R.color.white))
                .menuColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    @OnClick(R.id.btnJmDict)
    public void jmDict() {
        webBuilder.show(IConstants.JMDICT);
    }

    @OnClick(R.id.btnJmneDict)
    public void jmenDict() {
        webBuilder.show(IConstants.JMEDICT);
    }

    @OnClick(R.id.btnKanjiDic2)
    public void kanjiDic2() {
        webBuilder.show(IConstants.KANJIDIC2);
    }

    @OnClick(R.id.btnRadkfile)
    public void radkfile() {
        webBuilder.show(IConstants.RADKFILE);
    }

    @OnClick(R.id.btnEDict)
    public void edrdg() {
        webBuilder.show(IConstants.EDRDG);
    }

    @OnClick(R.id.btnEDictLicense)
    public void edrdgLicense() {
        webBuilder.show(IConstants.EDRDG_LICENSE);
    }

}
