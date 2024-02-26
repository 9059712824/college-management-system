package com.learning.collegemanagementsystem.service;

import com.learning.collegemanagementsystem.dao.StudentAccountDao;
import com.learning.collegemanagementsystem.dao.StudentAuthOTPDao;
import com.learning.collegemanagementsystem.dao.StudentDao;
import com.learning.collegemanagementsystem.dto.CreateStudentRequestDto;
import com.learning.collegemanagementsystem.model.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;

    private final StudentDao studentDao;

    private final StudentAccountDao studentAccountDao;

    private final StudentAuthOTPDao studentAuthOTPDao;

    private final JavaMailSender mailSender;

    @Override
    public StudentModel save(CreateStudentRequestDto studentRequestDto) throws MessagingException {
        StudentModel student = new StudentModel();
        student.setUuid(UUID.randomUUID());
        student.setFirstName(studentRequestDto.getFirstName());
        student.setLastName(studentRequestDto.getLastName());
        student.setPhoneNumber(studentRequestDto.getPhoneNumber());
        student.setEmail(studentRequestDto.getEmail());
        student.setCollege(studentRequestDto.getCollege());

        String rollNumber = studentRequestDto.getCollege().toString() + studentRequestDto.getStartYear().toString().substring(2) + studentRequestDto.getBranch();
        long studentCountByBranch = studentDao.countByBranchAndCollege(studentRequestDto.getBranch(), studentRequestDto.getCollege());
        String value;
        if (studentCountByBranch >= 0 && studentCountByBranch < 9) {
            value = "00" + String.valueOf(studentCountByBranch + 1);
        } else if (studentCountByBranch >= 9 && studentCountByBranch > 99) {
            value = "0" + String.valueOf(studentCountByBranch + 1);
        } else {
            value = String.valueOf(studentCountByBranch + 1);
        }
        student.setRollNumber(rollNumber + value);
        student.setDegree(studentRequestDto.getDegree());
        student.setBranch(studentRequestDto.getBranch());
        student.setJoiningDate(studentRequestDto.getJoiningDate());
        student.setStartYear(studentRequestDto.getStartYear());
        Year endYear = null;
        if (studentRequestDto.getDegree() == Degree.BTECH) {
            endYear = studentRequestDto.getStartYear().plusYears(4);
        } else if (studentRequestDto.getDegree() == Degree.MTECH) {
            endYear = studentRequestDto.getStartYear().plusYears(2);
        }

        student.setEndYear(endYear);
        student.setCreatedTime(Calendar.getInstance().getTime());
        student.setUpdatedTime(Calendar.getInstance().getTime());

        var result = studentDao.save(student);
        System.out.println(result);

        StudentAccountModel studentAccount = new StudentAccountModel();
        studentAccount.setUuid(UUID.randomUUID());
        studentAccount.setStudentModel(result);
        studentAccount.setAccountStatus(AccountStatus.PENDING);
        studentAccount.setPassword(generateRandomPassword());
        studentAccount.setUpdatedTime(Calendar.getInstance().getTime());
        studentAccountDao.save(studentAccount);

        StudentAuthOTPModel studentAuthOTPModel = new StudentAuthOTPModel();
        studentAuthOTPModel.setUuid(UUID.randomUUID());
        studentAuthOTPModel.setStudent(result);
        studentAuthOTPModel.setOtp(generateOTP());
        studentAuthOTPModel.setOtpUpdatedTime(Calendar.getInstance().getTime());
        studentAuthOTPDao.save(studentAuthOTPModel);

        sendRegistrationConfirmationMail(student);

        return result;
    }

    @Override
    public void delete(UUID uuid) {
        StudentAccountModel studentAccount = studentAccountDao.getStudentAccountByStudentUuid(uuid);

        if (studentAccount == null) {
            System.out.println("Didn't found account with id " + uuid);
        } else if (studentAccount != null && studentAccount.getAccountStatus().equals(AccountStatus.ACTIVE) || studentAccount.getAccountStatus().equals(AccountStatus.PENDING)) {
            studentAccount.setAccountStatus(AccountStatus.INACTIVE);
            studentAccountDao.save(studentAccount);
        }
    }

    @Override
    public void registerRemainder(College college) throws MessagingException {
        List<StudentModel> studentList = studentDao.registerRemainder(college);
        System.out.println(studentList);

        for (StudentModel student : studentList) {
            sendEmail(student, "Account Activation");
        }
    }

    @Override
    public void activateAccount(UUID studentUuid, long otp) {
        StudentModel student = studentDao.getReferenceById(studentUuid);

        if (student != null) {
            StudentAccountModel studentAccount = studentAccountDao.getStudentAccountByStudentUuid(student.getUuid());
            StudentAuthOTPModel studentAuthOTP = studentAuthOTPDao.getStudentAuthOtpByStudentUuid(student.getUuid());

            if (studentAuthOTP.getOtp() == otp && studentAuthOTP.getOtpUpdatedTime().getTime() + OTP_VALID_DURATION > System.currentTimeMillis()) {
                studentAccount.setAccountStatus(AccountStatus.ACTIVE);
                studentAccountDao.save(studentAccount);
            } else if (studentAuthOTP.getOtpUpdatedTime().getTime() + OTP_VALID_DURATION < System.currentTimeMillis()) {
                studentAuthOTP.setOtp(generateOTP());
                studentAuthOTPDao.save(studentAuthOTP);
                System.out.println("OTP expired, sent new OTP");
            } else {
                System.out.println("Incorrect OTP");
            }
        } else {
            throw new RuntimeException("Student Account exists with uuid " + studentUuid);
        }
    }

    @Override
    public void studentAccountResolver() {
        List<UUID> uuids = studentDao.getAllActiveStudentsUuid();

        for (UUID uuid : uuids) {
            Boolean isStudentAuthOTPDetailsExists = studentAuthOTPDao.isStudentAuthOTPDetailsExists(uuid);
            if(isStudentAuthOTPDetailsExists == false) {
                StudentAuthOTPModel studentAuthOTPModel = new StudentAuthOTPModel();
                StudentModel student = studentDao.getReferenceById(uuid);
                System.out.println(student);
                studentAuthOTPModel.setOtp(generateOTP());
                studentAuthOTPModel.setUuid(UUID.randomUUID());
                studentAuthOTPModel.setStudent(student);
                studentAuthOTPModel.setOtpUpdatedTime(Calendar.getInstance().getTime());
                studentAuthOTPDao.save(studentAuthOTPModel);
            }
        }
    }

    public void sendRegistrationConfirmationMail(StudentModel student) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("v4431365@gmail.com");
        helper.setTo(student.getEmail());
        helper.setSubject("Account Registration Successful");
        String content = "<p> Hello " + student.getFirstName() + " " + student.getLastName() + " ,<br>" +
                "Your Registration has been Completed with email " + student.getEmail() + " .<br>" +
                "<b>CollegeDetails : </b><br>" +
                "<b>College Code: </b>" + student.getCollege() + "<br> <b>Branch Name: </b>" + student.getBranch() +
                "<br><br><br>Thanks<br>CMS Team.</p>";
        helper.setText(content, true);

        mailSender.send(message);
    }

    public void sendEmail(StudentModel student, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("v4431365@gmail.com");
        helper.setTo(student.getEmail());
        helper.setSubject(subject);
        String content = "<p>Hello " + student.getFirstName() + " " + student.getLastName() + " ,</p>" +
                "<p>You are a part of " + student.getCollege() + " and Branch " + student.getBranch() + " </p>" +
                "<p>Please register your self </p>";
        helper.setText(content, true);

        mailSender.send(message);
    }

    public String generateRandomPassword() {
        String password = null;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < sb.capacity(); i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public long generateOTP() {
        Random random = new Random();
        Long number = random.nextLong(100000, 999999);
        return number;
    }
}
