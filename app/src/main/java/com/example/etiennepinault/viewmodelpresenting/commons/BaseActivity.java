package com.example.etiennepinault.viewmodelpresenting.commons;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.subscriptions.CompositeSubscription;

public class BaseActivity<P extends Presenter, VM extends ViewModel>
        extends AppCompatActivity {

    protected final @NonNull VM viewModel = new ViewModel.Factory<VM>().build(this);
    protected final @NonNull P presenter = new Presenter.Factory<P>().build(this, viewModel);

    private CompositeSubscription lifeTimeSubscriptions = new CompositeSubscription();
    private CompositeSubscription foregroundSubscriptions = new CompositeSubscription();

    private static final String TAG = "BaseActivity";

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parcelable parcelable = null;
        if(savedInstanceState != null && savedInstanceState.containsKey(getStateKey())) {
            Object state = savedInstanceState.get(getStateKey());
            if(state instanceof Parcelable) {
                parcelable = (Parcelable) state;
            }
        }
        //noinspection unchecked
        presenter.restoreState(parcelable);

        Log.e(TAG, "onCreate: " + savedInstanceState);
    }

    @Override protected void onStart() {
        super.onStart();
        subscribeForLifeTime(lifeTimeSubscriptions);
    }

    protected void subscribeForLifeTime(CompositeSubscription subscriptions) {

    }

    @Override protected void onResume() {
        super.onResume();
        subscribeForForeground(foregroundSubscriptions);
    }

    protected void subscribeForForeground(CompositeSubscription subscriptions) {

    }

    @Override protected void onPause() {
        super.onPause();
        foregroundSubscriptions.clear();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getStateKey(), presenter.saveState());
    }

    @Override protected void onStop() {
        super.onStop();
        lifeTimeSubscriptions.clear();
    }

    @NonNull private String getStateKey() {
        return "savedState_" + getClass().getName();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
