package com.example.etiennepinault.viewmodelpresenting;

import android.support.annotation.NonNull;

import com.example.etiennepinault.viewmodelpresenting.commons.Presenter;
import rx.Observable;

public class MainPresenter extends Presenter<MainViewModel> {

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
}
