package com.meliksah.banka.app.crd.dao;

import com.meliksah.banka.app.crd.domain.CrdCreditCardActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CrdCreditCardActivityDao extends JpaRepository<CrdCreditCardActivity, Long> {

    List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, Date startDate, Date endDate);

    Page<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, Date startDate, Date endDate, Pageable pageable);
}
