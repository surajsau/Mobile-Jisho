package com.halfplatepoha.jisho.offline.utils;

import android.text.TextUtils;

import com.halfplatepoha.jisho.offline.DbSchema;

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

    public static String getSenseElementsQuery() {
        String select = "SELECT se." + DbSchema.SenseTable.Cols._ID + ", group_concat(pos."
                + DbSchema.SensePosTable.Cols.VALUE + ", '§') AS pos_value, group_concat(foa."
                + DbSchema.SenseFieldTable.Cols.VALUE + ", '§') AS foa_value, group_concat(dial."
                + DbSchema.SenseDialectTable.Cols.VALUE + ", '§') AS dial_value, group_concat(gloss."
                + DbSchema.GlossTable.Cols.VALUE + ", '§') AS gloss_value ";

        String from = "FROM " + DbSchema.SenseTable.NAME + " AS se ";

        String join = "LEFT JOIN " + DbSchema.SensePosTable.NAME + " AS pos ON pos."
                + DbSchema.SensePosTable.Cols.SENSE_ID + " = se." + DbSchema.SenseTable.Cols._ID
                + " LEFT JOIN " + DbSchema.SenseFieldTable.NAME + " AS foa ON foa."
                + DbSchema.SenseFieldTable.Cols.SENSE_ID + " = se." + DbSchema.SenseTable.Cols._ID
                + " LEFT JOIN " + DbSchema.SenseDialectTable.NAME + " AS dial ON dial."
                + DbSchema.SenseDialectTable.Cols.SENSE_ID + " = se." + DbSchema.SenseTable.Cols._ID
                + " LEFT JOIN " + DbSchema.GlossTable.NAME + " AS gloss ON gloss."
                + DbSchema.GlossTable.Cols.SENSE_ID + " = se." + DbSchema.SenseTable.Cols._ID + " ";

        String where = "WHERE se." + DbSchema.SenseTable.Cols.ENTRY_ID + " = ? ";

        String groupBy = "GROUP BY se." + DbSchema.SenseTable.Cols._ID;

        return select + from + join + where + groupBy;
    }

    public static String getReadingElementsQuery() {
        String select = "SELECT r." + DbSchema.ReadingTable.Cols._ID + ", r."
                + DbSchema.ReadingTable.Cols.VALUE + " AS read_value" + ", r."
                + DbSchema.ReadingTable.Cols.IS_TRUE_READING + ", group_concat(rel."
                + DbSchema.ReadingTable.Cols.VALUE + ", '§') AS rel_value ";

        String from = "FROM " + DbSchema.ReadingTable.NAME + " AS r ";

        String join = "LEFT JOIN " + DbSchema.ReadingRelationTable.NAME + " AS rel ON rel."
                + DbSchema.ReadingRelationTable.Cols.READING_ELEMENT_ID + " = r."
                + DbSchema.ReadingTable.Cols._ID + " ";

        String where = "WHERE r." + DbSchema.ReadingTable.Cols.ENTRY_ID + " = ? ";

        String groupBy = "GROUP BY r." + DbSchema.ReadingTable.Cols._ID;

        return select + from + join + where + groupBy;
    }

    public static List<String> formatString(String stringToFormat) {
        if(!TextUtils.isEmpty(stringToFormat))
            return new ArrayList<>(new LinkedHashSet<>(Arrays.asList(stringToFormat.split("§"))));
        else
            return Collections.emptyList();
    }
}
