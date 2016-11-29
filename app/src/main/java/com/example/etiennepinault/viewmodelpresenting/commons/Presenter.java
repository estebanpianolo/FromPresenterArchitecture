package com.example.etiennepinault.viewmodelpresenting.commons;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Presenter<VM extends ViewModel> {

    protected final @NonNull VM viewModel;

    @SuppressWarnings("ConstantConditions") private Presenter() {this.viewModel = null;}

    public Presenter(@NonNull VM viewModel) {this.viewModel = viewModel;}

    static class Factory<P extends Presenter> {

        @SuppressWarnings("unchecked")
        protected Class<P> getPresenterClass(Object view)
                throws ClassCastException {

            Type type = view.getClass().getGenericSuperclass();
            ParameterizedType paramType = (ParameterizedType) type;
            return (Class<P>) paramType.getActualTypeArguments()[0];
        }

        private Constructor<?> getConstructor(Object view) throws ClassCastException {
            Constructor<?>[] constructors = getPresenterClass(view).getConstructors();
            Constructor<?> matchingConstructor = null;
            for (Constructor<?> constructor : constructors) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Class<?> viewModelType = parameterTypes[0];
                    if (ViewModel.class.isAssignableFrom(viewModelType)) {
                        matchingConstructor = constructor;
                        break;
                    }
                }
            }
            if(matchingConstructor == null) {
                throw new RuntimeException("Did you forget to make your Fragment extending the BaseFragment class ?");
            }
            return matchingConstructor;
        }

        public P build(Object view, ViewModel viewModel) {
            try {
                return (P) getConstructor(view).newInstance(viewModel);
            } catch (ClassCastException e) {
                throw new RuntimeException(
                        "Did you forget to add a Presenter as a genericType in your Activity ?",
                        e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "Did you forget to make your Fragment implementing the BaseView interface ?",
                        e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
