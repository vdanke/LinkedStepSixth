package org.step.linked.step.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.step.linked.step.dto.request.CourseSaveRequest;
import org.step.linked.step.dto.request.CourseUpdateRequest;
import org.step.linked.step.dto.response.CourseSaveResponse;
import org.step.linked.step.dto.response.CourseUpdateResponse;
import org.step.linked.step.exception.NotFoundException;
import org.step.linked.step.model.Course;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final List<Course> COURSE_DB;

    static {
        COURSE_DB = new ArrayList<>();
        COURSE_DB.add(new Course("abc", "good course"));
        COURSE_DB.add(new Course("cda", "new course"));
        COURSE_DB.add(new Course("bdc", "worst course"));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(COURSE_DB);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCourseById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(COURSE_DB.stream()
                .filter(c -> c.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFoundException(String.format("Course with ID %s not found", id)))
        );
    }

    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CourseUpdateResponse> updateCourse(
            @PathVariable(name = "id") String courseId,
            @RequestBody CourseUpdateRequest request
    ) {
        return ResponseEntity.ok(new CourseUpdateResponse());
    }

    @PostMapping(
            value = "/courses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CourseSaveResponse> saveCourse(@RequestBody CourseSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CourseSaveResponse("some id"));
    }
}
