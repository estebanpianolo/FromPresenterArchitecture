package com.chauffeurprive.core.mvp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import rx.subscriptions.CompositeSubscription;

class BaseView {

    @NonNull
    final private CompositeSubscription lifeTimeSubscriptions = new CompositeSubscription();
    @NonNull
    final private CompositeSubscription foregroundSubscriptions = new CompositeSubscription();
    @NonNull private Parent parent;

    BaseView(@NonNull Parent parent) {

        this.parent = parent;
    }

    void onCreate(Bundle savedInstanceState) {
        parent.restoreState(State.getState(savedInstanceState, parent.getStateKey()));
    }

    void onStart() {
        parent.subscribeForLifeTime(lifeTimeSubscriptions);
    }

    void onResume() {
        parent.subscribeForForeground(foregroundSubscriptions);
    }

    void onPause() {
        foregroundSubscriptions.clear();
    }

    void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(parent.getStateKey(), parent.getSaveState());
    }

    void onStop() {
        lifeTimeSubscriptions.clear();
    }

    void onDestroy() {
        parent.destroy();
    }

    interface Parent {

        String getStateKey();

        void restoreState(Parcelable state);

        Parcelable getSaveState();

        void subscribeForLifeTime(CompositeSubscription lifeTimeSubscriptions);

        void subscribeForForeground(CompositeSubscription foregroundSubscriptions);

        void destroy();

        void buildDependencies();
    }
}
