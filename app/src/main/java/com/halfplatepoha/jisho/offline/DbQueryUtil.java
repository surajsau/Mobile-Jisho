package com.halfplatepoha.jisho.offline;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by surjo on 05/07/17.
 */

public class DbQueryUtil {

    public static String getEnglishSearchQuery(boolean isWildCard) {
        String searchType = isWildCard ? "LIKE" : "=";

        String select = "SELECT " + "re." + DbSchema.ReadingTable.Cols.ENTRY_ID + ", " +
                "group_concat(ke." + DbSchema.KanjiTable.Cols.VALUE + ", '§') AS kanji_value, " +
                "group_concat(re." + DbSchema.ReadingTable.Cols.VALUE + ", '§') AS read_value, " +
                "group_concat(gloss." + DbSchema.GlossTable.Cols.VALUE + ", '§') AS gloss_value ";

        String from = "FROM " + DbSchema.ReadingTable.NAME + " AS re ";

        String join = "LEFT JOIN " +
                DbSchema.KanjiTable.NAME + " AS ke ON re." + DbSchema.ReadingTable.Cols.ENTRY_ID + " = " + "ke." + DbSchema.KanjiTable.Cols.ENTRY_ID +
                " JOIN " +
                DbSchema.GlossTable.NAME + " AS gloss ON re." + DbSchema.ReadingTable.Cols.ENTRY_ID + " = " + "gloss." + DbSchema.GlossTable.Cols.ENTRY_ID + " ";

        String where = "WHERE gloss." + DbSchema.GlossTable.Cols.ENTRY_ID + " IN ";

        String whereSubQuery = "(SELECT " + DbSchema.GlossTable.Cols.ENTRY_ID + " FROM " + DbSchema.GlossTable.NAME + " WHERE VALUE " + searchType + "?) ";

        String groupBy = "GROUP BY re." + DbSchema.ReadingTable.Cols.ENTRY_ID;

        return select + from + join + where + whereSubQuery + groupBy;
    }

    public static String getKanjiSearchQuery(boolean isWildCard) {
        String searchType = isWildCard ? "LIKE" : "=";

        String select = "SELECT " + "re." + DbSchema.ReadingTable.Cols.ENTRY_ID + ", " +
                "group_concat(ke." + DbSchema.KanjiTable.Cols.VALUE + ", '§') AS kanji_value, " +
                "group_concat(re." + DbSchema.ReadingTable.Cols.VALUE + ", '§') AS read_value, " +
                "group_concat(gloss." + DbSchema.GlossTable.Cols.VALUE + ", '§') AS gloss_value ";

        String from = "FROM " + DbSchema.ReadingTable.NAME + " AS re ";

        String join = "LEFT JOIN " +
                DbSchema.KanjiTable.NAME + " AS ke ON re." + DbSchema.ReadingTable.Cols.ENTRY_ID + " = " + "ke." + DbSchema.KanjiTable.Cols.ENTRY_ID +
                " JOIN " +
                DbSchema.GlossTable.NAME + " AS gloss ON re." + DbSchema.ReadingTable.Cols.ENTRY_ID + " = " + "gloss." + DbSchema.GlossTable.Cols.ENTRY_ID + " ";

        String where = "WHERE ke." + DbSchema.GlossTable.Cols.ENTRY_ID + " IN ";

        String whereSubQuery = "(SELECT " + DbSchema.KanjiTable.Cols.ENTRY_ID + " FROM " + DbSchema.KanjiTable.NAME + " WHERE VALUE " + searchType + "?) ";

        String groupBy = "GROUP BY re." + DbSchema.ReadingTable.Cols.ENTRY_ID;

        return select + from + join + where + whereSubQuery + groupBy;
    }

    public static String getKanaSearchQuery(boolean isWildCard) {
        String searchType = isWildCard ? "LIKE" : "=";

        String select = "SELECT " + "re." + DbSchema.ReadingTable.Cols.ENTRY_ID + ", " +
                "group_concat(ke." + DbSchema.KanjiTable.Cols.VALUE + ", '§') AS kanji_value, " +
                "group_concat(re." + DbSchema.ReadingTable.Cols.VALUE + ", '§') AS read_value, " +
                "group_concat(gloss." + DbSchema.GlossTable.Cols.VALUE + ", '§') AS gloss_value ";

        String from = "FROM " + DbSchema.ReadingTable.NAME + " AS re ";

        String join = "LEFT JOIN " +
                DbSchema.KanjiTable.NAME + " AS ke ON re." + DbSchema.ReadingTable.Cols.ENTRY_ID + " = " + "ke." + DbSchema.KanjiTable.Cols.ENTRY_ID +
                " JOIN " +
                DbSchema.GlossTable.NAME + " AS gloss ON re." + DbSchema.ReadingTable.Cols.ENTRY_ID + " = " + "gloss." + DbSchema.GlossTable.Cols.ENTRY_ID + " ";

        String where = "WHERE re." + DbSchema.ReadingTable.Cols.ENTRY_ID + " IN ";

        String whereSubQuery = "(SELECT " + DbSchema.ReadingTable.Cols.ENTRY_ID + " FROM " + DbSchema.ReadingTable.NAME + " WHERE VALUE " + searchType + "?) ";

        String groupBy = "GROUP BY re." + DbSchema.ReadingTable.Cols.ENTRY_ID;

        return select + from + join + where + whereSubQuery + groupBy;
    }

    public static List<String> formatString(String stringToFormat) {
        if(!TextUtils.isEmpty(stringToFormat))
            return new ArrayList<>(new LinkedHashSet<>(Arrays.asList(stringToFormat.split("§"))));
        else
            return Collections.emptyList();
    }
}
