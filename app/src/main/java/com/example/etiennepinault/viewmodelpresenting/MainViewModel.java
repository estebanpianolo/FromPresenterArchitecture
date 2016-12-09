package com.example.etiennepinault.viewmodelpresenting;

import com.example.etiennepinault.viewmodelpresenting.commons.ViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class MainViewModel
        extends ViewModel {

    private BehaviorSubject<String> email = BehaviorSubject.create("");
    private BehaviorSubject<String> pass = BehaviorSubject.create("");

    private PublishSubject<String> defaultEmail = PublishSubject.create();
    private PublishSubject<String> defaultPass = PublishSubject.create();

    private BehaviorSubject<Boolean> submitButtonEnabled = BehaviorSubject.create(false);

    void setEmail(String email) {
        this.email.onNext(email);
    }

    Observable<String> emailObs() {
        return email;
    }

    void setPass(String pass) {
        this.pass.onNext(pass);
    }

    Observable<String> passObs() {
        return pass;
    }

    void setDefaultEmail(String defaultEmail) {
        this.defaultEmail.onNext(defaultEmail);
    }

    Observable<String> defaultEmailObs() {
        return defaultEmail;
    }

    void setDefaultPass(String defaultPass) {
        this.defaultPass.onNext(defaultPass);
    }

    Observable<String> defaultPassObs() {
        return defaultPass;
    }

    void setSubmitButtonEnabled(boolean enabled) {
        this.submitButtonEnabled.onNext(enabled);
    }

    Observable<Boolean> submitButtonEnabled() {
        return submitButtonEnabled;
    }
}
