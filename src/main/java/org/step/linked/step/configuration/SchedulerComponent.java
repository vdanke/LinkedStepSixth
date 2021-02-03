package org.step.linked.step.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.step.linked.step.service.NotificationService;

@Component
@RequiredArgsConstructor
public class SchedulerComponent {

    private final NotificationService notificationService;

    /*
    fixedDelay - каждые 5 секунд, независимо от результата
    fixedRate - каждые 5 секунд, с последнего успешного старта метода
    initialDelay - после старта через сколько секунд запустися первый раз
    cron - 0 0 9-17 * * 5-7
     */
    @Async("notificationExecutor")
    @Scheduled(cron = "0 0 17 * * 5")
    public void sendNotification() {
        System.out.println("Send promotion");
        notificationService.sendNotification();
    }
}
