package com.dw.lms.model.CK;

import com.dw.lms.model.Lecture;
import com.dw.lms.model.User;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class Course_registration_CK implements Serializable {
    private User user;
    private Lecture lecture;
}
