package com.halfplatepoha.jisho.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class VerbInflection implements Parcelable {
    private String tense;
    private String affirmative;
    private String negative;

    public VerbInflection(String tense, String affirmative, String negative) {
        this.affirmative = affirmative;
        this.negative = negative;
        this.tense = tense;
    }

    protected VerbInflection(Parcel in) {
        tense = in.readString();
        affirmative = in.readString();
        negative = in.readString();
    }

    public static final Creator<VerbInflection> CREATOR = new Creator<VerbInflection>() {
        @Override
        public VerbInflection createFromParcel(Parcel in) {
            return new VerbInflection(in);
        }

        @Override
        public VerbInflection[] newArray(int size) {
            return new VerbInflection[size];
        }
    };

    public String getAffirmative() {
        return affirmative;
    }

    public String getNegative() {
        return negative;
    }

    public String getTense() {
        return tense;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tense);
        dest.writeString(affirmative);
        dest.writeString(negative);
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_NONE, TYPE_KURU_VERB, TYPE_SURU_VERB, TYPE_GODAN, TYPE_ICHIDAN})
    public @interface Type {};

    public static final String TYPE_NONE = "none";
    public static final String TYPE_KURU_VERB = "Kuru verb";
    public static final String TYPE_SURU_VERB = "Suru verb";
    public static final String TYPE_GODAN = "Godan verb";
    public static final String TYPE_ICHIDAN = "Ichidan verb";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({NON_PAST, NON_PAST_POLITE, PAST, PAST_POLITE, TE_FORM, POTENTIAL, PASSIVE, CAUSATIVE, CAUSATIVE_PASSIVE, IMPERATIVE})
    public @interface Inflection {};

    public static final String NON_PAST = "Non Past";
    public static final String NON_PAST_POLITE = "Non Past, polite";
    public static final String PAST = "Past";
    public static final String PAST_POLITE = "Past, polite";
    public static final String TE_FORM = "Te-form";
    public static final String POTENTIAL = "Potential";
    public static final String PASSIVE = "Passive";
    public static final String CAUSATIVE = "Causative";
    public static final String CAUSATIVE_PASSIVE = "Causative Passive";
    public static final String IMPERATIVE = "Imperative";
}