package com.chauffeurprive.core.mvp;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class Presenter<VM extends ViewModel, S extends Parcelable> {

    protected final @NonNull VM viewModel;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @SuppressWarnings({ "ConstantConditions", "unused" })
    private Presenter() {this.viewModel = null;}

    public Presenter(@NonNull VM viewModel) {this.viewModel = viewModel;}

    final protected void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    protected abstract void restoreState(@Nullable S state);

    protected abstract @Nullable S saveState();

    final protected void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    final void onDestroy() {
        destroy();
        subscriptions.clear();
    }

    abstract protected void destroy();
}
