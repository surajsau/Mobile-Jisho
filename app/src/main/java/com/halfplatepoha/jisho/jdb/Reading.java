package com.halfplatepoha.jisho.jdb;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Reading extends RealmObject {

    public String type;
    public String reading;

    @Ignore
    public static final String PINYIN = "pinyin";

    @Ignore
    public static final String KOREAN_ROMAJI = "korean_r";

    @Ignore
    public static final String HANGUL = "korean_h";

    @Ignore
    public static final String KUNYOMI = "ja_kun";

    @Ignore
    public static final String ONYOMI = "ja_on";

}