package edu.duke.ece651.team4.server;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeekReport {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static long calculateDelayToNextSundayMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextSundayMidnight = now.withHour(0).withMinute(0).withSecond(0).withNano(0)
                .with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        return Duration.between(now, nextSundayMidnight).getSeconds();
    }

    public void scheduleWeeklyTask(Runnable task) {
      long delay = calculateDelayToNextSundayMidnight();
      scheduler.scheduleAtFixedRate(task, delay, TimeUnit.DAYS.toSeconds(7), TimeUnit.SECONDS);
    }

    public void addTask(Lecture lecture) {
        Runnable task = () -> {
            lecture.getProfessorList().forEach((value) -> {
                try {
                    lecture.notifySingle(lecture.generateReport(), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        };
        scheduleWeeklyTask(task);
    }
        
}
