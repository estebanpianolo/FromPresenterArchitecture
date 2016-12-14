package com.chauffeurprive.example.main;

import com.chauffeurprive.core.mvp.AppComponent;
import com.chauffeurprive.core.mvp.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
