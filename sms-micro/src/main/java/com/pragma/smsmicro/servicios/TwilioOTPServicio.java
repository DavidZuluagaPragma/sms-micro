package com.pragma.smsmicro.servicios;

import com.pragma.smsmicro.configuracion.TwilioConfiguracion;
import com.pragma.smsmicro.dto.OtpEstado;
import com.pragma.smsmicro.dto.ResetearContraRequestDto;
import com.pragma.smsmicro.dto.ResetearContraRespuestaDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOTPServicio {
    @Autowired
    private TwilioConfiguracion twilioConfig;

    Map<String, String> otpMap = new HashMap<>();

    public Mono<ResetearContraRespuestaDto> sendOTPForPasswordReset(ResetearContraRequestDto passwordResetRequestDto) {

        ResetearContraRespuestaDto passwordResetResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getNumeroDestino());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = generateOTP();
            String otpMessage = "Querido usuario, tu pedido ya esta listo, entrega este pin ##" + otp +" y disfruta!";
            Message message = Message
                    .creator(to, from,
                            otpMessage)
                    .create();
            otpMap.put(passwordResetRequestDto.getUsuario(), otp);
            passwordResetResponseDto = new ResetearContraRespuestaDto(OtpEstado.DELIVERED, otpMessage);
        } catch (Exception ex) {
            passwordResetResponseDto = new ResetearContraRespuestaDto(OtpEstado.FAILED, ex.getMessage());
        }
        return Mono.just(passwordResetResponseDto);
    }

    public Mono<String> validateOTP(String userInputOtp, String userName) {
        if (userInputOtp.equals(otpMap.get(userName))) {
            otpMap.remove(userName,userInputOtp);
            return Mono.just("Validar OTP por fa para completar operación !");
        } else {
            return Mono.error(new IllegalArgumentException("Otp invalido prueba de nuevo !"));
        }
    }

    //6 digit otp
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}
