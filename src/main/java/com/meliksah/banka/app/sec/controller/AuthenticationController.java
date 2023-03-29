package com.meliksah.banka.app.sec.controller;

import com.meliksah.banka.app.cus.dto.CusCustomerDto;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.gen.dto.RestResponse;
import com.meliksah.banka.app.sec.dto.SecLoginRequestDto;
import com.meliksah.banka.app.sec.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(tags = "Authentication Controller")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {
        CusCustomerDto cusCustomerDto = authenticationService.register(cusCustomerSaveRequestDto);
        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

    @Operation(tags = "Authentication Controller")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SecLoginRequestDto secLoginRequestDto) {
        String token = authenticationService.login(secLoginRequestDto);
        return ResponseEntity.ok(RestResponse.of(token));
    }
}
