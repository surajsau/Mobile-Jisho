package com.halfplatepoha.jisho.utils;

import android.os.Environment;

/**
 * Created by surjo on 21/04/17.
 */

public interface IConstants {
    String BASE_URL = "http://jisho.org/api/v1/";

    String PREF_SHOW_NEW = "show_new";
    String EXTRA_SEARCH_TERM = "search_term";
    String PREF_OFFLINE_MODE = "offline_mode";
    String PREF_VERSION_CODE = "version_code";

    int FILE_SIZE = 77;

    String EXTRA_OFFLINE_STATUS = "offline_status";
    String OFFLINE_STATUS_BROADCAST_FILTER = "offline_status_broadcast_filter";
    String DOWNLOAD_BROADCAST_FILTER = "download_broadcast_filter";

    String DICTIONARY_FILE_NAME = "dictionary.db";

    String STORAGE_DIRECTORY = Environment.getExternalStorageDirectory() + "/Mobile Jisho";

    String JMDICT = "http://www.edrdg.org/jmdict/j_jmdict.html";
    String JMEDICT = "http://www.edrdg.org/enamdict/enamdict_doc.html";
    String KANJIDIC2 = "http://www.edrdg.org/kanjidic/kanjd2index.html";
    String RADKFILE = "http://nihongo.monash.edu/kradinf.html";
    String EDRDG = "http://www.edrdg.org/";
    String EDRDG_LICENSE = "http://www.edrdg.org/edrdg/licence.html";

    int MAX_DOWNLOAD_RETRY = 3;
}
