package com.example.etiennepinault.viewmodelpresenting;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import java.util.concurrent.TimeUnit;

public class TimeOutInteractor {

    private int timeout;

    public TimeOutInteractor setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public Subscription execute (Observer<String> observer) {
        Observable<String> obs = Observable.create(subscriber -> {
            subscriber.onNext("petit coucou");
            subscriber.onCompleted();
        });
        return obs
                .delay(timeout, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
