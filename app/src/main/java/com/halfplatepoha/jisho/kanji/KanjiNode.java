package com.halfplatepoha.jisho.kanji;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surjo on 09/01/18.
 */

public class KanjiNode {

    private String element;
    private int depth;
    private List<KanjiNode> nodes;

    public KanjiNode(String element, int depth) {
        this.element = element;
        this.depth = depth;
        this.nodes = new ArrayList<>();
    }

    public void addChildNodes(List<KanjiNode> nodes) {
        this.nodes = nodes;
    }

    public List<KanjiNode> getChildNodes() {
        return nodes;
    }

    public String getElement() {
        return element;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isLeaf() {
        return nodes.size() == 0;
    }

    public boolean isRoot() {
        return depth == 1;
    }
}
