package com.halfplatepoha.jisho.v2

import android.app.Application
import com.halfplatepoha.jisho.v2.koin.*

import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

class JishoApp : Application() {

    val defaultConfiguration: RealmConfiguration by inject(named(REALM_DEFAULT_CONFIG))

    val offlineConfiguration: RealmConfiguration by inject(named(REALM_OFFLINE_CONFIG))

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin {
            androidLogger()
            androidContext(this@JishoApp)
            modules(appModule
                    + dataModule
                    + homeModule
                    + searchFragmentModule
                    + historyFragmentModule
                    + favoriteFragmentModule)
        }

        Realm.deleteRealm(offlineConfiguration)
        Realm.setDefaultConfiguration(defaultConfiguration)
    }

}
