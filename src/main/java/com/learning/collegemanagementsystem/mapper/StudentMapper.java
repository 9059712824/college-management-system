package com.learning.collegemanagementsystem.mapper;

import com.learning.collegemanagementsystem.entity.Student;

import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    Student studentToStudentModel(Student student);
}
