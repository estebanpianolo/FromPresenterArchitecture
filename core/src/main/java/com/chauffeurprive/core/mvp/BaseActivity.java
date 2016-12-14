package com.chauffeurprive.core.mvp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;

public abstract class BaseActivity<P extends Presenter, VM extends ViewModel>
        extends AppCompatActivity
        implements BaseView.Parent {

    private final BaseView baseView;
    @Inject protected VM viewModel;
    @Inject protected P presenter;

    public BaseActivity() {
        this.baseView = new BaseView(this);
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildDependencies();
        baseView.onCreate(savedInstanceState);
    }

    @Override public void restoreState(Parcelable state) {
        //noinspection unchecked
        presenter.restoreState(state);
    }

    @Override public Parcelable getSaveState() {
        return presenter.saveState();
    }

    @Override protected void onStart() {
        super.onStart();
        baseView.onStart();
    }

    public void subscribeForLifeTime(CompositeSubscription subscriptions) {

    }

    @Override protected void onResume() {
        super.onResume();
        baseView.onResume();
    }

    public void subscribeForForeground(CompositeSubscription subscriptions) {

    }

    @Override protected void onPause() {
        super.onPause();
        baseView.onPause();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        baseView.onSaveInstanceState(outState);
    }

    @Override protected void onStop() {
        super.onStop();
        baseView.onStop();
    }

    @NonNull public String getStateKey() {
        return "savedState_" + getClass().getName();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        baseView.onDestroy();
    }

    @Override public void destroy() {
        presenter.onDestroy();
    }
}
