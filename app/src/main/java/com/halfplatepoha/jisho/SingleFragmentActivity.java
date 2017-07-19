package com.halfplatepoha.jisho;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleFragmentActivity extends AppCompatActivity {

    public static final int FRAG_ABOUT = 101;
    public static final int FRAG_LICENSE = 102;

    private static final String EXTRA_FRAG_TYPE = "frag_type";
    private static final String EXTRA_FRAG_TITLE = "frag_title";

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    public static Intent getLaunchIntent(Context context, int fragmentType, String title) {
        Intent intent = new Intent(context, SingleFragmentActivity.class);
        intent.putExtra(EXTRA_FRAG_TYPE, fragmentType);
        intent.putExtra(EXTRA_FRAG_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        ButterKnife.bind(this);

        int fragType = getIntent().getIntExtra(EXTRA_FRAG_TYPE, 0);
        String title = getIntent().getStringExtra(EXTRA_FRAG_TITLE);

        initFragment(fragType);
        tvTitle.setText(title);
    }

    private void initFragment(int fragType) {
        switch (fragType) {
            case FRAG_ABOUT:
                openFragment(new AboutFragment());
                break;

            case FRAG_LICENSE:
                openFragment(new LicenseFragment());
                break;
        }
    }

    @OnClick(R.id.back)
    public void back() {
        onBackPressed();
    }

    private void openFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home, fragment)
                .commit();
    }
}
