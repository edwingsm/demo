package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserUpdateForm {


    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
