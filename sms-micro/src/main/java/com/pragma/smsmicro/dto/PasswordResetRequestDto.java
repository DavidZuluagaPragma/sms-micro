package com.pragma.smsmicro.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
    private String destinationNumber;
    private String user;
    private String password;
}
