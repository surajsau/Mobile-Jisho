package com.halfplatepoha.jisho.jdb.transfermodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by surjo on 20/12/17.
 */

public class GSentence {

    @SerializedName("split")
    public List<GSplit> splits;

    @SerializedName("sentence")
    public String sentence;

    @SerializedName("english")
    public String english;

}
