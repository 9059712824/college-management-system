package com.learning.collegemanagementsystem.dao;

import com.learning.collegemanagementsystem.entity.Student;
import com.learning.collegemanagementsystem.entity.StudentAuthOTP;
import com.learning.collegemanagementsystem.model.StudentAuthOTPModel;
import com.learning.collegemanagementsystem.repository.StudentAuthOTPRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class StudentAuthOTPDaoImpl implements StudentAuthOTPDao{

    private final StudentAuthOTPRepository studentAuthOTPRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public void save(StudentAuthOTPModel studentAuthOTPModel) {
        studentAuthOTPRepository.save(modelMapper.map(studentAuthOTPModel, StudentAuthOTP.class));
    }

    @Override
    public StudentAuthOTPModel getStudentAuthOtpByStudentUuid(UUID uuid) {
        return modelMapper.map(studentAuthOTPRepository.getStudentAuthOTPByStudent(uuid), StudentAuthOTPModel.class);
    }

    @Override
    public Boolean isStudentAuthOTPDetailsExists(UUID studentUuid) {
        return studentAuthOTPRepository.isStudentAuthOTPDetailsExists(studentUuid);
    }
}