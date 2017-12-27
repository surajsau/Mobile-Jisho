package com.halfplatepoha.jisho.jdb.transfermodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by surjo on 19/12/17.
 */

public class GEntry {

    @SerializedName("ent_seq")
    public long entrySeq;

    @SerializedName("japanese")
    public String japanese;

    @SerializedName("pos")
    public List<String> pos;

    @SerializedName("glosses")
    public List<GGloss> glosses;

    @SerializedName("common")
    public boolean common;

    @SerializedName("furigana")
    public String furigana;

    @SerializedName("tags")
    public List<String> tags;

    @SerializedName("fields")
    public List<String> fields;

    @SerializedName("kanji_tags")
    public List<String> kanjiTags;

    @SerializedName("dialects")
    public List<String> dialects;

    @SerializedName("kana_tags")
    public List<String> kanaTags;

}
