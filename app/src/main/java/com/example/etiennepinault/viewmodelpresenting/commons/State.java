package com.example.etiennepinault.viewmodelpresenting.commons;

import android.os.Bundle;
import android.os.Parcelable;

public class State {

    static Parcelable getState(Bundle savedInstanceState, String key) {
        Parcelable parcelable = null;
        if(savedInstanceState != null && savedInstanceState.containsKey(key)) {
            Object state = savedInstanceState.get(key);
            if(state instanceof Parcelable) {
                parcelable = (Parcelable) state;
            }
        }
        return parcelable;
    }
}
