package org.step.linked.step.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ResponseFailed {

    @JsonProperty("path")
    private String path;
    @JsonProperty("method")
    private String method;
    @JsonProperty("message")
    private String message;
    @JsonProperty("time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime localDateTime;

    public ResponseFailed() {
    }

    @JsonCreator
    public ResponseFailed(String path, String method, String message, LocalDateTime localDateTime) {
        this.path = path;
        this.method = method;
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
