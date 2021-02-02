package org.step.linked.step.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.step.linked.step.service.NotificationService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MailNotificationService implements NotificationService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendNotification(String text) {
        System.out.printf("Notification: %s\n", text);
    }

    @Override
    @Async("notificationExecutor")
    public CompletableFuture<String> sendNotificationAsync(String text) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Async: " + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@linked.step.ru");
            message.setTo("to email");
            message.setSubject("New big sell");
            message.setText(text);

            javaMailSender.send(message);
            return text;
        });
    }
}
