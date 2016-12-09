package com.example.etiennepinault.viewmodelpresenting.commons;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import rx.subscriptions.CompositeSubscription;

public class BaseFragment<P extends Presenter, VM extends ViewModel>
        extends Fragment
        implements BaseView.Parent {

    protected final @NonNull VM viewModel = new ViewModel.Factory<VM>().build(this);
    protected final @NonNull P presenter = new Presenter.Factory<P>().build(this, viewModel);

    private final @NonNull BaseView baseView;
    private CompositeSubscription lifeTimeSubscriptions = new CompositeSubscription();
    private CompositeSubscription foregroundSubscriptions = new CompositeSubscription();

    public BaseFragment() {
        this.baseView = new BaseView(this);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        baseView.onCreate(savedInstanceState);
    }

    @Override public void restoreState(Parcelable state) {
        //noinspection unchecked
        presenter.restoreState(state);
    }

    @Override public Parcelable getSaveState() {
        return presenter.saveState();
    }

    @Override public void onStart() {
        super.onStart();
        baseView.onStart();
    }

    public void subscribeForLifeTime(CompositeSubscription subscriptions) {

    }

    @Override public void onResume() {
        super.onResume();
        baseView.onResume();
    }

    public void subscribeForForeground(CompositeSubscription subscriptions) {

    }

    @Override public void onPause() {
        super.onPause();
        baseView.onPause();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        baseView.onSaveInstanceState(outState);
    }

    @Override public void onStop() {
        super.onStop();
        baseView.onStop();
    }

    public String getStateKey() {
        return "savedState_" + getClass().getName();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        baseView.onDestroy();
    }

    @Override public void destroy() {
        presenter.onDestroy();
    }
}


