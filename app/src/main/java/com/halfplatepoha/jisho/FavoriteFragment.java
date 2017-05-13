package com.halfplatepoha.jisho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.halfplatepoha.jisho.db.FavJapanese;
import com.halfplatepoha.jisho.db.FavLink;
import com.halfplatepoha.jisho.db.FavSense;
import com.halfplatepoha.jisho.db.FavouriteWord;
import com.halfplatepoha.jisho.db.RealmString;
import com.halfplatepoha.jisho.model.Japanese;
import com.halfplatepoha.jisho.model.Link;
import com.halfplatepoha.jisho.model.Sense;
import com.halfplatepoha.jisho.model.Word;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        Word word = new Word();
        word.setIs_common(favWord.is_common());

        ArrayList<Japanese> japList = new ArrayList<>();
        for(FavJapanese favJapanese : favWord.getJapanese()) {
            Japanese japanese = new Japanese();
            japanese.setReading(favJapanese.getReading());
            japanese.setWord(favJapanese.getWord());

            japList.add(japanese);
        }
        word.setJapanese(japList);

        ArrayList<Sense> senseList = new ArrayList<>();
        for(FavSense favSense : favWord.getSenses()) {
            Sense sense = new Sense();

            if(favSense.getEnglish_definitions() != null) {
                ArrayList<String> list = new ArrayList<>();
                for (RealmString defs : favSense.getEnglish_definitions())
                    list.add(defs.getValue());
                sense.setEnglish_definitions(list);
            }

            if(favSense.getInfo() != null) {
                ArrayList<String> list = new ArrayList<>();
                for (RealmString defs : favSense.getInfo())
                    list.add(defs.getValue());
                sense.setInfo(list);
            }

            if(favSense.getParts_of_speech() != null) {
                ArrayList<String> list = new ArrayList<>();
                for (RealmString defs : favSense.getParts_of_speech())
                    list.add(defs.getValue());
                sense.setParts_of_speech(list);
            }

            if(favSense.getTags() != null) {
                ArrayList<String> list = new ArrayList<>();
                for (RealmString defs : favSense.getTags())
                    list.add(defs.getValue());
                sense.setTags(list);
            }

            if(favSense.getSee_also() != null) {
                ArrayList<String> list = new ArrayList<>();
                for (RealmString defs : favSense.getSee_also())
                    list.add(defs.getValue());
                sense.setSee_also(list);
            }

            if(favSense.getLinks() != null) {
                ArrayList<Link> list = new ArrayList<>();
                for(FavLink favLink : favSense.getLinks()) {
                    Link link = new Link();
                    link.setText(favLink.getText());
                    link.setUrl(favLink.getUrl());

                    list.add(link);
                }
                sense.setLinks(list);
            }

            senseList.add(sense);
        }
        word.setSenses(senseList);

        Intent detailsIntent = DetailsAcitivity.getLaunchIntent(getActivity(), word);
        startActivity(detailsIntent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

}
