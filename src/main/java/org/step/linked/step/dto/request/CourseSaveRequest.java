package org.step.linked.step.dto.request;

public class CourseSaveRequest {

    private String description;

    public CourseSaveRequest() {
    }

    public CourseSaveRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
