package com.halfplatepoha.jisho.v2.settings;

import android.support.annotation.ColorRes;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

import pub.devrel.easypermissions.AfterPermissionGranted;

/**
 * Created by surjo on 20/01/18.
 */

public interface SettingsContract {

    interface View extends BaseView {

        void showExportDB();

        @ColorRes
        int colorOn();

        @ColorRes
        int colorOff();

        void setOfflineColor(@ColorRes int color);

        void setOfflineCheck(boolean isOffline);

        void openGmail(String title);

        void setVersion(String version);
    }

    interface Presenter extends IPresenter {

        void clickUpdate();

        void exportDB();

        void report();

    }

    interface File {

        void checkStoragePersmissionAndStartDownload();

        java.io.File getRealmFile();

    }
}
