package com.halfplatepoha.jisho;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.db.FavJapanese;
import com.halfplatepoha.jisho.db.FavLink;
import com.halfplatepoha.jisho.db.FavSense;
import com.halfplatepoha.jisho.db.FavouriteWord;
import com.halfplatepoha.jisho.model.Japanese;
import com.halfplatepoha.jisho.model.Link;
import com.halfplatepoha.jisho.model.Sense;
import com.halfplatepoha.jisho.model.Word;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.viewholders.KanjiViewHolder;
import com.halfplatepoha.jisho.viewholders.SenseViewHolder;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class DetailsFragment extends DialogFragment implements KanjiViewHolder.OnKanjiClickedListener,
        SenseViewHolder.SenseActionListener{

    private static final String EXTRA_WORD = "mWord";

    private Word mWord;

    private Japanese mPrimary;

    private String mPrimaryString;

    private DetailsFragmentActionListener detailsFragmentActionListener;

    private boolean isFaved, isAlreadyFaved;
    
    @BindView(R.id.sensesContainer)
    LinearLayout sensesContainer;
    
    @BindView(R.id.ivCommon)
    ImageView ivCommon;

    @BindView(R.id.tvHiragana)
    TextView tvHiragana;

    @BindView(R.id.tvJapanese)
    TextView tvJapanese;

    @BindView(R.id.kanjiContainer)
    LinearLayout kanjiContainer;

    @BindView(R.id.rlOtherForms)
    RecyclerView rlOtherForms;

    @BindView(R.id.tvKanjis)
    TextView tvKanjis;

    @BindView(R.id.tvOtherForms)
    TextView tvOtherForms;

    @BindView(R.id.btnFav)
    ImageButton btnFav;

    private Realm realm;

    private OtherFormsAdapter otherFormsAdapter;

    FinestWebView.Builder webBuilder;

    public static DetailsFragment getNewInstance(Word mWord) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_WORD, mWord);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setDetailsFragmentActionListener(DetailsFragmentActionListener detailsFragmentActionListener) {
        this.detailsFragmentActionListener = detailsFragmentActionListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        mWord = (Word) getArguments().getSerializable(EXTRA_WORD);
        mPrimary = mWord.getJapanese().get(0);

        webBuilder = new FinestWebView.Builder(getActivity());
    }

    private void refreshUI() {
        if(mPrimary.getWord() != null) {
            tvJapanese.setText(mPrimary.getWord());
            tvHiragana.setText(mPrimary.getReading());
            mPrimaryString = mPrimary.getWord();
        } else {
            tvHiragana.setVisibility(View.GONE);
            tvJapanese.setText(mPrimary.getReading());
            mPrimaryString = mPrimary.getReading();
        }

        RealmResults<FavouriteWord> result = realm.where(FavouriteWord.class).equalTo("primary", mPrimaryString).findAll();
        if(result != null && !result.isEmpty()) {
            isFaved = true;
            isAlreadyFaved = true;
            btnFav.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.fav));
        }

        ivCommon.setVisibility(mWord.is_common() ? View.VISIBLE : View.GONE);

        //--fill senses
        if(mWord.getSenses() != null && !mWord.getSenses().isEmpty()) {
            sensesContainer.setVisibility(View.VISIBLE);
            sensesContainer.removeAllViews();

            for(int i=0; i < mWord.getSenses().size(); i++) {
                SenseViewHolder vh = new SenseViewHolder(getActivity(), sensesContainer,
                        mWord.getSenses().get(i), this);
                sensesContainer.addView(vh.getView());
            }
        }

        //--fill other forms
        if(mWord.getJapanese().size() > 1) {
            tvOtherForms.setVisibility(View.VISIBLE);
            rlOtherForms.setVisibility(View.VISIBLE);
            otherFormsAdapter = new OtherFormsAdapter(getActivity());
            rlOtherForms.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rlOtherForms.setAdapter(otherFormsAdapter);

            for(int i=1; i<mWord.getJapanese().size(); i++) {
                otherFormsAdapter.addOtherForm(mWord.getJapanese().get(i));
            }
        }

        //--add kanjis
        List<String> kanjis = Utils.kanjiList(mPrimary.getWord());
        if(kanjis != null && !kanjis.isEmpty()) {
            tvKanjis.setVisibility(View.VISIBLE);
            kanjiContainer.setVisibility(View.VISIBLE);
            for (String kanji : kanjis) {
                KanjiViewHolder kvh = new KanjiViewHolder(getActivity(), kanjiContainer, kanji, this);
                kanjiContainer.addView(kvh.getView());
            }
        }
    }

    @OnClick(R.id.back)
    public void back() {
        dismiss();
    }

//    @OnClick(R.id.btnDetails)
//    public void openDetails() {
//        String word = !TextUtils.isEmpty(mPrimary.getWord()) ? mPrimary.getWord() : mPrimary.getReading();
//        webBuilder.iconDefaultColor(ContextCompat.getColor(getActivity(), R.color.white))
//                .menuColor(ContextCompat.getColor(getActivity(), R.color.white))
//                .titleDefault(word)
//                .urlColor(ContextCompat.getColor(getActivity() ,R.color.colorPrimaryLight))
//                .updateTitleFromHtml(false)
//                .titleColor(ContextCompat.getColor(getActivity(), R.color.white))
//                .show(IConstants.JISHO_BASE_URL + word);
//    }

    @OnClick(R.id.btnFav)
    public void fav() {
        if(isFaved) {
            isFaved = false;
            btnFav.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.unfav));
        } else {
            isFaved = true;
            btnFav.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.fav));
        }

        Analytics.getInstance().recordClick("Fav", isFaved ? "Faved" : "Unfaved");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Analytics.getInstance().recordClick("details back", mPrimaryString);

        realm.beginTransaction();
        if(isFaved) {
            if(!isAlreadyFaved) {
                FavouriteWord word = realm.createObject(FavouriteWord.class);
                word.setPrimary(mPrimaryString);

                for (Sense sense : mWord.getSenses()) {
                    FavSense favSense = realm.createObject(FavSense.class);
                    favSense.setEnglish_definitions(sense.getEnglish_definitions());
                    favSense.setInfo(sense.getInfo());
                    favSense.setParts_of_speech(sense.getParts_of_speech());
                    favSense.setTags(sense.getTags());
                    favSense.setSee_also(sense.getSee_also());

                    if (sense.getLinks() != null && !sense.getLinks().isEmpty()) {
                        for (Link link : sense.getLinks()) {
                            FavLink favLink = realm.createObject(FavLink.class);
                            favLink.setText(link.getText());
                            favLink.setUrl(link.getUrl());
                            favSense.getLinks().add(favLink);
                        }
                    }

                    word.getSenses().add(favSense);
                }

                if (mWord.getJapanese() != null && !mWord.getJapanese().isEmpty()) {
                    for (Japanese japanese : mWord.getJapanese()) {
                        FavJapanese favJapanese = realm.createObject(FavJapanese.class);
                        favJapanese.setReading(japanese.getReading());
                        favJapanese.setWord(japanese.getWord());

                        word.getJapanese().add(favJapanese);
                    }
                }

                word.setIs_common(mWord.is_common());
            }

        } else {
            realm.where(FavouriteWord.class).equalTo("primary", mPrimaryString).findAll().deleteAllFromRealm();
        }

        realm.commitTransaction();
        realm.close();
        webBuilder = null;

        super.onDismiss(dialog);
    }

    @Override
    public void onKanjiClicked(String kanji) {
        Analytics.getInstance().recordClick("kanji", kanji);

        detailsFragmentActionListener.onKanjiClicked(kanji);
        dismiss();
    }

    @Override
    public void onLinkClick(String link) {
        Analytics.getInstance().recordClick("link", link);

        webBuilder.urlColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight))
                .titleColor(ContextCompat.getColor(getActivity(), R.color.white))
                .menuColor(ContextCompat.getColor(getActivity(), R.color.white))
                .show(link);
    }

    @Override
    public void seeAlso(String seeAlso) {
        Analytics.getInstance().recordClick("see also", seeAlso);

        detailsFragmentActionListener.seeAlso(seeAlso);
        dismiss();
    }

    public interface DetailsFragmentActionListener {
        void onKanjiClicked(String kanji);

        void seeAlso(String seeAlso);
    }

}
