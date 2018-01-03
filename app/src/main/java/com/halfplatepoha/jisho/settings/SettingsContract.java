package com.halfplatepoha.jisho.settings;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 20/12/17.
 */

public interface SettingsContract {

    interface View extends BaseView {

        void checkStoragePersmissionAndStartDownload();
    }

    interface Presenter extends IPresenter {

        void clickUpdate();

    }

    interface Async {

        void consumeEdict();

        void consumeTanaka();

        void consumeKanjiDic();

    }
}
