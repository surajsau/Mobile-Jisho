package com.halfplatepoha.jisho.offline;

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
}
