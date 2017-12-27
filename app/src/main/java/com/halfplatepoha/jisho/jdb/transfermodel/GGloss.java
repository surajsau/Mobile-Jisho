package com.halfplatepoha.jisho.jdb.transfermodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by surjo on 19/12/17.
 */

public class GGloss {

    @SerializedName("field")
    public String field;

    @SerializedName("tags")
    public List<String> tags;

    @SerializedName("common")
    public boolean common;

    @SerializedName("english")
    public String english;

    @SerializedName("related")
    public List<String> related;

}
