package com.chauffeurprive.example;

import android.content.Context;

import com.chauffeurprive.core.mvp.scopes.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides @ApplicationScope
    Context provideContext() {
        return context;
    }
}
