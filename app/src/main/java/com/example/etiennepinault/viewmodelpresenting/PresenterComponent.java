package com.example.etiennepinault.viewmodelpresenting;

import com.example.etiennepinault.viewmodelpresenting.interactors.InteractorsModule;
import dagger.Component;

@Component(modules = InteractorsModule.class)
public interface PresenterComponent {

    void inject(MainPresenter mainPresenter);
}
