package com.example.etiennepinault.viewmodelpresenting.commons;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ViewModel {

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
                        "Did you forget to add a ViewModel as a genericType in your View ?",
                        e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }}
