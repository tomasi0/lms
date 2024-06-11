package com.dw.lms.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class Teacher_CK implements Serializable {
    private User user;
    private Lecture lecture;

}
