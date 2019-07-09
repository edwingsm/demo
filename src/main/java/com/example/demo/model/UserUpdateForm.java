package com.example.demo.model;

import java.io.Serializable;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserUpdateForm implements Serializable {


    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
