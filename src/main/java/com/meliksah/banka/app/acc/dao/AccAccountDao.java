package com.meliksah.banka.app.acc.dao;

import com.meliksah.banka.app.acc.domain.AccAccount;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccAccountDao extends JpaRepository<AccAccount, Long> {

    List<AccAccount> findAllByStatusType(GenStatusType statusType);

    Page<AccAccount> findAllByStatusType(GenStatusType statusType, Pageable pageable);
}
