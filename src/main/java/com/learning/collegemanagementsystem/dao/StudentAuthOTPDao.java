package com.learning.collegemanagementsystem.dao;

import com.learning.collegemanagementsystem.model.StudentAuthOTPModel;

import java.util.UUID;

public interface StudentAuthOTPDao {
    void save(StudentAuthOTPModel studentAuthOTPModel);

    StudentAuthOTPModel getStudentAuthOtpByStudentUuid(UUID uuid);

    Boolean isStudentAuthOTPDetailsExists(UUID studentUuid);
}
