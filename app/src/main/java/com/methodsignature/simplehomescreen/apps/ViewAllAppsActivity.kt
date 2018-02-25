package com.methodsignature.simplehomescreen.apps

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.Window
import com.methodsignature.simplehomescreen.R

class ViewAllAppsActivity: FragmentActivity() {

    lateinit var presenter: ViewAllAppsPresenter

    val view: ViewAllAppsView get() = findViewById<LinearViewAllAppsView>(R.id.view_all_apps_activity_view_all_apps_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.do_nothing, R.anim.slide_down)
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.view_all_apps_activity)

        ViewAllAppsActivityScope(this).inject()

        presenter.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onBackPressed() {
        // don't allow back presses to percolate.
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_up, R.anim.do_nothing)
    }
}
