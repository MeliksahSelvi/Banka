package com.meliksah.banka.app.sec.service;

import com.meliksah.banka.app.cus.dto.CusCustomerDto;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.cus.enums.CusErrorMessage;
import com.meliksah.banka.app.cus.service.CusCustomerService;
import com.meliksah.banka.app.cus.service.entityservice.CusCustomerEntityService;
import com.meliksah.banka.app.gen.dto.JwtToken;
import com.meliksah.banka.app.gen.exception.exceptions.ItemNotFoundException;
import com.meliksah.banka.app.gen.util.HazelCastCacheUtil;
import com.meliksah.banka.app.sec.dto.SecLoginRequestDto;
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
    private final CusCustomerEntityService cusCustomerEntityService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final HazelCastCacheUtil hazelCastCacheUtil;

    public CusCustomerDto register(CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {
        CusCustomerDto cusCustomerDto = cusCustomerService.save(cusCustomerSaveRequestDto);
        return cusCustomerDto;
    }

    public JwtToken login(SecLoginRequestDto secLoginRequestDto) {
        validateCusCustomer(secLoginRequestDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(secLoginRequestDto.getIdentityNo().toString(), secLoginRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtToken jwtToken = jwtTokenGenerator.genereteJwtToken(authentication);

        writeTokenToCache(jwtToken);

        return jwtToken;
    }

    private void writeTokenToCache(JwtToken jwtToken) {
        hazelCastCacheUtil.writeTokenMap(jwtToken);
    }

    private void validateCusCustomer(SecLoginRequestDto secLoginRequestDto) {
        boolean customerIsExist = cusCustomerEntityService.existsCusCustomerByIdentityNo(secLoginRequestDto.getIdentityNo());
        if (!customerIsExist) {
            throw new ItemNotFoundException(CusErrorMessage.CUSTOMER_NOT_FOUND);
        }
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
