package com.example.etiennepinault.viewmodelpresenting;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.etiennepinault.viewmodelpresenting.commons.Presenter;
import com.example.etiennepinault.viewmodelpresenting.interactors.TimeOutInteractor;
import rx.Observable;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;

public class MainPresenter
        extends Presenter<MainViewModel, Parcelable> {

    @NonNull final private BehaviorSubject<String> email = BehaviorSubject.create();
    @NonNull final private BehaviorSubject<String> password = BehaviorSubject.create();
    @Inject TimeOutInteractor timeOutInteractor;
    private Subscription submitSubscription;

    public MainPresenter(@NonNull MainViewModel viewModel,
                         @NonNull TimeOutInteractor timeOutInteractor) {
        super(viewModel);
        this.timeOutInteractor = timeOutInteractor;
        init();
    }

    public MainPresenter(@NonNull MainViewModel viewModel) {
        super(viewModel);

        DaggerPresenterComponent.create().inject(this);
        init();
    }

    private void init() {
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
        viewModel.setDefaultPass("coucou");
    }

    void submitViewClicked() {
        unsubscribe(submitSubscription);

        submitSubscription =
                timeOutInteractor.setTimeout(1)
                                 .execute()
                                 .subscribe(o -> {}, Throwable::printStackTrace, this::clearFields);
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
