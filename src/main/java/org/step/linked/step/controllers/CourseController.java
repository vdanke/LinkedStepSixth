package org.step.linked.step.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.step.linked.step.dto.request.CourseSaveRequest;
import org.step.linked.step.dto.request.CourseUpdateRequest;
import org.step.linked.step.dto.response.CourseSaveResponse;
import org.step.linked.step.dto.response.CourseUpdateResponse;
import org.step.linked.step.model.Course;
import org.step.linked.step.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCourseById(@PathVariable(name = "id") String id) {
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_AUTHOR','ROLE_ADMIN')")
//    @PostAuthorize("#{principal.accountNonExpired == true}")
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

//    @PreAuthorize("#{#principal.username == 'gop@mail.ru'}")
    @PreAuthorize("hasAnyRole('ROLE_AUTHOR','ROLE_ADMIN')")
    @PostMapping(
            value = "/courses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CourseSaveResponse> saveCourse(@RequestBody CourseSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CourseSaveResponse("some id"));
    }
}
