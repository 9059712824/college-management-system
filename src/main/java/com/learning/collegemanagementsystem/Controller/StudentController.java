package com.learning.collegemanagementsystem.Controller;

import com.learning.collegemanagementsystem.dto.CreateStudentRequestDto;
import com.learning.collegemanagementsystem.model.College;
import com.learning.collegemanagementsystem.model.StudentModel;
import com.learning.collegemanagementsystem.service.StudentService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentModel> save(@RequestBody CreateStudentRequestDto studentRequestDto) throws MessagingException {
        return new ResponseEntity<>(studentService.save(studentRequestDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        studentService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/registerRemainder")
    public ResponseEntity<?> registerRemainder(@RequestParam("College") College college) throws MessagingException, UnsupportedEncodingException {
        studentService.registerRemainder(college);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/activateAccount/{studentUuid}")
    public ResponseEntity<?> activateAccount(@PathVariable UUID studentUuid, @RequestParam Long OTP) {
        studentService.activateAccount(studentUuid,OTP);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void StudentAccountResolver() {
        studentService.studentAccountResolver();
    }
}