package com.halfplatepoha.jisho.offline;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by surjo on 23/05/17.
 */

public class DbSchema {

    public static final int TYPE_KANA = 101;
    public static final int TYPE_ROMAJI = 102;
    public static final int TYPE_KANJI = 103;
    public static final int TYPE_ENGLISH = 104;

    @Retention(value = RetentionPolicy.RUNTIME)
    @IntDef({TYPE_KANA, TYPE_ROMAJI, TYPE_KANJI, TYPE_ENGLISH})
    public @interface SearchType{}

    public static final class KanjiTable {
        public static final String NAME = "Jmdict_Kanji_Element";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String ENTRY_ID = "ENTRY_ID";
            public static final String VALUE = "VALUE";

        }
    }

    public static final class ReadingTable {
        public static final String NAME = "Jmdict_Reading_Element";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String ENTRY_ID = "ENTRY_ID";
            public static final String VALUE = "VALUE";
            public static final String IS_TRUE_READING = "NO_KANJI";
        }
    }

    public static final class ReadingRelationTable {
        public static final String NAME = "Jmdict_Reading_Relation";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String ENTRY_ID = "ENTRY_ID";
            public static final String READING_ELEMENT_ID = "READING_ELEMENT_ID";
            public static final String VALUE = "VALUE";
        }
    }

    public static final class SenseTable {
        public static final String NAME = "Jmdict_Sense_Element";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String ENTRY_ID = "ENTRY_ID";
        }
    }

    public static final class SensePosTable {
        public static final String NAME = "Jmdict_Sense_Pos";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String SENSE_ID = "SENSE_ID";
            public static final String VALUE = "VALUE";
        }
    }

    public static final class SenseFieldTable {
        public static final String NAME = "Jmdict_Sense_Field";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String SENSE_ID = "SENSE_ID";
            public static final String VALUE = "VALUE";
        }
    }

    public static final class SenseDialectTable {
        public static final String NAME = "Jmdict_Sense_Dialect";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String SENSE_ID = "SENSE_ID";
            public static final String VALUE = "VALUE";
        }
    }

    public static final class GlossTable {
        public static final String NAME = "Jmdict_Gloss";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String ENTRY_ID = "ENTRY_ID";
            public static final String SENSE_ID = "SENSE_ID";
            public static final String VALUE = "VALUE";
        }
    }

    public static final class PriorityTable {
        public static final String NAME = "Jmdict_Priority";

        public static final class Cols {
            public static final String _ID = "_ID";
            public static final String ENTRY_ID = "ENTRY_ID";
            public static final String VALUE = "VALUE";
            public static final String TYPE = "TYPE";
        }
    }
}
