package com.meliksah.banka.app.acc.dao;

import com.meliksah.banka.app.acc.domain.AccMoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccMoneyTransferDao extends JpaRepository<AccMoneyTransfer, Long> {
}
