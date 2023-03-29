package com.meliksah.banka.app.acc.service.entityservice;

import com.meliksah.banka.app.acc.dao.AccAccountActivityDao;
import com.meliksah.banka.app.acc.domain.AccAccountActivity;
import com.meliksah.banka.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccAccountActivityEntityService extends BaseEntityService<AccAccountActivity, AccAccountActivityDao> {

    public AccAccountActivityEntityService(AccAccountActivityDao dao) {
        super(dao);
    }
}
