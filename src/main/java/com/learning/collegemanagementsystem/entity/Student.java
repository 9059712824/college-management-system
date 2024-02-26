package com.learning.collegemanagementsystem.entity;

import com.learning.collegemanagementsystem.model.Branch;
import com.learning.collegemanagementsystem.model.College;
import com.learning.collegemanagementsystem.model.Degree;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Year;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @Column(unique = true, updatable = false, nullable = false, name = "roll_number")
    private String rollNumber;

    @Column(nullable = false, name = "first_name")
    @Size(min = 3, max =25)
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @Size(min=1, max=25)
    private String lastName;

    @Column(nullable = false, name = "phone_number")
    @Digits(integer = 10, fraction = 0)
    private Long phoneNumber;

    @Column(unique = true, nullable = false, name = "email")
    @Email
    private String email;

    @Column(name = "college")
    @Enumerated(EnumType.STRING)
    private College college;

    @Column(nullable = false, name= "degree")
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Column(nullable = false,name = "branch")
    @Enumerated(EnumType.STRING)
    private Branch branch;

    @Column(nullable = false, name = "joining_date")
    private Date joiningDate;

    @Column(nullable = false, name = "start_year")
    private Year startYear;

    @Column(nullable = false, name = "end_year")
    private Year endYear;

    @CreationTimestamp
    @Column(updatable = false, name = "creation_time")
    private Date createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private Date updatedTime;
}
