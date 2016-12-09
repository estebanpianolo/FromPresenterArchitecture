package com.example.etiennepinault.viewmodelpresenting;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.etiennepinault.viewmodelpresenting.commons.Presenter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

import java.util.concurrent.TimeUnit;

public class MainPresenter
        extends Presenter<MainViewModel, Parcelable> {

    private Subscription submitSubscription;

    @NonNull final private BehaviorSubject<String> email = BehaviorSubject.create();
    @NonNull final private BehaviorSubject<String> password = BehaviorSubject.create();

    public MainPresenter(@NonNull MainViewModel viewModel) {
        super(viewModel);

        Observable.combineLatest(email,
                                 password,
                                 (email, pass) ->
                                         email.length() > 0 && pass.length() > 0
        ).subscribe(viewModel::setSubmitButtonEnabled);
    }

    @Override protected void restoreState(@Nullable Parcelable state) {

    }

    @Nullable @Override protected Parcelable saveState() {
        return null;
    }

    void emailChanged(String s) {
        email.onNext(s);
    }

    void passChanged(String s) {
        password.onNext(s);
    }

    void defaultViewClicked() {
        viewModel.setDefaultEmail("etienne@test.cp");
    }

    void submitViewClicked() {
        unsubscribe(submitSubscription);

        Observable<String> obs = Observable.create(subscriber -> {
            subscriber.onNext("petit coucou");
            subscriber.onCompleted();
        });
        submitSubscription = obs
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    clearFields();
                });
    }

    private void clearFields() {
        viewModel.setDefaultPass("");
        viewModel.setDefaultEmail("");
    }

    @Override protected void destroy() {
        unsubscribe(submitSubscription);
        submitSubscription = null;
    }
}
