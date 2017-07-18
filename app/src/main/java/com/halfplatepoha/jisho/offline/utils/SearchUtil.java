package com.halfplatepoha.jisho.offline.utils;

import com.halfplatepoha.jisho.offline.DbSchema;
import com.halfplatepoha.jisho.offline.WanaKanaJava;

/**
 * Created by surjo on 07/07/17.
 */

public class SearchUtil {

    public static @DbSchema.SearchType int getSearchType(String s) {
        boolean containsKana = false;

        for (char c : s.toCharArray()) {
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
                return DbSchema.TYPE_KANJI;
            else if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HIRAGANA
                    || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA)
                containsKana = true;
        }

        if (containsKana)
            return DbSchema.TYPE_KANA;
        else {
            WanaKanaJava wk = new WanaKanaJava(false);
            String kanaForm = wk.toKana(s);

            for (char c : kanaForm.toCharArray()) {
                if (Character.UnicodeBlock.of(c) != Character.UnicodeBlock.HIRAGANA
                        && Character.UnicodeBlock.of(c) != Character.UnicodeBlock.KATAKANA)
                    return DbSchema.TYPE_ENGLISH;
            }

            return DbSchema.TYPE_ROMAJI;
        }
    }

    public static boolean isAllStringUpperCase(String s) {
        for(char c : s.toCharArray()) {
            if(!Character.isUpperCase(c) && !Character.isWhitespace(c))
                return false;
        }

        return true;
    }


}
