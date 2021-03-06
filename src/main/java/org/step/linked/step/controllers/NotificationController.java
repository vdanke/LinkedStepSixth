package org.step.linked.step.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.step.linked.step.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/async")
    public ResponseEntity<?> sendNotificationAsync(@RequestBody String email) {
        notificationService.sendNotificationAsync(email);
        return ResponseEntity.ok().build();
    }
}
