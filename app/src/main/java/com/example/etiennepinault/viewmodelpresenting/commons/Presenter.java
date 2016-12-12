package com.example.etiennepinault.viewmodelpresenting.commons;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Presenter<VM extends ViewModel, S extends Parcelable> {

    protected final @NonNull VM viewModel;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @SuppressWarnings({ "ConstantConditions", "unused" })
    private Presenter() {this.viewModel = null;}

    public Presenter(@NonNull VM viewModel) {this.viewModel = viewModel;}

    final protected void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    protected abstract void restoreState(@Nullable S state);
    protected abstract @Nullable S saveState();


    final protected void unsubscribe(Subscription subscription) {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    final void onDestroy() {
        destroy();
        subscriptions.clear();
    }

    abstract protected void destroy();

    public static class Factory<P extends Presenter> {

        @SuppressWarnings("unchecked") Class<P> getPresenterClass(Object view)
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
                throw new RuntimeException("Did you forget to make your View extending the Base view class ?");
            }
            return matchingConstructor;
        }

        public P build(Object view, ViewModel viewModel) {
            try {
                return (P) getConstructor(view).newInstance(viewModel);
            } catch (ClassCastException e) {
                throw new RuntimeException(
                        "Did you forget to add a Presenter as a genericType in your View ?",
                        e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
