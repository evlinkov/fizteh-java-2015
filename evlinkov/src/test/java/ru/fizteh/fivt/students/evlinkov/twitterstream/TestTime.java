package ru.fizteh.fivt.students.evlinkov.twitterstream;
/**
 * Created by evlinkov on 17.12.15.
 */
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;

public class TestTime {
    @Test
    public void test() {
        long start = LocalDate.of(2015, Month.JANUARY, 1)
            .atTime(0, 0, 0)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long finish = LocalDate.of(2015, Month.JANUARY, 1)
            .atTime(0, 0, 1)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Assert.assertEquals("Только что", Time.printTime(start, finish));
        finish = LocalDate.of(2015, Month.JANUARY, 1)
            .atTime(0, 15, 0)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Assert.assertEquals("15 минут назад", Time.printTime(start, finish));
        finish = LocalDate.of(2015, Month.JANUARY, 1)
            .atTime(5, 0, 0)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Assert.assertEquals("5 часов назад", Time.printTime(start, finish));
        finish = LocalDate.of(2015, Month.JANUARY, 2)
            .atTime(0, 1, 0)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Assert.assertEquals("вчера", Time.printTime(start, finish));
        finish = LocalDate.of(2015, Month.JANUARY, 4)
            .atTime(3, 20, 0)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Assert.assertEquals("3 дней назад", Time.printTime(start, finish));
    }
}
