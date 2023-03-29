package com.meliksah.banka.app.crd.service.entityservice;

import com.meliksah.banka.app.crd.dao.CrdCreditCardDao;
import com.meliksah.banka.app.crd.domain.CrdCreditCard;
import com.meliksah.banka.app.crd.dto.CrdCreditCardDetails;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import com.meliksah.banka.app.gen.service.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CrdCreditCardEntityService extends BaseEntityService<CrdCreditCard, CrdCreditCardDao> {
    public CrdCreditCardEntityService(CrdCreditCardDao dao) {
        super(dao);
    }

    public List<CrdCreditCard> findAllActiveCreditCardList() {
        return getDao().findAllByStatusType(GenStatusType.ACTIVE);
    }

    public List<CrdCreditCard> findAllActiveCreditCardList(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);
        Page<CrdCreditCard> allByStatusType = getDao().findAllByStatusType(GenStatusType.ACTIVE, pageRequest);
        return allByStatusType.toList();
    }

    public List<CrdCreditCard> findAllPassiveCreditCardList() {
        return getDao().findAllByStatusType(GenStatusType.PASSIVE);
    }

    public CrdCreditCard findByCardNoAndCvvNoAndExpireDate(Long cardNo, Long cvvNo, Date expireDate) {
        return getDao().findByCardNoAndCvvNoAndExpireDateAndStatusType(cardNo, cvvNo, expireDate, GenStatusType.ACTIVE);
    }

    public CrdCreditCardDetails getCrdCreditCardDetails(Long crdCreditCardId) {
        return getDao().getCrdCreditCardDetails(crdCreditCardId);
    }
}
