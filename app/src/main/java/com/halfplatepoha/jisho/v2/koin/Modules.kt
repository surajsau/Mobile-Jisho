package com.halfplatepoha.jisho.v2.koin

import com.halfplatepoha.jisho.db.JishoSchema
import com.halfplatepoha.jisho.v2.IJishoPreference
import com.halfplatepoha.jisho.v2.JishoPreference
import com.halfplatepoha.jisho.v2.realm.JishoMigration
import com.halfplatepoha.jisho.v2.OfflineModule
import com.halfplatepoha.jisho.v2.detail.DetailActivity
import com.halfplatepoha.jisho.v2.experiment.DatabaseProvider
import com.halfplatepoha.jisho.v2.favorite.FavoriteAdapter
import com.halfplatepoha.jisho.v2.favorite.FavoriteFragment
import com.halfplatepoha.jisho.v2.favorite.FavoriteFragmentViewModel
import com.halfplatepoha.jisho.v2.history.HistoryAdapter
import com.halfplatepoha.jisho.v2.history.HistoryFragment
import com.halfplatepoha.jisho.v2.history.HistoryFragmentViewModel
import com.halfplatepoha.jisho.v2.home.HomeViewModel
import com.halfplatepoha.jisho.v2.home.MainActivity
import com.halfplatepoha.jisho.v2.search.SearchFragmentViewModel
import com.halfplatepoha.jisho.v2.realm.RelineMigration
import com.halfplatepoha.jisho.v2.room.AssetRoom
import com.halfplatepoha.jisho.v2.room.OfflineDatabase
import com.halfplatepoha.jisho.v2.search.SearchAdapter
import com.halfplatepoha.jisho.v2.search.SearchFragment
import com.halfplatepoha.jisho.v2.viewmodel.DetailsViewModel
import com.halfplatepoha.jisho.v2.viewmodel.FavoriteViewModel
import com.halfplatepoha.jisho.v2.viewmodel.HistoryViewModel
import com.halfplatepoha.jisho.v2.viewmodel.SearchViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DEFAULT_MIGRATOR = "default_migrator"
const val OFFLINE_MIGRATOR = "offline_migrator"
const val REALM_DEFAULT = "realm_default"
const val REALM_OFFLINE = "realm_offline"
const val REALM_DEFAULT_CONFIG = "realm_default_config"
const val REALM_OFFLINE_CONFIG = "realm_offline_config"

const val OFFLINE_DB = "Japanese4.db"

val appModule = module {

    single<RealmMigration>(named(OFFLINE_MIGRATOR)) { RelineMigration() }

    single<RealmMigration>(named(DEFAULT_MIGRATOR)) { JishoMigration() }

    single<RealmConfiguration>(named(REALM_OFFLINE_CONFIG)) {
        RealmConfiguration.Builder()
                .assetFile("offline.realm")
                .modules(OfflineModule())
                .migration(get(named(OFFLINE_MIGRATOR)))
                .schemaVersion(RelineMigration.DB_VERSION)
                .build()
    }

    single<RealmConfiguration>(named(REALM_DEFAULT_CONFIG)) {
        RealmConfiguration.Builder()
                .modules(JishoSchema())
                .schemaVersion(JishoMigration.DB_VERSION)
                .migration(get(named(DEFAULT_MIGRATOR)))
                .build()
    }

    single<Realm>(named(REALM_DEFAULT)) { Realm.getInstance(get(named(REALM_DEFAULT_CONFIG))) }

    single<Realm>(named(REALM_OFFLINE)) { Realm.getInstance(get(named(REALM_OFFLINE_CONFIG))) }

}

val dataModule = module {

    single<IJishoPreference> { JishoPreference(androidApplication(), "JishoPref") }

    single { AssetRoom.databaseBuilder(androidApplication(),
            OfflineDatabase::class.java,
            "Japanese4.db",
            arrayOf(OfflineDatabase.migration_1548319745_to_1548319746))
            .build()
    }

    single { (get() as OfflineDatabase).entryDao() }

    viewModel { SearchViewModel(get()) }
    viewModel { DetailsViewModel(get(named(REALM_OFFLINE_CONFIG))) }
    viewModel { HistoryViewModel(get(named(REALM_DEFAULT_CONFIG))) }
    viewModel { FavoriteViewModel(get(named(REALM_DEFAULT_CONFIG))) }

}

val searchFragmentModule = module {
    scope(named<SearchFragment>()) {
        viewModel { SearchFragmentViewModel() }
        factory { SearchAdapter() }
    }
}

val historyFragmentModule = module {
    scope(named<HistoryFragment>()) {
        factory { HistoryAdapter() }
        viewModel { HistoryFragmentViewModel() }
    }
}

val homeModule = module {
    scope(named<MainActivity>()) {
        viewModel { HomeViewModel() }
    }
}

val favoriteFragmentModule = module {
    scope(named<FavoriteFragment>()) {
        factory { FavoriteAdapter() }
        viewModel { FavoriteFragmentViewModel() }
    }
}

val detailModule = module {
    scope(named<DetailActivity>()) {

    }
}