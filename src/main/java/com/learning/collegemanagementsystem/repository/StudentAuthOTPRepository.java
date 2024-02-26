package com.learning.collegemanagementsystem.repository;

import com.learning.collegemanagementsystem.entity.StudentAuthOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface StudentAuthOTPRepository extends JpaRepository<StudentAuthOTP, UUID> {
    @Query("select sao from student_auth_otp sao where sao.student.uuid = :studentUuid")
    StudentAuthOTP getStudentAuthOTPByStudent(UUID studentUuid);

    @Query("select case when exists (select sao from student_auth_otp sao where sao.student.uuid = :studentUuid) Then cast(true as boolean) else cast (false as boolean) end ")
    Boolean isStudentAuthOTPDetailsExists(UUID studentUuid);
}
