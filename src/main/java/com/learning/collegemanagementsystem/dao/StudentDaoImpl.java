package com.learning.collegemanagementsystem.dao;

import com.learning.collegemanagementsystem.model.Branch;
import com.learning.collegemanagementsystem.model.College;
import com.learning.collegemanagementsystem.entity.Student;
import com.learning.collegemanagementsystem.model.StudentModel;
import com.learning.collegemanagementsystem.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class StudentDaoImpl implements StudentDao{
    private final StudentRepository studentRepository;

    public static final ModelMapper modelMapper = new ModelMapper();

    public long count(){
        return studentRepository.count();
    }

    public StudentModel save(StudentModel student) {
        return modelMapper.map(studentRepository.save(modelMapper.map(student, Student.class)), StudentModel.class);
    }

    @Override
    public long countByBranchAndCollege(Branch branch, College college) {
        return studentRepository.countByBranchAndCollege(branch,college);
    }

    @Override
    public void delete(UUID uuid) {
        studentRepository.deleteById(uuid);
    }

    @Override
    public List<StudentModel> registerRemainder(College college) {
        return studentRepository.getAllByCollege(college)
                .stream()
                .map(student -> modelMapper.map(student, StudentModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentModel getReferenceById(UUID studentUuid) {
        return modelMapper.map(studentRepository.getStudentByUuid(studentUuid), StudentModel.class);
    }

    @Override
    public List<UUID> getAllActiveStudentsUuid() {
        return studentRepository.getAllStudentsUuid();
    }
}
