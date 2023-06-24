package com.meliksah.banka.app.cus.dao;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CusCustomerDao extends JpaRepository<CusCustomer, Long> {

    CusCustomer findByIdentityNo(Long identityNo);

    boolean existsCusCustomerByIdentityNo(Long identityNo);
}
