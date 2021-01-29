package org.step.linked.step.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.step.linked.step.model.Course;

@Repository
public interface CourseRepositoryImpl extends MongoRepository<Course, String> {
}
