package com.meliksah.banka.app.sec.service;

import com.meliksah.banka.app.cus.dto.CusCustomerDto;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.cus.service.CusCustomerService;
import com.meliksah.banka.app.sec.dto.SecLoginRequestDto;
import com.meliksah.banka.app.sec.enums.EnumJwtConstant;
import com.meliksah.banka.app.sec.security.JwtTokenGenerator;
import com.meliksah.banka.app.sec.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CusCustomerService cusCustomerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;

    public CusCustomerDto register(CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {
        CusCustomerDto cusCustomerDto = cusCustomerService.save(cusCustomerSaveRequestDto);
        return cusCustomerDto;
    }

    public String login(SecLoginRequestDto secLoginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(secLoginRequestDto.getIdentityNo().toString(), secLoginRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenGenerator.genereteJwtToken(authentication);

        String bearer = EnumJwtConstant.BEARER.toString();

        return bearer + token;
    }

    public Long getCurrentCustomerId() {
        JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails();

        Long customerId = null;
        if (jwtUserDetails != null) {
            customerId = jwtUserDetails.getId();
        }
        return customerId;
    }

    private JwtUserDetails getCurrentJwtUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtUserDetails jwtUserDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserDetails) {
            jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        }
        return jwtUserDetails;
    }
}