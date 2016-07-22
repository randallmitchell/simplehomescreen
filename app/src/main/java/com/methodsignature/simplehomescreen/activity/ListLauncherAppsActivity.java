package com.methodsignature.simplehomescreen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.methodsignature.simplehomescreen.R;
import com.methodsignature.simplehomescreen.global.SimpleHomeApp;
import com.methodsignature.simplehomescreen.data.Launchable;
import com.methodsignature.simplehomescreen.data.request.DataRequestManager;
import com.methodsignature.simplehomescreen.view.LaunchableRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by randallmitchell on 7/20/16.
 */
public class ListLauncherAppsActivity extends Activity {

    protected DataRequestManager dataRequestManager;

    @BindView(R.id.list_launcher_apps_activity_list)
    protected LaunchableRecyclerView listView;

    private Subscription launchableAppsSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_launcher_apps_activity);

        ButterKnife.bind(this);

        dataRequestManager = ((SimpleHomeApp) getApplication()).getApplicationComponent().launchableStore();

        listView.setOnLaunchableClickListener(new LaunchableRecyclerView.OnLaunchableClickListener() {
            @Override
            public void onLaunchableClicked(Launchable launchable) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(launchable.getComponentName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        launchableAppsSubscription = dataRequestManager.getLaunchableApps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Launchable>() {
                    @Override
                    public void onCompleted() {
                        listView.getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "[onError]");
                        Toast.makeText(ListLauncherAppsActivity.this, "Error retrieving application list.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Launchable launchable) {
                        listView.addLaunchable(launchable);

                        // update view every so often
                        if (listView.getAdapter().getItemCount() % 10 == 0) {
                            listView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        launchableAppsSubscription.unsubscribe();
        listView.clear();
    }

    @Override
    public void onBackPressed() {
        // don't allow back presses to percolate.
    }
}
