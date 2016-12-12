package com.example.etiennepinault.viewmodelpresenting.interactors;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import java.util.concurrent.TimeUnit;

public class TimeOutInteractor {

    private int timeout;

    public TimeOutInteractor setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public Observable<String> execute () {
        Observable<String> obs = Observable.create(subscriber -> {
            subscriber.onNext("petit coucou");
            subscriber.onCompleted();
        });
        return obs
                .delay(timeout, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
