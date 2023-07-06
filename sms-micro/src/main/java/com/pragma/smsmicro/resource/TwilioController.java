package com.pragma.smsmicro.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TwilioController {

    @Autowired
    private TwilioOTPHandler handler;

    @Bean
    public RouterFunction<ServerResponse> handleSMS() {
        return RouterFunctions.route()
                .POST("/router/sendOTP", handler::sendOTP)
                .POST("/router/validateOTP", handler::validateOTP)
                .build();
    }
}
