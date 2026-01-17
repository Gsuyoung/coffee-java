package com.cafe.coffeejava.notification.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationMessage {
    private Long userId;
    private String message;
}