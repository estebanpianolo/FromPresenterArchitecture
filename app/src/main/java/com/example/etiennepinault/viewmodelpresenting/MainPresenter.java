package com.example.etiennepinault.viewmodelpresenting;

import android.support.annotation.NonNull;

import com.example.etiennepinault.viewmodelpresenting.commons.Presenter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import java.util.concurrent.TimeUnit;

public class MainPresenter
        extends Presenter<MainViewModel> {

    private Subscription submitSubscription;

    public MainPresenter(@NonNull MainViewModel viewModel) {
        super(viewModel);

        Observable.combineLatest(viewModel.emailObs(),
                                 viewModel.passObs(),
                                 (email, pass) ->
                                         email.length() > 0 && pass.length() > 0
        ).subscribe(viewModel::setSubmitButtonEnabled);
    }

    void emailChanged(String s) {
        viewModel.setEmail(s);
    }

    void passChanged(String s) {
        viewModel.setPass(s);
    }

    public void defaultViewClicked() {
        viewModel.setDefaultEmail("etienne@test.cp");
    }

    public void submitViewClicked() {
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
