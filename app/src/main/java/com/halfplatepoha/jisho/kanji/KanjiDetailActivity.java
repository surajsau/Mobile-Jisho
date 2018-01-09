package com.halfplatepoha.jisho.kanji;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.badoualy.kanjistroke.KanjiStrokeView;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class KanjiDetailActivity extends BaseActivity<KanjiDetailContract.Presenter> implements KanjiDetailContract.View {

    public static final String KEY_KANJI = "kanji";

    @BindView(R.id.ivKanjiPlay)
    KanjiStrokeView ivKanjiPlay;

    @BindView(R.id.japanese)
    View japanese;

    @BindView(R.id.korean)
    View korean;

    @BindView(R.id.chinese)
    View chinese;

    @BindView(R.id.tvJapaneseReading)
    TextView tvJapaneseReading;

    @BindView(R.id.tvKoreanReading)
    TextView tvKoreanReading;

    @BindView(R.id.tvChineseReading)
    TextView tvChineseReading;

    @BindView(R.id.nodeComponentsContainer)
    LinearLayout nodeComponentsContainer;

    public static Intent getIntent(Context context, String kanji) {
        Intent intent = new Intent(context, KanjiDetailActivity.class);
        intent.putExtra(KEY_KANJI, kanji);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_kanji_detail;
    }


    @Override
    public void setKanjiStrokes(List<String> strokes) {
        ivKanjiPlay.loadPathData(strokes);
    }

    @Override
    public void animateStroke() {
        ivKanjiPlay.startDrawAnimation(500);
    }

    @Override
    public void setKanjiElements(String kanjiElements) {
//        tvComponents.setText(kanjiElements);
    }

    @Override
    public void setPinyinReading(String reading) {
        tvChineseReading.setText(reading);
    }

    @Override
    public void showPinyin() {
        chinese.setVisibility(View.VISIBLE);
    }

    @Override
    public void showKorean() {
        korean.setVisibility(View.VISIBLE);
    }

    @Override
    public void showJapanese() {
        japanese.setVisibility(View.VISIBLE);
    }

    @Override
    public void setKoreanReading(String reading) {
        tvKoreanReading.setText(reading);
    }

    @Override
    public void setJapaneseReading(String reading) {
        tvJapaneseReading.setText(reading);
    }

    @Override
    public void buildNode(KanjiNode node, ViewGroup parent, int childPosition, int totalChildrenOfParent) {

        Log.e("COMPONENT", node.getElement());

        View kanjiNodeView = LayoutInflater.from(this).inflate(R.layout.row_kanji_node, parent, true);
        TextView tvKanjiNode = kanjiNodeView.findViewById(R.id.tvKanjiNode);
        View firstDash = kanjiNodeView.findViewById(R.id.verticalDashFirst);
        View middleDash = kanjiNodeView.findViewById(R.id.verticalDashMiddle);
        View lastDash = kanjiNodeView.findViewById(R.id.verticalDashLast);
        View beforeDash = kanjiNodeView.findViewById(R.id.dashBefore);
        View afterDash = kanjiNodeView.findViewById(R.id.dashAfter);
        LinearLayout childContainer = kanjiNodeView.findViewById(R.id.childContainer);

        if(node.isRoot())
            beforeDash.setVisibility(View.GONE);

        if(node.isLeaf())
            afterDash.setVisibility(View.GONE);

        if(!node.isRoot()) {
            if (childPosition == 0)
                firstDash.setVisibility(View.VISIBLE);
            else if (childPosition > 0 && childPosition < totalChildrenOfParent - 1)
                middleDash.setVisibility(View.VISIBLE);
            else if (childPosition == totalChildrenOfParent - 1)
                lastDash.setVisibility(View.VISIBLE);
        }

        tvKanjiNode.setText(node.getElement());

        presenter.buildFurther(node, childContainer);
    }

    @Override
    public ViewGroup getComponentsRoot() {
        return nodeComponentsContainer;
    }

    @OnClick(R.id.ivKanjiPlay)
    public void clickKanjiPlay() {
        presenter.clickKanjiPlay();
    }
}
