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

    String EXTRA_OFFLINE_STATUS = "offline_status";
    String OFFLINE_STATUS_BROADCAST_FILTER = "offline_status_broadcast_filter";
    String DOWNLOAD_BROADCAST_FILTER = "download_broadcast_filter";

    String DICTIONARY_FILE_NAME = "dictionary.db";

    String STORAGE_DIRECTORY = Environment.getExternalStorageDirectory() + "/Jisho";
}
