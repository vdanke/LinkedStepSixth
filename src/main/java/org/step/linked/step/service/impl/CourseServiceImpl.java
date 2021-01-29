package org.step.linked.step.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.step.linked.step.model.Course;
import org.step.linked.step.repository.CourseRepositoryImpl;
import org.step.linked.step.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepositoryImpl courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
