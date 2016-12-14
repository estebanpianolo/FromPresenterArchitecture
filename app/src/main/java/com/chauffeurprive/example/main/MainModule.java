package com.chauffeurprive.example.main;

import com.chauffeurprive.core.mvp.scopes.ActivityScope;
import com.chauffeurprive.example.interactors.TimeOutInteractor;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    @ActivityScope
    @Provides MainViewModel provideMainViewModel() {
        return new MainViewModel();
    }

    @ActivityScope
    @Provides TimeOutInteractor provideTimeOutInteractor() {
        return new TimeOutInteractor();
    }
}
