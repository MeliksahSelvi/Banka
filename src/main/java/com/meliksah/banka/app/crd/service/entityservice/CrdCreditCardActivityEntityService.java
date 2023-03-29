package com.meliksah.banka.app.crd.service.entityservice;

import com.meliksah.banka.app.crd.dao.CrdCreditCardActivityDao;
import com.meliksah.banka.app.crd.domain.CrdCreditCardActivity;
import com.meliksah.banka.app.gen.service.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CrdCreditCardActivityEntityService extends BaseEntityService<CrdCreditCardActivity, CrdCreditCardActivityDao> {
    public CrdCreditCardActivityEntityService(CrdCreditCardActivityDao dao) {
        super(dao);
    }

    public List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, Date startDate, Date endDate) {
        return getDao().findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId, startDate, endDate);
    }

    public List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, Date startDate, Date endDate,
                                                                                         Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);

        Page<CrdCreditCardActivity> allByCrdCreditCardIdAndTransactionDateBetween = getDao().findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId, startDate, endDate, pageRequest);
        return allByCrdCreditCardIdAndTransactionDateBetween.toList();
    }
}
