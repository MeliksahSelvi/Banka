package com.meliksah.banka.app.acc.service.entityservice;

import com.meliksah.banka.app.acc.dao.AccMoneyTransferDao;
import com.meliksah.banka.app.acc.domain.AccMoneyTransfer;
import com.meliksah.banka.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccMoneyTransferEntityService extends BaseEntityService<AccMoneyTransfer, AccMoneyTransferDao> {

    public AccMoneyTransferEntityService(AccMoneyTransferDao dao) {
        super(dao);
    }
}
