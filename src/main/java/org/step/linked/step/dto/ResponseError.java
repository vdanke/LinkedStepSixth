package org.step.linked.step.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ResponseError {

    String message;
    LocalDateTime time;
}
