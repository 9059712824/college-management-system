package com.learning.collegemanagementsystem.dto;

import com.learning.collegemanagementsystem.model.Branch;
import com.learning.collegemanagementsystem.model.College;
import com.learning.collegemanagementsystem.model.Degree;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequestDto {
    @NotNull
    @Size(min = 3, max = 25)
    private String firstName;

    @NotNull
    @Size
    private String lastName;

    @NotNull
    @Digits(integer = 10,fraction = 0)
    private Long phoneNumber;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private College college;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Branch branch;

    @NotNull
    private Date joiningDate;

    @NotNull
    private Year startYear;
}