package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 20/01/18.
 */

public interface SwitchToOfflineContract {

    interface View extends BaseView {

        void confirm();

        void cancel();

    }

    interface Presenter extends IPresenter {

        void clickConfirm();

        void clickCancel();
    }

}
