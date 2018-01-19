package com.halfplatepoha.jisho.kanji;

import android.view.ViewGroup;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.KanjiElement;
import com.halfplatepoha.jisho.jdb.Reading;
import com.halfplatepoha.jisho.jdb.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by surjo on 09/01/18.
 */

public class KanjiDetailPresenter extends BasePresenter<KanjiDetailContract.View> implements KanjiDetailContract.Presenter {

    private Realm realm;

    private String kanji;

    private Stack<KanjiNode> kanjiNodeStack;

    @Inject
    public KanjiDetailPresenter(KanjiDetailContract.View view, @Named(KanjiDetailActivity.KEY_KANJI) String kanji) {
        super(view);
        this.kanji = kanji;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        realm = Realm.getDefaultInstance();

        kanjiNodeStack = new Stack<>();

        Kanji kanjiResult = realm.where(Kanji.class).equalTo(Schema.Kanji.LITERAL, kanji).findFirst();

        if(kanjiResult != null) {
            view.setKanjiStrokes(kanjiResult.strokes);
            view.setKanji(kanji);

            if(kanjiResult.readings != null) {

                StringBuilder pinyin = new StringBuilder("");
                StringBuilder korean = new StringBuilder("");
                StringBuilder kunyomi = new StringBuilder("");
                StringBuilder onyomi = new StringBuilder("");

                for(Reading reading : kanjiResult.readings) {
                    if(Reading.PINYIN.equals(reading.type)) {
                        pinyin.append(reading.reading).append(" ");
                    } else if(Reading.HANGUL.equals(reading.type) || Reading.KOREAN_ROMAJI.equals(reading.type)) {
                        korean.append(reading.reading).append(" ");
                    } else if(Reading.KUNYOMI.equals(reading.type)) {
                        kunyomi.append(reading.reading).append(" ");
                    } else if(Reading.ONYOMI.equals(reading.type)) {
                        onyomi.append(reading.reading).append(" ");
                    }
                }

                if(!"".equals(kunyomi.toString())) {
                    view.showKunyomi();
                    view.setKunReading(kunyomi.toString());
                }

                if(!"".equals(onyomi.toString())) {
                    view.showOnyomi();
                    view.setOnReading(onyomi.toString());
                }

                if(!"".equals(korean.toString())) {
                    view.showKorean();
                    view.setKoreanReading(korean.toString());
                }

                if(!"".equals(pinyin.toString())) {
                    view.showPinyin();
                    view.setPinyinReading(pinyin.toString());
                }
            }

//            populateComponents(kanjiResult.elements);
        }
    }

    //TODO: implement this properly
    private void populateComponents(RealmList<KanjiElement> elements) {
        if(elements != null) {
            KanjiNode root = new KanjiNode(kanji, 1);

            kanjiNodeStack.push(root);

            int index = 0;

            while(kanjiNodeStack.size() > 0 && index != elements.size()) {
                KanjiElement element = elements.get(index);

                KanjiNode node = new KanjiNode(element.element, element.depth);

                if(element.depth < kanjiNodeStack.peek().getDepth()) {
                    KanjiNode peek = kanjiNodeStack.pop();
                    List<KanjiNode> childs = new ArrayList<>();

                    childs.add(peek);

                    while(kanjiNodeStack.size() > 0 && peek.getDepth() == kanjiNodeStack.peek().getDepth()) {
                        childs.add(kanjiNodeStack.pop());
                    }

                    kanjiNodeStack.peek().addChildNodes(childs);
                }

                kanjiNodeStack.push(node);
                index++;
            }

            if(!kanjiNodeStack.empty()) {
                while(kanjiNodeStack.size() != 1) {
                    KanjiNode peek = kanjiNodeStack.pop();
                    List<KanjiNode> childs = new ArrayList<>();

                    childs.add(peek);

                    while(kanjiNodeStack.size() > 0 && peek.getDepth() == kanjiNodeStack.peek().getDepth()) {
                        childs.add(kanjiNodeStack.pop());
                    }

                    kanjiNodeStack.peek().addChildNodes(childs);
                }
            }

            List<KanjiNode> tree = new ArrayList<>();
            tree.add(kanjiNodeStack.peek());

            view.buildNode(kanjiNodeStack.peek(), view.getComponentsRoot(), 0, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        view.animateStroke();

    }

    @Override
    public void buildFurther(KanjiNode node, ViewGroup kanjiNodeView) {
        if(node.getChildNodes() != null) {
            for (int i = 0; i < node.getChildNodes().size(); i++) {
                view.buildNode(node.getChildNodes().get(i), kanjiNodeView, i, node.getChildNodes().size());
            }
        }
    }

    @Override
    public void clickKanjiPlay() {
        view.animateStroke();
    }

//    public KanjiNode getTree(KanjiNode root, int depth, List<KanjiElement> kanjiElements, int position) {
//        if(position == kanjiElements.size() - 1)
//            return new KanjiNode()
//        KanjiElement element = kanjiElements.get(position);
//    }

}
