package com.halfplatepoha.jisho;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halfplatepoha.jisho.db.History;
import com.halfplatepoha.jisho.db.RealmString;

import butterknife.BindView;
import io.realm.Realm;

public class HistoryFragment extends BaseFragment implements HistoryAdapter.OnSearchClickedListener{

    @BindView(R.id.rlHistory)
    RecyclerView rlHistory;

    HistoryAdapter adapter;

    Realm realm;

    HistoryFragmentActionListener listener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HistoryAdapter(getActivity());
        adapter.setSearchClickedListener(this);
        rlHistory.setAdapter(adapter);
        rlHistory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true));

        refreshUI();
    }

    public void setHistoryFragmentActionListener(HistoryFragmentActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    private void refreshUI() {
        History history = realm.where(History.class).findFirst();
        if(history != null && history.getHistory() != null) {
            for(RealmString str : history.getHistory())
                adapter.addHistory(str.getValue());
        }
    }

    @Override
    public void onSearchClicked(String searchString) {
        if(listener != null)
            listener.onSearchHistoryStringSelected(searchString);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_history;
    }

    public interface HistoryFragmentActionListener {
        void onSearchHistoryStringSelected(String searchString);
    }
}
