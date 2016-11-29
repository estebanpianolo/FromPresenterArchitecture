package com.example.etiennepinault.viewmodelpresenting;

import android.support.annotation.Nullable;

import com.example.etiennepinault.viewmodelpresenting.commons.ViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.io.Serializable;

//@State
public class MainViewModel
        extends ViewModel<MainViewModel.State> {

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

    public void setPass(String pass) {
        this.pass.onNext(pass);
    }

    public String getPass() {
        return pass.getValue();
    }

    public Observable<String> passObs() {
        return pass;
    }

    void setSubmitButtonEnabled(boolean enabled) {
        this.submitButtonEnabled.onNext(enabled);
    }

    Observable<Boolean> submitButtonEnabled() {
        return submitButtonEnabled;
    }

    @Override protected void restoreState(@Nullable MainViewModel.State state) {
        if(state != null) {
            setEmail(state.email);
            setPass(state.pass);
        }
    }

    @Override protected MainViewModel.State saveState() {
        return new State().setEmail(email.getValue()).setPass(pass.getValue());
    }

    static class State implements Serializable {
        String email;
        String pass;

        State setEmail(String email) {
            this.email = email;
            return this;
        }

        State setPass(String pass) {
            this.pass = pass;
            return this;
        }
    }
}
