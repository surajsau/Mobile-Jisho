package com.halfplatepoha.jisho.history;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseFragment;
import com.halfplatepoha.jisho.db.History;

import butterknife.BindView;
import io.realm.Realm;

public class HistoryFragment extends BaseFragment<HistoryContract.Presenter> implements HistoryAdapter.OnSearchClickedListener, HistoryContract.View{

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
        rlHistory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));

        refreshUI();
    }

    public void setHistoryFragmentActionListener(HistoryFragmentActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getInstance(getApplication().getAppConfig());
    }

    private void refreshUI() {
        History history = realm.where(History.class).findFirst();
        if(history != null && history.getHistory() != null) {
            for(String str : history.getHistory())
                adapter.addHistory(str);
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
