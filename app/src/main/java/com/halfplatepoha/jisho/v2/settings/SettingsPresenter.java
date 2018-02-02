package com.halfplatepoha.jisho.v2.settings;

import com.halfplatepoha.jisho.BuildConfig;
import com.halfplatepoha.jisho.JishoPreference;
import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.v2.data.IDataProvider;
import com.halfplatepoha.jisho.utils.IConstants;

import javax.inject.Inject;

/**
 * Created by surjo on 20/01/18.
 */

public class SettingsPresenter extends BasePresenter<SettingsContract.View> implements SettingsContract.Presenter {

    private SettingsContract.File file;

    private IDataProvider dataProvider;

    @Inject
    public SettingsPresenter(SettingsContract.View view,
                             SettingsContract.File file,
                             IDataProvider dataProvider) {
        super(view);
        this.file = file;
        this.dataProvider = dataProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);

        view.setOfflineCheck(isOffline);
        view.setOfflineColor(isOffline ? view.colorOn() : view.colorOff());

        if(BuildConfig.DEBUG)
            view.showExportDB();

        view.setVersion("v" + BuildConfig.VERSION_NAME);
    }

    @Override
    public void clickUpdate() {
//        file.checkStoragePersmissionAndStartDownload();
    }

    @Override
    public void exportDB() {
        dataProvider.writeCopyTo(file.getRealmFile());
    }

    @Override
    public void report() {
        view.openGmail("Report: v" + BuildConfig.VERSION_NAME);
    }
}
