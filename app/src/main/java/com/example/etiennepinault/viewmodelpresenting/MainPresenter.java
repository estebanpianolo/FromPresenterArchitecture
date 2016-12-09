package com.example.etiennepinault.viewmodelpresenting;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.etiennepinault.viewmodelpresenting.commons.Presenter;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

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

        submitSubscription = new TimeOutInteractor().setTimeout(1).execute(new Observer<String>() {
            @Override public void onCompleted() {
                clearFields();
            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(String s) {

            }
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
