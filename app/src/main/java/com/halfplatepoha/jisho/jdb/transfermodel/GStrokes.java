package com.halfplatepoha.jisho.jdb.transfermodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by surjo on 27/12/17.
 */

public class GStrokes {

    @SerializedName("paths")
    public List<String> paths;

    @SerializedName("elements")
    public List<GElement> elements;

    @SerializedName("name")
    public String name;

}
