package com.halfplatepoha.jisho.settings;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 20/12/17.
 */

public class SettingsPresenter extends BasePresenter<SettingsContract.View> implements SettingsContract.Presenter {

    @Inject
    public SettingsPresenter(SettingsContract.View view) {
        super(view);
    }

    @Override
    public void clickUpdate() {
        view.checkStoragePersmissionAndStartDownload();
    }
}
