package com.halfplatepoha.jisho;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        webBuilder.show("http://www.csse.monash.edu.au/~jwb/edict_doc.html");
    }

    @OnClick(R.id.btnJmneDict)
    public void jmenDict() {
        webBuilder.show("http://www.csse.monash.edu.au/~jwb/enamdict_doc.html");
    }

    @OnClick(R.id.btnKanjiDic2)
    public void kanjiDic2() {
        webBuilder.show("http://www.csse.monash.edu.au/~jwb/kanjidic2/index.html");
    }

    @OnClick(R.id.btnRadkfile)
    public void radkfile() {
        webBuilder.show("http://www.csse.monash.edu.au/~jwb/kradinf.html");
    }

    @OnClick(R.id.btnEDict)
    public void edrdg() {
        webBuilder.show("http://www.edrdg.org/");
    }

    @OnClick(R.id.btnEDictLicense)
    public void edrdgLicense() {
        webBuilder.show("http://www.edrdg.org/edrdg/licence.html");
    }

}
