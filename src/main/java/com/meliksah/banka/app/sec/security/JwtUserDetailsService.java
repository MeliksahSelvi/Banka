package com.meliksah.banka.app.sec.security;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.service.entityservice.CusCustomerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CusCustomerEntityService cusCustomerEntityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long userNameAsLong = Long.valueOf(username);
        CusCustomer cusCustomer = cusCustomerEntityService.findByIdentityNo(userNameAsLong);

        JwtUserDetails userDetails = JwtUserDetails.create(cusCustomer);
        return userDetails;
    }

    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        CusCustomer cusCustomer = cusCustomerEntityService.getByIdWithControl(id);

        JwtUserDetails userDetails = JwtUserDetails.create(cusCustomer);
        return userDetails;
    }
}
