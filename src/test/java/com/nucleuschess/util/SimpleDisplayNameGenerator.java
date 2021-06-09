package com.nucleuschess.util;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public final class SimpleDisplayNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return testClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return nestedClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return testMethod.getName();
    }
}
