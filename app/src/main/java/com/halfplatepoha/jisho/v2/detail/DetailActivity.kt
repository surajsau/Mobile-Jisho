package com.halfplatepoha.jisho.v2.detail

import android.content.Context
import android.content.Intent
import com.halfplatepoha.jisho.R
import com.halfplatepoha.jisho.v2.base.BaseActivity

class DetailActivity : BaseActivity() {

    companion object {

        val EXTRA_ID = "extra_id"

        @JvmStatic
        fun getLaunchIntent(context: Context, id: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.apply {
                putExtra(EXTRA_ID, id)
            }
            return intent
        }
    }

    override val layout: Int
        get() = R.layout.activity_details

}