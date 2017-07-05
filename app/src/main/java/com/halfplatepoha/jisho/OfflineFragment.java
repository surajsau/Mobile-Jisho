package com.halfplatepoha.jisho;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.halfplatepoha.jisho.offline.DbSchema;
import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.offline.model.ListEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineFragment extends BaseFragment {

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.tvSearchResult)
    TextView tvSearchResult;

    private OfflineDbHelper dbHelper;

    public OfflineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = OfflineDbHelper.getInstance();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_offline;
    }

    @OnClick(R.id.btnSearch)
    public void search() {
        List<ListEntry> entries = dbHelper.searchDictionary(etSearch.getText().toString(), DbSchema.TYPE_ENGLISH);
        if(entries != null) {
            StringBuilder sb = new StringBuilder("");
            for(ListEntry entry : entries) {
                sb.append(entry.getKanji()).append(", ");
            }
            tvSearchResult.setText(sb.toString());
        }
    }
}
