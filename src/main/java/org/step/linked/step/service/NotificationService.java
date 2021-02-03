package org.step.linked.step.service;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    void sendNotification();

    CompletableFuture<Void> sendNotificationAsync(String text);
}
