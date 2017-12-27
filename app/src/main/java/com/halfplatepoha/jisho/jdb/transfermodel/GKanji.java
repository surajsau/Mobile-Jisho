package com.halfplatepoha.jisho.jdb.transfermodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GKanji {

    @SerializedName("codepoints")
    public List<GCodepoint> codepoints;

    @SerializedName("dic_numbers")
    public List<GDicNumber> dicNumbers;

    @SerializedName("freq")
    public int freq;

    @SerializedName("grade")
    public int grade;

    @SerializedName("literal")
    public String literal;

    @SerializedName("meanings")
    public List<GMeaning> meanings;

    @SerializedName("query_codes")
    public List<GQueryCode> queryCodes;

    @SerializedName("readings")
    public List<GReading> readings;

    @SerializedName("stroke_count")
    public int strokeCount;

}