package com.example.etiennepinault.viewmodelpresenting.commons;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import rx.subscriptions.CompositeSubscription;

public class BaseFragment<P extends Presenter<VM>, VM extends ViewModel>
        extends Fragment {

    protected final @NonNull VM viewModel = new ViewModel.Factory<VM>().build(this);
    protected final @NonNull P presenter = new Presenter.Factory<P>().build(this, viewModel);

    private CompositeSubscription lifeTimeSubscriptions = new CompositeSubscription();
    private CompositeSubscription foregroundSubscriptions = new CompositeSubscription();

    @Override public void onStart() {
        super.onStart();
        subscribeForLifeTime(lifeTimeSubscriptions);
    }

    protected void subscribeForLifeTime(CompositeSubscription subscriptions) {

    }


    @Override public void onResume() {
        super.onResume();
        subscribeForForeground(foregroundSubscriptions);
    }

    protected void subscribeForForeground(CompositeSubscription subscriptions) {

    }

    @Override public void onPause() {
        super.onPause();
        foregroundSubscriptions.clear();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getStateKey(), viewModel.saveState());
    }

    @Override public void onStop() {
        super.onStop();
        lifeTimeSubscriptions.clear();
    }

    private String getStateKey() {
        return "savedState_" + getClass().getName();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}


