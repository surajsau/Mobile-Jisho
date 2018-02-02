package com.halfplatepoha.jisho.rxviews;

import com.halfplatepoha.jisho.view.CustomEditText;

/**
 * Created by surjo on 02/02/18.
 */

public class RxTextView {

    public static InitialValueObservable<CharSequence> textChanges(CustomEditText et) {
        return new TextViewTextObservable(et);
    }
}
