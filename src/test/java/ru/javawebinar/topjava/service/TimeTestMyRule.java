package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.List;

public class TimeTestMyRule implements TestRule {

    public final List<String> listMethodsTime;

    public TimeTestMyRule(List<String> listMethodsTime) {
        this.listMethodsTime = listMethodsTime;
    }

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long timeStart = System.currentTimeMillis();
                try {
                    base.evaluate();
                } finally {
                    long methodTime = System.currentTimeMillis() - timeStart;
                    String methodName = description.getMethodName();
                    String methodTimeMessage = String.format("RunTime of test method name: %s is - %d ms\n", methodName, methodTime);
                    System.out.println(methodTimeMessage);
                    listMethodsTime.add(methodTimeMessage);
                }
            }
        };
    }
}
