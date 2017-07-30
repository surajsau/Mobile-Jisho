package com.halfplatepoha.jisho;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.offline.model.Entry;
import com.halfplatepoha.jisho.offline.utils.GetEntryDetailsTask;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.UIUtils;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.utils.VerbInflection;
import com.halfplatepoha.jisho.viewholders.KanjiViewHolder;
import com.halfplatepoha.jisho.viewholders.SenseViewHolder;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class DetailsAcitivity extends BaseActivity implements SenseViewHolder.SenseActionListener,
        KanjiViewHolder.OnKanjiClickedListener, NestedScrollView.OnScrollChangeListener, GetEntryDetailsTask.EntryDetailsTaskListener {

    private static final String EXTRA_WORD = "mWord";
    private static final String EXTRA_IS_OFFLINE = "isOffline";
    private static final String EXTRA_OFFLINE_ENTRY_ID = "mOfflineEntryId";

    @BindView(R.id.sensesContainer)
    LinearLayout sensesContainer;

    @BindView(R.id.ivCommon)
    ImageView ivCommon;

    @BindView(R.id.tvHiragana)
    TextView tvHiragana;

    @BindView(R.id.tvJapanese)
    TextView tvJapanese;

    @BindView(R.id.tvExtraJapanese)
    TextView tvExtraJapanese;

    @BindView(R.id.tvExtraHiragana)
    TextView tvExtraHiragana;

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

    @BindView(R.id.scroll)
    NestedScrollView scroll;

    @BindView(R.id.extraToolbar)
    View extraToolbar;

    @BindView(R.id.progress)
    MaterialProgressBar progress;

    @BindView(R.id.header)
    View header;

    private Word mWord;

    private Japanese mPrimary;

    private String mPrimaryString;

    private boolean isFaved, isAlreadyFaved;

    private Realm realm;

    private OtherFormsAdapter otherFormsAdapter;

    private FinestWebView.Builder webBuilder;

    public static Intent getLaunchIntent(Context context, Word word) {
        Intent intent = new Intent(context, DetailsAcitivity.class);
        intent.putExtra(EXTRA_WORD, word);
        intent.putExtra(EXTRA_IS_OFFLINE, false);
        return intent;
    }

    public static Intent getOfflineLaunchIntent(Context context, int entryId) {
        Intent intent = new Intent(context, DetailsAcitivity.class);
        intent.putExtra(EXTRA_IS_OFFLINE, true);
        intent.putExtra(EXTRA_OFFLINE_ENTRY_ID, entryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        boolean isOffline = getIntent().getBooleanExtra(EXTRA_IS_OFFLINE, false);

        if(!isOffline) {

            mWord = (Word) getIntent().getSerializableExtra(EXTRA_WORD);
            mPrimary = mWord.getJapanese().get(0);
            refreshUI();

        } else {

            int entryId = getIntent().getIntExtra(EXTRA_OFFLINE_ENTRY_ID, 0);
            new GetEntryDetailsTask(this, OfflineDbHelper.getInstance(), entryId).execute();

        }

        webBuilder = new FinestWebView.Builder(this);

        scroll.setOnScrollChangeListener(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_details;
    }

    private void refreshUI() {
        progress.setVisibility(View.GONE);

        header.setVisibility(View.VISIBLE);
        scroll.setVisibility(View.VISIBLE);

        if(mPrimary.getWord() != null) {
            tvJapanese.setText(mPrimary.getWord());
            tvExtraJapanese.setText(mPrimary.getWord());
            tvHiragana.setText(mPrimary.getReading());
            tvExtraHiragana.setText(mPrimary.getReading());

            mPrimaryString = mPrimary.getWord();
        } else {
            tvHiragana.setVisibility(View.GONE);
            tvExtraHiragana.setVisibility(View.GONE);
            tvJapanese.setText(mPrimary.getReading());
            tvExtraJapanese.setText(mPrimary.getReading());

            mPrimaryString = mPrimary.getReading();
        }

        Analytics.getInstance().viewDetails(mPrimaryString);

        RealmResults<FavouriteWord> result = realm.where(FavouriteWord.class).equalTo("primary", mPrimaryString).findAll();
        if(result != null && !result.isEmpty()) {
            isFaved = true;
            isAlreadyFaved = true;
            btnFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fav));
        }

        ivCommon.setVisibility(mWord.is_common() ? View.VISIBLE : View.GONE);

        //--fill senses
        if(mWord.getSenses() != null && !mWord.getSenses().isEmpty()) {
            sensesContainer.setVisibility(View.VISIBLE);
            sensesContainer.removeAllViews();

            for(int i=0; i < mWord.getSenses().size(); i++) {
                SenseViewHolder vh = new SenseViewHolder(mPrimaryString, this, sensesContainer,
                        mWord.getSenses().get(i), this);
                sensesContainer.addView(vh.bind().getView());
            }
        }

        //--fill other forms
        if(mWord.getJapanese().size() > 1) {
            tvOtherForms.setVisibility(View.VISIBLE);
            rlOtherForms.setVisibility(View.VISIBLE);
            otherFormsAdapter = new OtherFormsAdapter(this);
            rlOtherForms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
                KanjiViewHolder kvh = new KanjiViewHolder(this, kanjiContainer, kanji, this);
                kanjiContainer.addView(kvh.bind().getView());
            }
        }
    }

    @OnClick(R.id.btnFav)
    public void fav() {
        if(isFaved) {
            isFaved = false;
            btnFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.unfav));
        } else {
            isFaved = true;
            btnFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fav));
        }

        Analytics.getInstance().recordClick("Fav", isFaved ? "Faved" : "Unfaved");
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

    @Override
    public void onLinkClick(String link) {
        Analytics.getInstance().recordClick("link", link);

        if(webBuilder == null)
            webBuilder = new FinestWebView.Builder(this);

        webBuilder.urlColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
                .titleColor(ContextCompat.getColor(this, R.color.white))
                .menuColor(ContextCompat.getColor(this, R.color.white))
                .show(link);
    }

    @Override
    public void seeAlso(String seeAlso) {
        Analytics.getInstance().recordClick("see also", seeAlso);
        Analytics.getInstance().recordSearch(seeAlso);

        openSearch(seeAlso);
    }

    @Override
    public void onInflectionClicked(ArrayList<VerbInflection> inflections) {
        InflectionDialogFragment.getInstance(mPrimaryString, inflections)
                .show(getSupportFragmentManager(), "Inflections");
    }

    private void openSearch(String searchString) {
        Intent searchIntent = new Intent(this, MainActivity.class);
        searchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        searchIntent.putExtra(IConstants.EXTRA_SEARCH_TERM, searchString);
        startActivity(searchIntent);
        finish();
    }

    @Override
    public void onKanjiClicked(String kanji) {
        Analytics.getInstance().recordClick("kanji", kanji);
        Analytics.getInstance().recordSearch(kanji);

        openSearch(kanji);
    }

    @OnClick(R.id.back)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Analytics.getInstance().recordClick("details back", mPrimaryString);

        if(realm != null && !realm.isClosed()) {
            realm.beginTransaction();
            if (isFaved) {
                if (!isAlreadyFaved) {
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
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int scroll = UIUtils.convertPxToDp(this, scrollY);
        float percentage = ((float)scroll - UIUtils.TOOLBAR_THRESHOLD_DP)/(float)UIUtils.TOOLBAR_HEIGHT_DP;
        if(scroll >= (UIUtils.TOOLBAR_THRESHOLD_DP)) {
            extraToolbar.setAlpha(percentage);
        } else {
            extraToolbar.setAlpha(0);
        }
    }

    @Override
    public void onResult(Entry entry) {
        mWord = Word.fromOfflineEntry(entry);
        mPrimary = mWord.getJapanese().get(0);

        refreshUI();
    }
}
