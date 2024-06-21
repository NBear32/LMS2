package com.dw.lms.repository;

import com.dw.lms.model.CK.Course_registration_CK;
import com.dw.lms.model.Course_registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<Course_registration, Course_registration_CK> {
}
