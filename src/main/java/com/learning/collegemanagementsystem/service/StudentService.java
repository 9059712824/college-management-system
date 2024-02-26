package com.learning.collegemanagementsystem.service;

import com.learning.collegemanagementsystem.dto.CreateStudentRequestDto;
import com.learning.collegemanagementsystem.model.College;
import com.learning.collegemanagementsystem.model.StudentModel;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public interface StudentService {
    StudentModel save(CreateStudentRequestDto studentRequestDto) throws MessagingException;

    void delete(UUID uuid);

    void registerRemainder(College college) throws MessagingException, UnsupportedEncodingException;

    void activateAccount(UUID studentUuid, long otp);

    void studentAccountResolver();
}
