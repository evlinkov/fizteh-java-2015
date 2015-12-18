package ru.fizteh.fivt.students.evlinkov.twitterstream;
/**
 * Created by evlinkov on 17.12.15.
 */
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.util.Date;
import java.time.*;

public class Time {
    public static String printTime(long tweetTime, long currentTime) {
        LocalDateTime currentTimer = new Date(currentTime).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime tweetTimer = new Date(tweetTime).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (tweetTimer.isAfter(currentTimer.minusMinutes(2))) {
            return "Только что";
        } else {
            if (tweetTimer.isAfter(currentTimer.minusHours(1))) {
                return new StringBuilder().append(ChronoUnit.MINUTES.between(tweetTimer, currentTimer))
                        .append(" минут назад").toString();
            } else {
                if (currentTimer.toLocalDate().isEqual(tweetTimer.toLocalDate())) {
                    return new StringBuilder().append(ChronoUnit.HOURS.between(tweetTimer, currentTimer))
                            .append(" часов назад").toString();
                } else {
                    if (tweetTimer.toLocalDate().isEqual(currentTimer.minusDays(1).toLocalDate())) {
                        return "вчера";
                    } else {
                        return new StringBuilder().append(ChronoUnit.DAYS.between(tweetTimer, currentTimer))
                                .append(" дней назад").toString();
                    }
                }
            }
        }
    }
}
