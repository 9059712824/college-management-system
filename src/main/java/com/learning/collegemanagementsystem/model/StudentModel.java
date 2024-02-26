package com.learning.collegemanagementsystem.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel {

    private UUID uuid;

    private String rollNumber;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;

    private College college;

    private Degree degree;

    private Branch branch;

    private Date joiningDate;

    private Year startYear;

    private Year endYear;

    private Date createdTime;

    private Date updatedTime;
}
