package com.meliksah.banka.app.crd.dao;

import com.meliksah.banka.app.crd.domain.CrdCreditCard;
import com.meliksah.banka.app.crd.dto.CrdCreditCardDetails;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CrdCreditCardDao extends JpaRepository<CrdCreditCard, Long> {

    List<CrdCreditCard> findAllByStatusType(GenStatusType statusType);

    Page<CrdCreditCard> findAllByStatusType(GenStatusType statusType, Pageable pageable);

    CrdCreditCard findByCardNoAndCvvNoAndExpireDateAndStatusType(Long cardNo, Long cvvNo, Date expireDate, GenStatusType statusType);

    @Query(
            "Select new com.meliksah.banka.app.crd.dto.CrdCreditCardDetails(" +
                    " cusCustomer.name," +
                    " cusCustomer.surName," +
                    " crdCreditCard.cardNo," +
                    " crdCreditCard.expireDate," +
                    " crdCreditCard.currentDebt," +
                    " crdCreditCard.minimumPaymentAmount," +
                    " crdCreditCard.cutoffDate," +
                    " crdCreditCard.dueDate" +
                    ")" +
                    " From CrdCreditCard crdCreditCard " +
                    " left join CusCustomer cusCustomer on crdCreditCard.cusCustomerId = cusCustomer.id " +
                    " where crdCreditCard.id =:crdCreditCardId"
    )
    CrdCreditCardDetails getCrdCreditCardDetails(Long crdCreditCardId);

}
