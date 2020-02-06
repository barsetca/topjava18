package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello TopJava Enterprise!");

        System.out.println(LocalDateTime.of(2015, Month.MAY, 30, 10, 0));
        System.out.println();
        LocalDateTime localDateTime = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
        System.out.println(TimeUtil.localTimeToString(localDateTime));
    }
}
