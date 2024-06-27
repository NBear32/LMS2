package com.dw.lms.repository;


import com.dw.lms.model.Teacher;
import com.dw.lms.model.CK.Teacher_CK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Teacher_CK> {
}
