package com.example.etiennepinault.viewmodelpresenting.commons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.subscriptions.CompositeSubscription;

import java.io.Serializable;

public class Activity<P extends Presenter<VM>, VM extends ViewModel>
        extends AppCompatActivity {

    protected final @NonNull VM viewModel = new ViewModel.Factory<VM>().build(this);
    protected final @NonNull P presenter = new Presenter.Factory<P>().build(this, viewModel);

    private CompositeSubscription lifeTimeSubscriptions = new CompositeSubscription();
    private CompositeSubscription foregroundSubscriptions = new CompositeSubscription();

    private static final String TAG = "Activity";

    public Activity() {
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Serializable savedState = null;
        if(savedInstanceState != null && savedInstanceState.containsKey(getStateKey())) {
            Object state = savedInstanceState.get(getStateKey());
            if(state instanceof Serializable) {
                savedState = (Serializable) state;
            }
        }
        viewModel.restoreState(savedState);

        Log.e(TAG, "onCreate: " + savedInstanceState);
    }

    @Override protected void onStart() {
        super.onStart();
        subscribeForLifeTime(lifeTimeSubscriptions);
    }

    protected void subscribeForLifeTime(CompositeSubscription lifeTimeSubscriptions) {

    }

    @Override protected void onResume() {
        super.onResume();
        subscribeForForeground(foregroundSubscriptions);
    }

    protected void subscribeForForeground(CompositeSubscription foregroundSubscriptions) {

    }

    @Override protected void onPause() {
        super.onPause();
        foregroundSubscriptions.clear();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getStateKey(), viewModel.saveState());
    }

    private String getStateKey() {
        return "savedState_" + getClass().getName();
    }


}
