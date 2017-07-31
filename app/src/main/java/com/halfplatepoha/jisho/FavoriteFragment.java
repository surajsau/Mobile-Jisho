package com.halfplatepoha.jisho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.halfplatepoha.jisho.db.FavouriteWord;
import com.halfplatepoha.jisho.model.Word;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteFragment extends BaseFragment implements FavoritesAdapter.OnFavoriteClickListner {

    @BindView(R.id.rlFavorites)
    RecyclerView rlFavorites;

    private FavoritesAdapter adapter;

    private Realm realm;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new FavoritesAdapter(getActivity());
        adapter.setOnFavoriteClickListener(this);
        rlFavorites.setAdapter(adapter);
        rlFavorites.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    private void refreshUI() {
        RealmResults<FavouriteWord> favs = realm.where(FavouriteWord.class).findAll();
        for(FavouriteWord word : favs)
            adapter.addFavorite(word);
    }

    @Override
    public void onFavoriteClicked(FavouriteWord favWord) {
        Word word = Word.fromFavoriteWord(favWord);

        Intent detailsIntent = DetailsAcitivity.getLaunchIntent(getActivity(), word);
        startActivity(detailsIntent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

}
