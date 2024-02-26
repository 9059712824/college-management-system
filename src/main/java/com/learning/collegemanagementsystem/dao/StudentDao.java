package com.learning.collegemanagementsystem.dao;

import com.learning.collegemanagementsystem.model.Branch;
import com.learning.collegemanagementsystem.model.College;
import com.learning.collegemanagementsystem.model.StudentModel;

import java.util.List;
import java.util.UUID;

public interface StudentDao {
    long count();

    StudentModel save(StudentModel student);

    long countByBranchAndCollege(Branch branch, College college);

    void delete(UUID uuid);

    List<StudentModel> registerRemainder(College college);

    StudentModel getReferenceById(UUID studentUuid);

    List<UUID> getAllActiveStudentsUuid();
}
