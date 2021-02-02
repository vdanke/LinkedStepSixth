package org.step.linked.step.service;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    void sendNotification(String text);

    CompletableFuture<String> sendNotificationAsync(String text);
}
