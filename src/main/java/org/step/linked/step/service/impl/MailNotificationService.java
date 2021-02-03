package org.step.linked.step.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.step.linked.step.model.PromotionCard;
import org.step.linked.step.model.User;
import org.step.linked.step.service.NotificationService;
import org.step.linked.step.service.PromotionService;
import org.step.linked.step.service.UserService;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MailNotificationService implements NotificationService {

    private final JavaMailSender javaMailSender;
    private final PromotionService promotionService;
    private final UserService userService;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendNotification() {
        String[] emails = userService.findAll().stream().map(User::getUsername).toArray(String[]::new);
        promotionService.findNotExpiredPromotion()
                .stream()
                .findAny().ifPresent(pc -> this.sendPromotion(pc, emails)
        );
    }

    @Override
    @Async("notificationExecutor")
    public CompletableFuture<Void> sendNotificationAsync(String email) {
        return CompletableFuture.runAsync(() -> promotionService.findNotExpiredPromotion()
                .stream()
                .findAny().ifPresent(pc -> this.sendPromotion(pc, email)
                ));
    }

    private void sendPromotion(PromotionCard promotionCard, String... emails) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(emails);
        message.setSubject(promotionCard.getPromo());
        message.setText(promotionCard.getText());

        javaMailSender.send(message);
    }
}
