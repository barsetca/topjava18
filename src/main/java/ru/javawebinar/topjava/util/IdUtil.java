package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdUtil {

    private static AtomicInteger id = new AtomicInteger(0);

    public static int incrementId() {
        id.incrementAndGet();
        return id.get();
    }
}

