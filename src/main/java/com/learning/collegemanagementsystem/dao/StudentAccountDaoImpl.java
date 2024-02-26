package com.learning.collegemanagementsystem.dao;

import com.learning.collegemanagementsystem.entity.Student;
import com.learning.collegemanagementsystem.entity.StudentAccount;
import com.learning.collegemanagementsystem.model.StudentAccountModel;
import com.learning.collegemanagementsystem.model.StudentModel;
import com.learning.collegemanagementsystem.repository.StudentAccountRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class StudentAccountDaoImpl implements StudentAccountDao{

    private final StudentAccountRepository studentAccountRepository;

    public static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public void save(StudentAccountModel studentAccount) {
        studentAccountRepository.save(modelMapper.map(studentAccount, StudentAccount.class));
    }

    @Override
    public StudentAccountModel getStudentAccountByStudentUuid(UUID uuid) {
        Student student = studentAccountRepository.getStudentAccountByStudent(uuid).getStudent();

        StudentAccountModel studentAccountModel = modelMapper.map(studentAccountRepository.getStudentAccountByStudent(uuid), StudentAccountModel.class);
        studentAccountModel.setStudentModel(modelMapper.map(student, StudentModel.class));
        return studentAccountModel;
    }
}
