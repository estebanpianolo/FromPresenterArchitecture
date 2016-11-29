package com.example.etiennepinault.viewmodelpresenting.commons;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ViewModel<S extends Serializable> {

    protected abstract void restoreState(@Nullable S state);
    protected abstract @Nullable S saveState();

    static class Factory<VM> {

        @SuppressWarnings("unchecked")
        protected Class<VM> getClass(Object view)
                throws ClassCastException {

            Type type = view.getClass().getGenericSuperclass();
            ParameterizedType paramType = (ParameterizedType) type;
            return (Class<VM>) paramType.getActualTypeArguments()[1];
        }

        public VM build(Object view) {
            try {
                return getClass(view).newInstance();
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
    }}
