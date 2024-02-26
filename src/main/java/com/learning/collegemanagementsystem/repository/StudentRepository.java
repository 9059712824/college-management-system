package com.learning.collegemanagementsystem.repository;

import com.learning.collegemanagementsystem.model.Branch;
import com.learning.collegemanagementsystem.model.College;
import com.learning.collegemanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    long countByBranchAndCollege(Branch branch,College college);

    List<Student> getAllByCollege(College college);

    @Query("select s.uuid from student s " +
            "join student_account sa on sa.student.uuid = s.uuid where sa.accountStatus = 'PENDING'")
    List<UUID> getAllStudentsUuid();

    Student getStudentByUuid(UUID studentUuid);
}
