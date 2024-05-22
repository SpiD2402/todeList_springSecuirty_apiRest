package com.example.todelist.dto.passwordChange;

import lombok.Data;

@Data
public class PasswordChangeDao {
    private String username;        // o username, email, etc.
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
