package com.chauffeurprive.core.mvp;

import android.content.Context;

import com.chauffeurprive.core.mvp.scopes.ApplicationScope;
import dagger.Component;

@ApplicationScope
@Component(modules = { AppModule.class })
public interface AppComponent {
    Context getContext();
}
