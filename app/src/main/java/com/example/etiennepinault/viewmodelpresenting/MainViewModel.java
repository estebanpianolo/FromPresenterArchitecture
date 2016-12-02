package com.example.etiennepinault.viewmodelpresenting;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.etiennepinault.viewmodelpresenting.commons.ViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

//@State
public class MainViewModel
        extends ViewModel {

    /*
    @BehaviorSubject @State
    String email = "";

    @BehaviorSubject @State
    String pass = "";

    @BehaviorSubject
    boolean submitButtonEnabled = false;
*/

    private BehaviorSubject<String> email = BehaviorSubject.create("");
    private BehaviorSubject<String> pass = BehaviorSubject.create("");

    private PublishSubject<String> defaultEmail = PublishSubject.create();
    private PublishSubject<String> defaultPass = PublishSubject.create();

    private BehaviorSubject<Boolean> submitButtonEnabled = BehaviorSubject.create(false);

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        this.email.onNext(email);
    }

    public Observable<String> emailObs() {
        return email;
    }

    public String getPass() {
        return pass.getValue();
    }

    public void setPass(String pass) {
        this.pass.onNext(pass);
    }

    public Observable<String> passObs() {
        return pass;
    }

    public Observable<String> defaultEmailObs() {
        return defaultEmail;
    }

    public void setDefaultEmail(String defaultEmail) {
        this.defaultEmail.onNext(defaultEmail);
    }

    public Observable<String> defaultPassObs() {
        return defaultPass;
    }

    public void setDefaultPass(String defaultPass) {
        this.defaultPass.onNext(defaultPass);
    }

    void setSubmitButtonEnabled(boolean enabled) {
        this.submitButtonEnabled.onNext(enabled);
    }

    Observable<Boolean> submitButtonEnabled() {
        return submitButtonEnabled;
    }

    @Override protected void restoreState(@Nullable Parcelable state) {

    }

    @Nullable @Override protected Parcelable saveState() {
        return null;
    }
}
