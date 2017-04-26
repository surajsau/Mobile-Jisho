package com.halfplatepoha.jisho.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class VerbInflection {
    private String affirmative;
    private String negative;

    public VerbInflection(String affirmative, String negative) {
        this.affirmative = affirmative;
        this.negative = negative;
    }

    public String getAffirmative() {
        return affirmative;
    }

    public String getNegative() {
        return negative;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_NONE, TYPE_KURU_VERB, TYPE_SURU_VERB, TYPE_GODAN, TYPE_ICHIDAN})
    public @interface Type {};

    public static final String TYPE_NONE = "none";
    public static final String TYPE_KURU_VERB = "Kuru verb";
    public static final String TYPE_SURU_VERB = "Suru verb";
    public static final String TYPE_GODAN = "Godan verb";
    public static final String TYPE_ICHIDAN = "Ichidan verb";
}