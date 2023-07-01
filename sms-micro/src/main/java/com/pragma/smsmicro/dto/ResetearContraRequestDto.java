package com.pragma.smsmicro.dto;

import lombok.Data;

@Data
public class ResetearContraRequestDto {
    private String numeroDestino;
    private String usuario;
    private String tiempoContra;
}
