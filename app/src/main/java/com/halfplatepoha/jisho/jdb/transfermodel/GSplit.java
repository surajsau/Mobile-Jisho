package com.halfplatepoha.jisho.jdb.transfermodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by surjo on 20/12/17.
 */

public class GSplit {

    @SerializedName("keyword")
    public String keyword;

    @SerializedName("sense")
    public String sense;

    @SerializedName("sentence_form")
    public String sentenceForm;

    @SerializedName("is_good")
    public boolean isGood;

    @SerializedName("reading")
    public String reading;

}
