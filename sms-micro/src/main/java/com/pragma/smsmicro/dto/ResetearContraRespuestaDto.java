package com.pragma.smsmicro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetearContraRespuestaDto {
    private OtpEstado estado;
    private String mensaje;
}
