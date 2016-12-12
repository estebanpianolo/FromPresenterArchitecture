package com.example.etiennepinault.viewmodelpresenting.interactors;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorsModule {

    @Provides static TimeOutInteractor provideTimeOutInteractor() {
        return new TimeOutInteractor();
    }
}
