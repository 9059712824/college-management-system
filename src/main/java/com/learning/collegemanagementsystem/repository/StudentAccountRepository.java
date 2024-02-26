package com.learning.collegemanagementsystem.repository;

import com.learning.collegemanagementsystem.entity.StudentAccount;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface StudentAccountRepository extends JpaRepository<StudentAccount, UUID> {
    @Query("select sa from student_account sa where sa.student.uuid = :studentUuid")
    StudentAccount getStudentAccountByStudent(@PathParam("studentUuid") UUID studentUuid);
}
