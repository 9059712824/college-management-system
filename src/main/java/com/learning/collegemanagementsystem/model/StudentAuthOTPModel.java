package com.learning.collegemanagementsystem.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAuthOTPModel {

    @NotNull
    private UUID uuid;

    @NotNull
    private StudentModel student;

    @NotNull
    private long otp;

    @NotNull
    private Date otpUpdatedTime;
}
