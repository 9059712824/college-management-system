package com.learning.collegemanagementsystem.dao;

import com.learning.collegemanagementsystem.model.StudentAccountModel;

import java.util.UUID;

public interface StudentAccountDao {
    void save(StudentAccountModel studentAccount);

    StudentAccountModel getStudentAccountByStudentUuid(UUID uuid);

}
